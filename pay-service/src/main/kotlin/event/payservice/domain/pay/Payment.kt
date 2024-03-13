package event.payservice.domain.pay

import event.payservice.domain.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Payment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val bulletAccountId: Long,
    val orderId: Long,
    val paymentDate: LocalDateTime,
    val paymentAmount: Int,
    @Enumerated(EnumType.STRING)
    val paymentMethod: PaymentMethod,
    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus? = PaymentStatus.PENDING
) : BaseEntity() {
}

enum class PaymentMethod {
    CARD,
    BANK,
    CASH
}

enum class PaymentStatus {
    PENDING,
    SUCCESS,
    FAIL
}