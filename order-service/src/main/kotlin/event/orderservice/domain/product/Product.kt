package event.orderservice.domain.product

import event.orderservice.api.error.ErrorCode
import event.orderservice.api.error.OrderServerException
import event.orderservice.domain.BaseEntity
import jakarta.persistence.*

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val name: String,
    val price: Int,
    var stockQuantity: Int,
    @Enumerated(EnumType.STRING)
    val productCategory: ProductCategory
) : BaseEntity() {
    fun decreaseStock(quantity: Int) {
        if (this.stockQuantity - quantity < 0) {
            throw OrderServerException(ErrorCode.STOCK_QUANTITY_SHORTAGE)
        }
        this.stockQuantity -= quantity
    }
}