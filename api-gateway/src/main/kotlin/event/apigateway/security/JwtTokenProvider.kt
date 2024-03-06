package event.apigateway.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.SignatureException
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {

    @Value("\${token.secret}")
    private lateinit var secretKey: String

    val key: Key by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
    }.apply {
        Jwts.SIG.HS256.key().build().algorithm
    }

    fun validateJwtToken(token: String) {
        try {
            Jwts.parser().verifyWith(key as SecretKey).build().parseSignedClaims(token)
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

    private fun getClaimsFromJwtToken(token: String): Claims {
        return try {
            Jwts.parser().verifyWith(key as SecretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

}