package event.orderservice.api.request

import event.orderservice.domain.order.Address

data class OrderRequestDto(
    val totalOrderAmount: Int,
    val address: Address,
    val productIds: MutableList<Long>
)
