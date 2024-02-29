package event.userservice.global.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtTokenProvider {

    @Value("\${token.access-expired-time}")
    private var accessExpiredTime: Long = 0

    @Value("\${token.refresh-expired-time}")
    private var refreshExpiredTime: Long = 0

    @Value("\${jwt.secret.key}")
    private lateinit var secretKey: String

    val key: Key by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    private val signatureAlgorithm = Jwts.SIG.HS512

    fun createAccessToken(userId: String, uri: String, roles: List<String>): String {
        val claims = Jwts.claims().apply {
            subject(userId)
            mapOf("roles" to roles)
        }.build()

        return Jwts.builder()
            .claims()
            .add(claims)
            .and()
            .expiration(Date(System.currentTimeMillis() + accessExpiredTime))
            .issuedAt(Date())
            .issuer(uri)
            .signWith(key, signatureAlgorithm)
            .compact()
    }

    fun createRefreshToken(): String {
        val claims = Jwts.claims().build()
        claims["value"] = UUID.randomUUID()

        return Jwts.builder()
            .claims()
            .add(claims)
            .and()
            .expiration(Date(System.currentTimeMillis() + refreshExpiredTime))
            .issuedAt(Date())
            .signWith(secret as Key)
            .compact()
    }

    fun validateJwtToken(token: String) {
        try {
            Jwts.parser().verifyWith(secret as SecretKey).build().parseSignedClaims(token)
        } catch (jwtException: Exception) {
            when (jwtException) {
                is SignatureException,
                is MalformedJwtException,
                is UnsupportedJwtException,
                is IllegalArgumentException,
                is ExpiredJwtException -> throw jwtException

                else -> throw jwtException
            }
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
            Jwts.parser().verifyWith(secret as SecretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

    fun getRefreshTokenId(token: String): String {
        return getClaimsFromJwtToken(token)["value"].toString()
    }
}