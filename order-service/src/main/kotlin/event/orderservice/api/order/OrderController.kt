package event.orderservice.api.order

import event.orderservice.api.request.OrderRequestDto
import event.orderservice.application.OrderService
import event.orderservice.global.annotation.AuthenticatedBulletUserId
import event.orderservice.global.dto.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class OrderController(private val orderService: OrderService) {

    @PostMapping("/orders")
    fun createOrder(@AuthenticatedBulletUserId bulletAccountId: Long, @RequestBody orderRequestDto: OrderRequestDto): ResponseEntity<Result<Nothing>> {
        orderService.createOrder(bulletAccountId, orderRequestDto)
        return ResponseEntity.status(HttpStatus.OK).body(Result.success())
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