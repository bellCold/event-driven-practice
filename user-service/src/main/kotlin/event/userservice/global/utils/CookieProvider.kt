package event.userservice.global.utils

import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class CookieProvider(@Value("\${token.refresh-expired-time}") val refreshTokenExpiredTime: String) {

    fun createRefreshTokenCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from("refresh-token", refreshToken)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(refreshTokenExpiredTime.toLong()).build()
    }

    fun removeRefreshTokenCookie(): ResponseCookie {
        return ResponseCookie.from("refresh-token", "")
            .maxAge(0)
            .path("/")
            .build()
    }

    fun of(responseCookie: ResponseCookie): Cookie {
        return Cookie(responseCookie.name, responseCookie.value).apply {
            path = responseCookie.path
            secure = responseCookie.isSecure
            isHttpOnly = responseCookie.isHttpOnly
            maxAge = responseCookie.maxAge.seconds.toInt()
        }
    }
}