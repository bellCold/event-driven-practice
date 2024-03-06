package event.orderservice.application

import event.orderservice.api.request.OrderRequestDto
import event.orderservice.api.response.OrderResponseDto
import event.orderservice.domain.order.Order
import event.orderservice.domain.order.OrderRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

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
        val findOrder = findOrder(orderId)

        return OrderResponseDto(
            totalOrderAmount = findOrder.totalOrderAmount,
            orderDate = LocalDateTime.now()
        )
    }

    @Transactional
    fun orderCancel(orderId: Long) {
        val order = findOrder(orderId)
        order.cancel()
    }

    private fun findOrder(orderId: Long): Order {
        return orderRepository.findById(orderId).orElseThrow { throw NotFoundException() }
    }

}
