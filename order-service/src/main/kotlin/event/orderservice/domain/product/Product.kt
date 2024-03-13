package event.orderservice.domain.product

import event.orderservice.domain.BaseEntity
import jakarta.persistence.*

@Entity
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val name: String,
    val price: Int,
    val stockQuantity: Int,
    @Enumerated(EnumType.STRING)
    val category: Category
) : BaseEntity()

enum class Category(val description: String) {
    ELECTRONICS("전자"),
    CLOTHING("의류"),
    BOOKS("책"),
    HOME_APPLIANCES("가전"),
    SPORTS("스포츠")
}