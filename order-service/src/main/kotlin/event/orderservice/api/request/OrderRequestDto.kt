package event.orderservice.api.request

import event.orderservice.domain.order.Address

data class OrderRequestDto(
    val totalOrderAmount: Int,
    val address: Address,
    val products: List<ProductRequestDto>
)

data class ProductRequestDto(
    val productId: Long,
    val quantity: Int
)
