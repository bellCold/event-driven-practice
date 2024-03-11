package event.orderservice.application.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import event.orderservice.domain.order.Order
import event.orderservice.global.logger.logger
import jakarta.annotation.PostConstruct
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    val log = logger()

    @PostConstruct
    fun postInitOrderSender() {
        println("postInitOrderSender")
    }

    fun orderPlaced(order: Order) {
        objectMapper.registerKotlinModule()

        val kafkaSendOrderEventDto = KafkaSendOrderEventDto(
            bulletAccountId = order.bulletAccountId,
            orderId = order.id
        )

        println(kafkaSendOrderEventDto)

        val jsonInString = objectMapper.writeValueAsString(kafkaSendOrderEventDto)
        kafkaTemplate.send("orderPlaced", jsonInString)
        log.info("producer order event message : {}", kafkaSendOrderEventDto)
    }

    fun orderCanceled(order: Order) {
        KafkaSendOrderEventDto(
            bulletAccountId = order.bulletAccountId,
            orderId = order.id!!
        )
    }


    data class KafkaSendOrderEventDto(
        private val bulletAccountId: Long,
        private val orderId: Long?
    )
}