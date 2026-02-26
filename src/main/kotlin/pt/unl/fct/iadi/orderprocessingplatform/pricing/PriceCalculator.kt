package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.security.SecurityProperties
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

interface PriceCalculator {
    fun calculateTotalPrice(order: Order): Double
}