package atmospheric.calculator.services

import atmospheric.calculator.components.DensityCalculator
import atmospheric.calculator.components.PressureCalculator
import atmospheric.calculator.components.TemperatureCalculator
import atmospheric.calculator.atmosphere.Layer
import atmospheric.calculator.model.CalculationResult
import org.springframework.stereotype.Service

import atmospheric.calculator.atmosphere.AirSeaLevelConstants

import static atmospheric.calculator.atmosphere.Layer.MESOSPHERE_LOW
import static atmospheric.calculator.atmosphere.Layer.STRATOPAUSE
import static atmospheric.calculator.atmosphere.Layer.STRATOSPHERE_LOW
import static atmospheric.calculator.atmosphere.Layer.STRATOSPHERE_HIGH
import static atmospheric.calculator.atmosphere.Layer.TROPOSHPERE
import static atmospheric.calculator.atmosphere.Layer.TROPOPAUSE

@Service
class CalculatorServiceImpl implements CalculatorService {

    private static final List<Layer> LAYERS = [
            TROPOSHPERE, TROPOPAUSE, STRATOSPHERE_LOW, STRATOSPHERE_HIGH, STRATOPAUSE, MESOSPHERE_LOW]

    private final TemperatureCalculator temperatureCalculator
    private final PressureCalculator pressureCalculator
    private final DensityCalculator densityCalculator

    CalculatorServiceImpl(TemperatureCalculator temperatureCalculator,
                          PressureCalculator pressureCalculator, DensityCalculator densityCalculator) {
        this.temperatureCalculator = temperatureCalculator
        this.pressureCalculator = pressureCalculator
        this.densityCalculator = densityCalculator
    }

    CalculationResult calculate(BigDecimal height) {
        Boolean finalIteration = Boolean.FALSE
        BigDecimal baseTemperature = AirSeaLevelConstants.TEMPERATURE
        BigDecimal basePressure = AirSeaLevelConstants.PRESSURE
        BigDecimal density = AirSeaLevelConstants.DENSITY
        BigDecimal initialHeight = AirSeaLevelConstants.HEIGHT
        BigDecimal currentHeight = AirSeaLevelConstants.HEIGHT

        for (Layer layer in LAYERS) {
            if (initialHeight >= height) {
                break
            }

            if (layer.maxHeight <= height) {
                currentHeight = layer.maxHeight
            } else {
                currentHeight = height
                finalIteration = Boolean.TRUE
            }

            BigDecimal lapseRate = layer.lapseRate
            BigDecimal temperature = temperatureCalculator.calculate(baseTemperature, lapseRate, initialHeight, currentHeight)
            BigDecimal pressure = pressureCalculator.calculate(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight)
            density = densityCalculator.calculate(temperature, pressure)

            baseTemperature = temperature
            basePressure = pressure

            if (finalIteration) {
                break
            } else {
                initialHeight = layer.maxHeight
            }
        }

        new CalculationResult(temperature: baseTemperature, pressure: basePressure, density: density)
    }
}
