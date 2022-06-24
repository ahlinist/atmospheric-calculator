package atmospheric.calculator.controllers

import atmospheric.calculator.constants.SeaLevel
import atmospheric.calculator.enums.Layer
import atmospheric.calculator.model.CalculationResult
import atmospheric.calculator.services.CalculatorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestController
class CalculatorController {

    private final CalculatorService calculatorService

    CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService
    }

    @GetMapping("/calculate")
    CalculationResult calculate(@RequestParam BigDecimal height) {
        if (height < SeaLevel.HEIGHT || height > Layer.MESOSPHERE_LOW.maxHeight) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 'Height should be between 0 and 71000 m')
        }

        calculatorService.calculate(height)
    }

    @RestControllerAdvice
    class GlobalControllerExceptionHandler {

        @ExceptionHandler(NumberFormatException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        Map numberFormatException(NumberFormatException ex) {
            [message: 'Height should be an integer or decimal value']
        }
    }
}
