package event.orderservice.application.message

import com.fasterxml.jackson.databind.ObjectMapper
import event.orderservice.domain.order.Order
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

    fun orderPlaced(order: Order) {

        val kafkaSendOrderEventDto = KafkaSendOrderEventDto(
            bulletAccountId = order.bulletAccountId,
            orderId = order.id
        )

        val jsonInString = objectMapper.writeValueAsString(kafkaSendOrderEventDto)
        kafkaTemplate.send("orderPlaced", jsonInString)
        log.info("producer order event message : {}", kafkaSendOrderEventDto)
    }

    fun orderCanceled(order: Order) {
        KafkaSendOrderEventDto(
            bulletAccountId = order.bulletAccountId,
            orderId = order.id
        )
    }

    data class KafkaSendOrderEventDto(
        val bulletAccountId: Long,
        val orderId: Long?,
        val createAt: LocalDateTime = LocalDateTime.now()
    )
}