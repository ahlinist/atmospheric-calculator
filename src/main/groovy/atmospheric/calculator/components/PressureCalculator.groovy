package atmospheric.calculator.components

import groovy.transform.CompileStatic

@CompileStatic
interface PressureCalculator {

    BigDecimal calculate(BigDecimal basePressure, BigDecimal baseTemperature, BigDecimal temperature,
                         BigDecimal lapseRate, BigDecimal initialHeight, BigDecimal currentHeight)
}