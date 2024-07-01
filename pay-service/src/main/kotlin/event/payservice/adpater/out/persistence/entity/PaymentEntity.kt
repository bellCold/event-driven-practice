package event.payservice.adpater.out.persistence.entity

import event.payservice.domain.pay.Payment
import event.payservice.domain.pay.PaymentMethod
import event.payservice.domain.pay.PaymentStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class PaymentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val bulletAccountId: Long,
    val orderId: Long,
    val paymentDate: LocalDateTime = LocalDateTime.now(),
    val paymentAmount: Int,
    @Enumerated(EnumType.STRING)
    val paymentMethod: PaymentMethod,
    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus? = PaymentStatus.PENDING
) : BaseEntity() {
    companion object {
        fun of(payment: Payment): PaymentEntity {
            return PaymentEntity(
                orderId = payment.orderId,
                bulletAccountId = payment.bulletAccountId,
                paymentAmount = payment.paymentAmount,
                paymentMethod = payment.paymentMethod
            )
        }
    }
}