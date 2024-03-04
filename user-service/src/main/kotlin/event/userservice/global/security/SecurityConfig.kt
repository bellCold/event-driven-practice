package event.userservice.global.security

import event.userservice.application.RefreshTokenService
import event.userservice.global.utils.CookieProvider
import event.userservice.global.utils.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val cookieProvider: CookieProvider,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
    private val refreshTokenService: RefreshTokenService,
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val loginAuthenticationFilter = LoginAuthenticationFilter(
            jwtTokenProvider,
            cookieProvider,
            authenticationManager(),
            refreshTokenService
        ).apply { setFilterProcessesUrl("/api/v1/login") }

        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .logout {
                it.logoutUrl("/api/v1/logout")
                    .deleteCookies("refresh-token")
            }
            .userDetailsService(userDetailsService)
            .addFilter(loginAuthenticationFilter)

        return http.build()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}