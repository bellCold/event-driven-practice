package event.payservice.adpater.`in`.web.payment.request

import event.payservice.application.port.PaymentCommand
import event.payservice.domain.pay.PaymentMethod
import event.payservice.domain.pay.PaymentStatus

data class PayRequestDto(
    val orderId: Long,
    val bulletAccountId: Long,
    val paymentAmount: Int,
    val paymentMethod: PaymentMethod,
    val paymentStatus: PaymentStatus
) {


    fun toCommand(): PaymentCommand {
        return PaymentCommand(
            orderId = this.orderId,
            bulletAccountId = this.bulletAccountId,
            paymentAmount = this.paymentAmount,
            paymentMethod = this.paymentMethod,
            paymentStatus = this.paymentStatus
        )
    }
}
