package event.orderservice.domain.association

import event.orderservice.domain.order.Order
import event.orderservice.domain.product.Product
import jakarta.persistence.*

@Entity
class OrderProduct(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    @ManyToOne
    @JoinColumn(name = "order_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val order: Order,
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val product: Product
) {
}