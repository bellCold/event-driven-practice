package event.orderservice.domain.order

import event.orderservice.application.kafka.OrderSender
import event.orderservice.global.logger.logger
import jakarta.persistence.PostLoad
import jakarta.persistence.PostUpdate
import org.springframework.context.annotation.Lazy

class OrderListener(@Lazy private val orderSender: OrderSender) {
    private val log = logger()

    @PostLoad
    @PostUpdate
    fun eventPubByOrder(order: Order) {
        log.info("[OrderListener] {}", OrderStatus.ORDER_PLACED)
        if (order.orderStatus == OrderStatus.ORDER_PLACED) {
            try {
                orderSender.orderPlaced(order)
            } catch (e: Exception) {
                throw IllegalArgumentException()
            }
        }

        if (order.orderStatus == OrderStatus.CANCELLED) {
            try {
                orderSender.orderCanceled(order)
            } catch (e: Exception) {
                throw IllegalArgumentException()
            }
        }
    }

}