package event.apigateway.filter

import event.apigateway.global.logger.logger
import event.apigateway.security.JwtTokenProvider
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(private val jwtTokenProvider: JwtTokenProvider) :
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    val log = logger();

    inner class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val headers = exchange.request.headers
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }

            val token = headers.getFirst(HttpHeaders.AUTHORIZATION)?.removePrefix("Bearer ")
                ?: return@GatewayFilter onError(exchange, "No authorization token found", HttpStatus.UNAUTHORIZED)

            jwtTokenProvider.validateJwtToken(token)

            val subject = jwtTokenProvider.getUserId(token)

            val newRequest = exchange.request.mutate()
                .header("user-id", subject)
                .build()

            chain.filter(exchange.mutate().request(newRequest).build())
        }
    }

    private fun onError(exchange: ServerWebExchange, errorMessage: String, httpStatus: HttpStatus): Mono<Void> {
        log.error(errorMessage)

        exchange.response.statusCode = httpStatus

        return exchange.response.setComplete()
    }
}