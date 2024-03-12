package event.userservice.api.auth

import event.userservice.api.response.auth.JwtTokenResponseDto
import event.userservice.application.RefreshTokenService
import event.userservice.api.Result
import event.userservice.global.utils.CookieProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthController(private val refreshTokenService: RefreshTokenService, private val cookieProvider: CookieProvider) {

    @PostMapping("/logout")
    fun logout(@RequestHeader(value = "X-AUTH-TOKEN") accessToken: String): ResponseEntity<Result<*>> {
        refreshTokenService.logoutToken(accessToken)
        val refreshCookie = cookieProvider.removeRefreshTokenCookie()
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(Result.success())
    }

    @GetMapping("/reissue")
    fun refreshToken(
        @RequestHeader(value = "X-AUTH-TOKEN") accessToken: String,
        @CookieValue(value = "refresh-token") refreshToken: String
    ): ResponseEntity<Result<JwtTokenResponseDto>> {
        val jwtTokenDto = refreshTokenService.refreshJwtToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )

        val responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken)
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .body(Result.success(jwtTokenDto))
    }

}