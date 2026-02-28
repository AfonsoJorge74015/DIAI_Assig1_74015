package pt.unl.fct.iadi.orderprocessingplatform.payment

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt
import pt.unl.fct.iadi.orderprocessingplatform.domain.ReceiptStatus
import java.util.UUID

@Component
@Profile("prod")
class StripeLikePaymentGateway : PaymentGateway {
    override fun processPayment(request: PaymentRequest): Receipt {
        return when {
            request.amount <= 0 -> {
                val metadata = mapOf("gateway" to "stripe-like", "reason" to "Invalid amount", "amount" to request.amount)
                Receipt(request.orderId, ReceiptStatus.REJECTED, metadata)
            }
            request.amount > 10000 -> {
                val metadata = mapOf("gateway" to "stripe-like", "reason" to "High value transaction requires review", "amount" to request.amount)
                Receipt(request.orderId, ReceiptStatus.FLAGGED_FOR_REVIEW, metadata)
            }
            else -> {
                val metadata = mapOf("gateway" to "stripe-like", "transactionId" to UUID.randomUUID().toString(), "amount" to request.amount)
                Receipt(request.orderId, ReceiptStatus.PAID, metadata)
            }
        }
    }
}