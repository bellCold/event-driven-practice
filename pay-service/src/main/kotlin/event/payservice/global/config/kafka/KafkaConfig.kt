package event.payservice.global.config.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler

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
        properties[ConsumerConfig.GROUP_ID_CONFIG] = "consumerGroupId"
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(properties)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String>? {
        val kafkaListenerContainerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
        kafkaListenerContainerFactory.consumerFactory = consumerFactory()
        kafkaListenerContainerFactory.setCommonErrorHandler(errorHandler())
        return kafkaListenerContainerFactory
    }

    @Bean
    fun errorHandler(): DefaultErrorHandler {
        return DefaultErrorHandler()
    }
}