package event.userservice.global.security

import event.userservice.global.logger.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class HeaderAuthorizationFilter : OncePerRequestFilter() {

    private val log = logger()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath == "/api/v1/login") {
            filterChain.doFilter(request, response)
            return
        }

        val email = request.getHeader("jwt-sub")
        log.info("email jwt-sub ={}", email)
        filterChain.doFilter(request, response)
    }
}