package atmospheric.calculator.components

import groovy.transform.CompileStatic

@CompileStatic
interface TemperatureCalculator {

    BigDecimal calculate(BigDecimal baseTemperature, BigDecimal lapseRate, BigDecimal initialHeight, BigDecimal currentHeight)
}