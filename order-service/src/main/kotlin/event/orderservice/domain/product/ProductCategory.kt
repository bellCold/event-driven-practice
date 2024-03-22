package event.orderservice.domain.product

enum class ProductCategory(val description: String) {
    ELECTRONICS("전자"),
    CLOTHING("의류"),
    BOOKS("책"),
    HOME_APPLIANCES("가전"),
    SPORTS("스포츠")
}