package event.orderservice.application.message

import com.fasterxml.jackson.databind.ObjectMapper
import event.orderservice.domain.order.Order
import event.orderservice.domain.order.OrderStatus
import event.orderservice.global.logger.logger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    val log = logger()

    fun sendOrderEvent(order: Order) {
        val eventTopic = when (order.orderStatus) {
            OrderStatus.ORDER_PLACED -> "orderPlaced"
            OrderStatus.CANCELLED -> "orderCancel"
            else -> throw IllegalStateException("does not exist order status")
        }

        val kafkaSendOrderEventDto = KafkaSendOrderEventDto(
            bulletAccountId = order.bulletAccountId,
            orderId = order.id
        )
        val jsonInString = objectMapper.writeValueAsString(kafkaSendOrderEventDto)

        kafkaTemplate.send(eventTopic, jsonInString)
        log.info("Producer event topic '{}' message: '{}'", eventTopic, kafkaSendOrderEventDto)
    }

    data class KafkaSendOrderEventDto(
        val bulletAccountId: Long,
        val orderId: Long?,
        val eventTime: LocalDateTime = LocalDateTime.now()
    )
}