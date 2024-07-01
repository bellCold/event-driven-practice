package event.payservice.application.service

import event.payservice.application.port.PaymentCommand
import event.payservice.application.port.`in`.PayUseCase
import event.payservice.application.port.out.PayPersistencePort
import event.payservice.domain.pay.Payment
import org.springframework.stereotype.Service

@Service
class PayService(private val payPersistencePort: PayPersistencePort) : PayUseCase {
    override fun pay(paymentCommand: PaymentCommand) {
        val payment = Payment(
            orderId = paymentCommand.orderId,
            bulletAccountId = paymentCommand.bulletAccountId,
            paymentAmount = paymentCommand.paymentAmount,
            paymentMethod = paymentCommand.paymentMethod,
            paymentStatus = paymentCommand.paymentStatus
        )

        payPersistencePort.save(payment)
    }
}