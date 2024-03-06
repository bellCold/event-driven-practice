package event.apigateway.global.config

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import reactor.core.publisher.Mono

@Configuration
class GatewayConfig {

    @Bean
    fun tokenKeyResolver(): KeyResolver {
        return KeyResolver { exchange ->
            Mono.just(exchange.request.headers[HttpHeaders.AUTHORIZATION]!![0])
        }
    }
}