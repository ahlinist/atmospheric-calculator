package atmospheric.calculator.components

import spock.lang.Specification
import spock.lang.Subject

class PressureCalculatorSpec extends Specification {

    @Subject
    PressureCalculator pressureCalculator = new PressureCalculatorImpl()

    def "should return pressure based on input values"() {
        expect:
        pressureCalculator.calculate(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight) == pressure

        where:
        basePressure       | baseTemperature | temperature | lapseRate | initialHeight | currentHeight | pressure
        101325             | 288.15          | 288.15      | -0.0065   | 0             | 0             | 101325
        101325             | 288.15          | 223.15      | -0.0065   | 0             | 10000         | 26435.887468345805
        22631.700908231855 | 216.65          | 223.15      | 0         | 11000         | 15000         | 12044.296338503767
        5474.717688205542  | 216.65          | 221.65      | 0.001     | 20000         | 25000         | 2510.9239839360007
        867.974467329669   | 228.65          | 265.05      | 0.0028    | 32000         | 45000         | 143.1247749176813
    }
}
