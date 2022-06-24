package atmospheric.calculator.controllers

import atmospheric.calculator.constants.SeaLevel
import atmospheric.calculator.enums.Layer
import atmospheric.calculator.model.CalculationResult
import atmospheric.calculator.services.CalculatorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CalculatorController {

    private final CalculatorService calculatorService

    CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService
    }

    @GetMapping("/calculate")
    CalculationResult calculate(@RequestParam BigDecimal height) {
        if (height < SeaLevel.HEIGHT || height > Layer.MESOSPHERE_LOW.maxHeight) {
            return 'Height should be between 0 and 71000 m'
        }

        calculatorService.calculate(height)
    }
}
