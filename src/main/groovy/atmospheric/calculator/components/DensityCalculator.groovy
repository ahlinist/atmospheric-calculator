package atmospheric.calculator.components

import groovy.transform.CompileStatic

@CompileStatic
interface DensityCalculator {

    BigDecimal calculate(BigDecimal temperature, BigDecimal pressure)
}