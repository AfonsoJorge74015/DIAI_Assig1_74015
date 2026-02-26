package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

@Component
@ConditionalOnProperty(name=["pricing.promo.enabled"], havingValue = "true")
class PromoPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        return order.items.sumOf { item ->
            if(item.quantity > 5)
                item.quantity * item.price * 0.8
            else
                item.quantity * item.price
        }
    }
}