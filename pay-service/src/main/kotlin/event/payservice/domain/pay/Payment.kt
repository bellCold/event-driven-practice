package event.payservice.domain.pay

class Payment(
    val id: Long = 0,
    val orderId: Long,
    val bulletAccountId: Long,
    val paymentAmount: Int,
    val paymentMethod: PaymentMethod,
    val paymentStatus: PaymentStatus? = PaymentStatus.PENDING
)

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