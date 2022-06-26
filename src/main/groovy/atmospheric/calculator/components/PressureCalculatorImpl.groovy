package atmospheric.calculator.components

import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import static atmospheric.calculator.constants.General.GRAVITATIONAL_ACCELERATION
import static atmospheric.calculator.constants.General.AIR_GAS_CONSTANT

@Component
@CompileStatic
class PressureCalculatorImpl implements PressureCalculator{

    @Override
    BigDecimal calculate(BigDecimal basePressure, BigDecimal baseTemperature, BigDecimal temperature,
                         BigDecimal lapseRate, BigDecimal initialHeight, BigDecimal currentHeight) {
        if (lapseRate) {
            return basePressure * (temperature/baseTemperature)**(-GRAVITATIONAL_ACCELERATION/(lapseRate * AIR_GAS_CONSTANT))
        } else {
            BigDecimal argument = -(currentHeight - initialHeight) * GRAVITATIONAL_ACCELERATION / (AIR_GAS_CONSTANT * baseTemperature)
            return basePressure * Math.exp(argument.toDouble())
        }
    }
}
