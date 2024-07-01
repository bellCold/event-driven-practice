package event.payservice.application.port

import event.payservice.domain.pay.PaymentMethod
import event.payservice.domain.pay.PaymentStatus

data class PaymentCommand(
    val orderId: Long,
    val bulletAccountId: Long,
    val paymentAmount: Int,
    val paymentMethod: PaymentMethod,
    val paymentStatus: PaymentStatus
)
