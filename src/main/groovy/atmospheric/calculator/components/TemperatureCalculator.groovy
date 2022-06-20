package atmospheric.calculator.components

import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@Component
@CompileStatic
class TemperatureCalculator {

    BigDecimal calculate(BigDecimal baseTemperature, BigDecimal lapseRate, BigDecimal initialHeight, BigDecimal currentHeight) {
        baseTemperature + (lapseRate * (currentHeight - initialHeight))
    }
}
