package event.userservice.api.auth

import event.userservice.application.RefreshTokenService
import event.userservice.global.dto.Result
import event.userservice.global.utils.CookieProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(private val refreshTokenService: RefreshTokenService, private val cookieProvider: CookieProvider) {

    @PostMapping("/logout")
    fun logout(@RequestHeader("X-AUTH-TOKEN") accessToken: String): ResponseEntity<Result> {
        refreshTokenService.logoutToken(accessToken)
        val refreshCookie = cookieProvider.removeRefreshTokenCookie()
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(Result.success())
    }

}