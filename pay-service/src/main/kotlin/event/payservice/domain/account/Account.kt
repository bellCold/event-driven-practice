package event.payservice.domain.account

import event.payservice.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    val bulletAccountId: Long,
    val deposit: Long
) : BaseEntity() {
}