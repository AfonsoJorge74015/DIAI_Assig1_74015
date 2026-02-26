package pt.unl.fct.iadi.orderprocessingplatform.domain

data class PaymentRequest(
    private val orderId: String,
    private val amount: Double) {
}