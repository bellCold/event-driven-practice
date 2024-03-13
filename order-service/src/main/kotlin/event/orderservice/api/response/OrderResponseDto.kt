package event.orderservice.api.response

import event.orderservice.domain.order.Order
import event.orderservice.domain.order.OrderStatus
import java.time.LocalDateTime

data class OrderResponseDto(
    val orderId: Long?,
    val orderStatus: OrderStatus?,
    val orderDate: LocalDateTime,
    val totalOrderAmount: Int
) {
    companion object {
        fun domainToDto(findOrder: Order): OrderResponseDto {
            return OrderResponseDto(
                orderId = findOrder.id,
                orderStatus = findOrder.orderStatus,
                totalOrderAmount = findOrder.totalOrderAmount,
                orderDate = findOrder.createdAt
            )
        }
    }
}
