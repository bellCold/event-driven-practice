package event.payservice.adpater.out.persistence

import event.payservice.adpater.out.persistence.entity.PaymentEntity
import event.payservice.adpater.out.persistence.jpaRepository.PaymentEntityJpaRepository
import event.payservice.application.port.out.PayPersistencePort
import event.payservice.domain.pay.Payment
import org.springframework.stereotype.Repository

@Repository
class PayPersistenceAdapter(
    private val paymentEntityJpaRepository: PaymentEntityJpaRepository
) : PayPersistencePort {
    override fun save(payment: Payment) {
        paymentEntityJpaRepository.save(PaymentEntity.of(payment))
    }

}