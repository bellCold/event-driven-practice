package event.payservice.adpater.`in`.web.payment

import event.payservice.adpater.`in`.web.payment.request.PayRequestDto
import event.payservice.application.port.`in`.PayUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController(private val payUseCase: PayUseCase){

    @PostMapping
    fun pay(@RequestBody payRequestDto: PayRequestDto) {
        payUseCase.pay(payRequestDto.toCommand())
    }
}