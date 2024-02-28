package event.apigateway.filter

import event.apigateway.global.logger.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {
    val log = logger();

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val response = exchange.response
            log.info(">>>>>>>>>>>> Global filter message: {} <<<<<<<<<<<<", config.baseMessage)

            if (config.preLogger) {
                log.info("Global Filter Start: request id -> {}", request.id)
                log.info("Global Filter Start: request path -> {}", request.path)
            }

            chain.filter(exchange)
                .then(Mono.fromRunnable {
                    if (config.postLogger) {
                        log.info(">>>>>>>>>>>> Global Filter End: response statusCode -> {} <<<<<<<<<<<<", response.statusCode)
                    }
                })
        }

    }

    data class Config(
        val baseMessage: String?,
        val preLogger: Boolean,
        val postLogger: Boolean,
    )
}

