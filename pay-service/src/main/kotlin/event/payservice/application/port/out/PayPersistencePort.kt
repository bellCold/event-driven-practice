package event.payservice.application.port.out

import event.payservice.domain.pay.Payment

interface PayPersistencePort {
    fun save(payment: Payment)
}