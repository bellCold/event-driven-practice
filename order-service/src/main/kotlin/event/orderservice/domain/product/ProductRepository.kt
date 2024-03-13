package event.orderservice.domain.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByIdIn(mutableList: MutableList<Long>): MutableList<Product>?
}