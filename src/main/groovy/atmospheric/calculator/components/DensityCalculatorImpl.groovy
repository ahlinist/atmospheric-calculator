package atmospheric.calculator.components

import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import static atmospheric.calculator.constants.General.AIR_GAS_CONSTANT

@Component
@CompileStatic
class DensityCalculatorImpl implements DensityCalculator {

    @Override
    BigDecimal calculate(BigDecimal temperature, BigDecimal pressure) {
        pressure / (AIR_GAS_CONSTANT * temperature)
    }
}
