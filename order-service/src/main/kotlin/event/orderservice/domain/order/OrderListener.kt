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
        try {
            orderSender.sendOrderEvent(order)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to process order: ${e.message}")
        }
    }
}