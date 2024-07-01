package event.payservice.adpater.out.persistence.jpaRepository

import event.payservice.adpater.out.persistence.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentEntityJpaRepository: JpaRepository<PaymentEntity, Long> {
}