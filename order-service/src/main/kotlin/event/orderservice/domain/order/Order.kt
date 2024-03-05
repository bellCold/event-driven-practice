package event.orderservice.domain.order

import event.orderservice.domain.BaseEntity
import jakarta.persistence.*
import java.time.LocalDate

@Table(name = "orders")
@Entity
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val bulletAccountId: Long,
    @Embedded
    val address: Address,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus? = OrderStatus.ORDER_PLACED,
    val totalOrderAmount: Int,
    val orderDate: LocalDate
) : BaseEntity() {
}

