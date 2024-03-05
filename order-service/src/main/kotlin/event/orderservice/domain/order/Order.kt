package event.orderservice.domain.order

import event.orderservice.domain.BaseEntity
import jakarta.persistence.*

@Table(name = "orders")
@Entity
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val bulletAccountId: Long,
    @Embedded
    val address: Address,
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus? = OrderStatus.ORDER_PLACED,
    val totalOrderAmount: Int
) : BaseEntity() {


    fun cancel() {
        this.orderStatus = OrderStatus.CANCELLED
    }
}

