package pt.unl.fct.iadi.orderprocessingplatform.payment

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt
import pt.unl.fct.iadi.orderprocessingplatform.domain.ReceiptStatus

@Component
@Profile("!prod")
class SandboxPaymentGateway : PaymentGateway {
    override fun processPayment(request: PaymentRequest): Receipt {
        val metadata = mapOf("gateway" to "sandbox", "amount" to request.amount)
        return Receipt(request.orderId, ReceiptStatus.PAID, metadata)
    }
}