package pt.unl.fct.iadi.orderprocessingplatform

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.payment.PaymentGateway
import pt.unl.fct.iadi.orderprocessingplatform.pricing.PriceCalculator
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class OrderProcessor(
    private val calculator: PriceCalculator,
    private val paymentGateway: PaymentGateway)
    : CommandLineRunner {

    fun processOrder(order: Order) : List<String> {
        val totalPrice = BigDecimal(calculator.calculateTotalPrice(order))
            .setScale(2, RoundingMode.HALF_UP).toDouble()

        val receipt = paymentGateway.processPayment(
            PaymentRequest(order.id, totalPrice))

        val result = mutableListOf<String>()

        result.add("")
        result.add("Order ID: ${order.id}")
        result.add("User ID: ${order.userId}")
        result.add("Created at: ${order.createdAt}")
        result.add("")

        result.add("Items:")
        order.items.forEach { item ->
            val itemTotal = BigDecimal(item.quantity * item.price)
                .setScale(2, RoundingMode.HALF_UP)
            result.add("  - ${item.productId}: ${item.quantity} x \$${item.price} = \$$itemTotal")
        }
        result.add("")

        result.add("Total Price: \$$totalPrice")
        result.add("Calculator Used: ${calculator.javaClass.simpleName}")
        result.add("")

        result.add("Payment Status: ${receipt.status}")
        result.add("Payment Gateway: ${receipt.metadata["gateway"]}")

        result.add("")
        result.add("=== Processing Complete ===")

        return result
    }

    override fun run(vararg args: String?) {
        val example = Order(
            id = "ORD-2026-001",
            userId = "user123",
            items = listOf(
                Order.OrderItem("LAPTOP-001", 2, 999.99),
                Order.OrderItem("MOUSE-042", 3, 29.99),
                Order.OrderItem("KEYBOARD-123", 1, 149.99)
            )
        )
        processOrder(example).forEach { println(it) }
    }
}