package atmospheric.calculator.services

import atmospheric.calculator.components.DensityCalculator
import atmospheric.calculator.components.PressureCalculator
import atmospheric.calculator.components.TemperatureCalculator
import atmospheric.calculator.model.CalculationResult
import spock.lang.Specification
import spock.lang.Subject

class CalculatorServiceSpec extends Specification {

    TemperatureCalculator temperatureCalculator = Mock(TemperatureCalculator)
    PressureCalculator pressureCalculator = Mock(PressureCalculator)
    DensityCalculator densityCalculator = Mock(DensityCalculator)

    @Subject
    CalculatorService calculatorService = new CalculatorServiceImpl(temperatureCalculator, pressureCalculator, densityCalculator)

    def "should return result bean and invoke calculators for 1 m height"() {
        given:
        BigDecimal height = 1
        BigDecimal temperature = 288.1435
        BigDecimal pressure = 101312.98730778921
        BigDecimal density = 1.22489466417

        when:
        CalculationResult result = calculatorService.calculate(height)

        then:
        result.density == density
        result.temperature == temperature
        result.pressure == pressure

        and:
        1 * temperatureCalculator.calculate(288.15, -0.0065, 0, 1) >> temperature
        1 * pressureCalculator.calculate(101325, 288.15, temperature, -0.0065, 0, 1) >> pressure
        1 * densityCalculator.calculate(temperature, pressure) >> density
        0 * _
    }

    def "should return result bean and invoke calculators for 15000 m height"() {
        given:
        BigDecimal height = 15000
        BigDecimal temperature = 216.65
        BigDecimal pressure = 12044.296338503767
        BigDecimal density = 0.193671264359

        when:
        CalculationResult result = calculatorService.calculate(height)

        then:
        result.density == density
        result.temperature == temperature
        result.pressure == pressure

        and:
        1 * temperatureCalculator.calculate(288.15, -0.0065, 0, 11000) >> 216.65
        1 * pressureCalculator.calculate(101325, 288.15, 216.65, -0.0065, 0, 11000) >> 22631.700908231855
        1 * densityCalculator.calculate(216.65, 22631.700908231855) >> 0.36391583255
        1 * temperatureCalculator.calculate(216.65, 0, 11000, height) >> temperature
        1 * pressureCalculator.calculate(22631.700908231855, 216.65, 216.65, 0, 11000, 15000) >> pressure
        1 * densityCalculator.calculate(216.65, 12044.296338503767) >> density
        0 * _
    }
}
