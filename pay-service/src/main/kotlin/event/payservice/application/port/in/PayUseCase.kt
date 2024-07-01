package event.payservice.application.port.`in`

import event.payservice.application.port.PaymentCommand

interface PayUseCase {
    fun pay(paymentCommand: PaymentCommand)
}