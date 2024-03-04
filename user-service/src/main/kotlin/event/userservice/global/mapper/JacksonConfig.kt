/*
package event.userservice.global.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.std.StringSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            StringSerializer() // keySerializer 설정
        }
    }

    @Bean
    fun mappingJackson2HttpMessageConverter(objectMapper: ObjectMapper): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter(objectMapper)
    }
}*/
