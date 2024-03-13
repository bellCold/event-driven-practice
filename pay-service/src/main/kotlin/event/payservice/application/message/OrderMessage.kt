package event.payservice.application.message

import com.fasterxml.jackson.databind.ObjectMapper
import event.payservice.global.logger.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderMessage(private val objectMapper: ObjectMapper) {

    val log = logger()

    @Transactional
    @KafkaListener(topics = ["orderPlaced"])
    fun orderEventConsumer(message: String) {
        log.info("event >>>>>>>>>>>>>>>>>>>> {}", message)

    }
}