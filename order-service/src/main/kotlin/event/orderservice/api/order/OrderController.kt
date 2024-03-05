package event.orderservice.api.order

import event.orderservice.api.request.OrderRequestDto
import event.orderservice.application.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class OrderController(private val orderService: OrderService) {

    /**
     * 주문 생성,
     * 주문 조회,
     * 주문 취소
     * 동시성, 멱등성
     */

    @PostMapping("/orders")
    fun createOrder(bulletAccountId: Long, @RequestBody orderRequestDto: OrderRequestDto) {
        orderService.createOrder(bulletAccountId, orderRequestDto)
    }

    @GetMapping("/orders/{orderId}")
    fun getOrder(@PathVariable orderId: Long) {
        orderService.getOrder(orderId)
    }

    @DeleteMapping("/orders/{orderId}")
    fun orderCancel(@PathVariable orderId: Long) {
        orderService.orderCancel(orderId)
    }
}