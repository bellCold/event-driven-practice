package event.apigateway.security

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {

    @Value("\${token.access-expired-time}")
    var accessExpiredTime: Long = 0

    @Value("\${token.refresh-expired-time}")
    var refreshExpiredTime: Long = 0

    @Value("\${token.secret}")
    lateinit var secret: String

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

    private fun getClaimsFromJwtToken(token: String): Claims {
        return try {
            Jwts.parser().verifyWith(secret as SecretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

}