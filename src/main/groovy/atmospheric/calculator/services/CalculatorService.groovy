package atmospheric.calculator.services

import atmospheric.calculator.model.CalculationResult

interface CalculatorService {

    CalculationResult calculate(BigDecimal height)
}