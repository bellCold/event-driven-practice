package event.payservice.global.config.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConfig(
    @Value("\${kafka.host}")
    private val kafkaServerHost: String,
    @Value("\${kafka.port}")
    private val kafkaServerPort: String
) {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val properties = mutableMapOf<String, Any>()
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "$kafkaServerHost:$kafkaServerPort"
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ConsumerConfig.GROUP_ID_CONFIG] = "consumerGroupId"
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaConsumerFactory(properties)
    }
}