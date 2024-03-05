package event.userservice.global.utils

import event.userservice.global.logger.logger
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {

    val log = logger()

    @Value("\${token.access-expired-time}")
    private var accessExpiredTime: Long = 0

    @Value("\${token.refresh-expired-time}")
    private var refreshExpiredTime: Long = 0

    @Value("\${token.secret}")
    private lateinit var secretKey: String

    val key: Key by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }.apply {
        Jwts.SIG.HS256.key().build().algorithm
    }

    fun createAccessToken(userId: String, uri: String, roles: List<String>): String {
        val claims = mutableMapOf<String, Any>()
        claims["sub"] = userId
        claims["roles"] = roles

        return Jwts.builder()
            .claims()
            .add(claims)
            .and()
            .expiration(Date(System.currentTimeMillis() + accessExpiredTime))
            .issuedAt(Date())
            .issuer(uri)
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(): String {
        val claims = mutableMapOf<String, Any>()
        claims["value"] = UUID.randomUUID()

        return Jwts.builder()
            .claims()
            .add(claims)
            .and()
            .expiration(Date(System.currentTimeMillis() + refreshExpiredTime))
            .issuedAt(Date())
            .signWith(key)
            .compact()
    }

    fun validateJwtToken(token: String): Boolean {
        return try {
            Jwts.parser().verifyWith(key as SecretKey).build().parseSignedClaims(token)
            true
        } catch (e: SignatureException) {
            log.error("Invalid JWT signature: ${e.message}")
            false
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token: ${e.message}")
            false
        } catch (e: ExpiredJwtException) {
            log.error("JWT token is expired: ${e.message}")
            false
        } catch (e: UnsupportedJwtException) {
            log.error("JWT token is unsupported: ${e.message}")
            false
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty: ${e.message}")
            false
        }
    }

    fun getUserId(token: String): String {
        return getClaimsFromJwtToken(token).subject
    }

    fun getExpiredTime(token: String): Date {
        return getClaimsFromJwtToken(token).expiration
    }

    private fun getClaimsFromJwtToken(token: String): Claims {
        return try {
            Jwts.parser().verifyWith(key as SecretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

    fun getRefreshTokenId(token: String): String {
        return getClaimsFromJwtToken(token)["value"].toString()
    }

    fun equalRefreshTokenId(refreshTokenId: String, refreshToken: String): Boolean {
        return refreshTokenId == getRefreshTokenId(refreshToken)
    }
}