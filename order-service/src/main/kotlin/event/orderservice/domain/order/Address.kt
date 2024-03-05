package event.orderservice.domain.order

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column
    val city: String,
    val street: String,
    val zipcode: String
)