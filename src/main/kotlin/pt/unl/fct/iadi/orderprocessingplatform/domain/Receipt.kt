package pt.unl.fct.iadi.orderprocessingplatform.domain

data class Receipt(
    private val orderId: String,
    private val status: ReceiptStatus,
    private val metadata: Map<String, Any>) {
}