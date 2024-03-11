package event.orderservice.domain.order

import event.orderservice.application.message.OrderSender
import event.orderservice.global.logger.logger
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.springframework.beans.factory.annotation.Autowired

class OrderListener {

    @Autowired
    private lateinit var orderSender: OrderSender
    private val log = logger()

    @PostLoad
    @PostPersist
    fun eventPubByOrder(order: Order) {
        log.info("[OrderListener] {}", OrderStatus.ORDER_PLACED)
        log.info("order-id {}", order.id)
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