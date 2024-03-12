package event.userservice.global.security

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import event.userservice.application.RefreshTokenService
import event.userservice.global.constant.TIME_FORMAT
import event.userservice.global.dto.LoginRequest
import event.userservice.global.logger.logger
import event.userservice.global.utils.CookieProvider
import event.userservice.global.utils.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat


@Component
class LoginAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val cookieProvider: CookieProvider,
    private val authenticationManager: AuthenticationManager,
    private val refreshTokenService: RefreshTokenService
) : UsernamePasswordAuthenticationFilter() {
    init {
        super.setAuthenticationManager(authenticationManager)
    }

    val log = logger()

    // login 리퀘스트 패스로 오는 요청을 판단
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        log.info("login request")
        return try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.email,
                    loginRequest.password
                )
            )
        } catch (e: Exception) {
            when (e) {
                is RuntimeException -> throw e
                else -> throw RuntimeException("Failed to parse authentication request body", e)
            }
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        log.info("login successfulAuthentication")

        val user = authResult.principal as User

        val roles = user.authorities.map { it.authority }.toList()
        val accessToken = jwtTokenProvider.createAccessToken(user.username, request.requestURI, roles)
        val expiredTime = jwtTokenProvider.getExpiredTime(accessToken)
        val refreshToken = jwtTokenProvider.createRefreshToken()
        refreshTokenService.updateRefreshToken(user.username.toLong(), jwtTokenProvider.getRefreshTokenId(refreshToken))

        val refreshTokenCookie = cookieProvider.createRefreshTokenCookie(refreshToken)

        val cookie = cookieProvider.of(refreshTokenCookie)
        response.contentType = APPLICATION_JSON_VALUE
        response.addCookie(cookie)

        val tokens = mapOf(
            "accessToken" to accessToken,
            "expiredTime" to SimpleDateFormat(TIME_FORMAT).format(expiredTime)
        )

        jacksonObjectMapper().configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
        jacksonObjectMapper().writeValue(response.outputStream, tokens)
    }
}
