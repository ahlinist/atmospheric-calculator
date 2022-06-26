package atmospheric.calculator.controllers

import atmospheric.calculator.model.CalculationResult
import atmospheric.calculator.services.CalculatorService
import spock.lang.Specification
import spock.lang.Subject

class CalculatorControllerSpec extends Specification {

    CalculatorService calculatorService = Mock(CalculatorService)

    @Subject
    CalculatorController calculatorController = new CalculatorController(calculatorService)

    def "should accept height and return the result bean"() {
        given:
        BigDecimal height = 1
        CalculationResult result = new CalculationResult()

        when:
        CalculationResult actual = calculatorController.calculate(1)

        then:
        result == actual

        and:
        1 * calculatorService.calculate(height) >> result
        0 * _
    }
}
