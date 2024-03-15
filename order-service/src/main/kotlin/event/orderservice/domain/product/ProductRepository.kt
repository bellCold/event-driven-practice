package event.orderservice.domain.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByIdIn(productIds: List<Long>): MutableList<Product>?
}