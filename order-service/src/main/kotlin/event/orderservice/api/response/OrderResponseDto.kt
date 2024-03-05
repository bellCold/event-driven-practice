package event.orderservice.api.response

import java.time.LocalDateTime

data class OrderResponseDto(
    val orderDate: LocalDateTime,
    val totalOrderAmount: Int
)
