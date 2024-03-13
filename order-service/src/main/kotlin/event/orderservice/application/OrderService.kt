package event.orderservice.application

import event.orderservice.api.error.ErrorCode
import event.orderservice.api.error.OrderServerException
import event.orderservice.api.request.OrderRequestDto
import event.orderservice.api.response.OrderResponseDto
import event.orderservice.domain.order.Order
import event.orderservice.domain.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun createOrder(bulletAccountId: Long, orderRequestDto: OrderRequestDto) {
        val order = Order(
            bulletAccountId = bulletAccountId,
            totalOrderAmount = orderRequestDto.totalOrderAmount,
            address = orderRequestDto.address
        )

        orderRepository.save(order)
    }

    @Transactional(readOnly = true)
    fun getOrder(orderId: Long): OrderResponseDto {
        val findOrder = findOrderById(orderId)

        return OrderResponseDto.domainToDto(findOrder)
    }

    @Transactional
    fun orderCancel(orderId: Long) {
        val order = findOrderById(orderId)
        order.cancel()
    }

    private fun findOrderById(orderId: Long): Order {
        return orderRepository.findById(orderId).orElseThrow { throw OrderServerException(ErrorCode.ORDER_NOT_FOUND) }
    }

}
