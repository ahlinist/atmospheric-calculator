package atmospheric.calculator.components

import spock.lang.Specification
import spock.lang.Subject

class DensityCalculatorSpec extends Specification {

    @Subject
    DensityCalculator densityCalculator = new DensityCalculatorImpl()

    def "should calculate density based on temperature and pressure"() {
        expect:
        densityCalculator.calculate(temperature, pressure) == density

        where:
        temperature | pressure           | density
        288.15      | 101325             | 1.2250122660
        281.65      | 89874.45518056731  | 1.11165228222
        223.15      | 26435.887468345805 | 0.412704735495
        221.65      | 2510.9239839360007 | 0.0394646520581
        259.45      | 39.96666191931408  | 0.00053664446029
    }
}
