import { TEMPERATURE, PRESSURE, DENSITY, HEIGHT } from "../constants/airSeaLevelConstants";
import { TROPOSHPERE, TROPOPAUSE, STRATOSPHERE_LOW, STRATOSPHERE_HIGH, STRATOPAUSE, MESOSPHERE_LOW } from "../constants/layer";
import CalculationResult from "./calculationResult";

export default class AtmosphericParametersCalculator {
    constructor(temperatureCalculator, pressureCalculator, densityCalculator) {
        this.temperatureCalculator = temperatureCalculator;
        this.pressureCalculator = pressureCalculator;
        this.densityCalculator = densityCalculator;
    }

    calculate(height) {
        const LAYERS = [TROPOSHPERE, TROPOPAUSE, STRATOSPHERE_LOW, STRATOSPHERE_HIGH, STRATOPAUSE, MESOSPHERE_LOW];
        let finalIteration = false;
        let baseTemperature = TEMPERATURE;
        let basePressure = PRESSURE;
        let density = DENSITY;
        let initialHeight = HEIGHT;
        let currentHeight = HEIGHT;

        for (const layer of LAYERS) {
            if (initialHeight >= height) {
                break;
            }

            if (layer.maxHeight <= height) {
                currentHeight = layer.maxHeight;
            } else {
                currentHeight = height;
                finalIteration = true;
            }
    
            const lapseRate = layer.lapseRate;
            const temperature = temperatureCalculator.calculate(baseTemperature, lapseRate, initialHeight, currentHeight);
            const pressure = pressureCalculator.calculate(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight);
            density = densityCalculator.calculate(temperature, pressure);
    
            baseTemperature = temperature
            basePressure = pressure
    
            if (finalIteration) {
                break
            } else {
                initialHeight = layer.maxHeight
            }
        }
    
        return new CalculationResult(baseTemperature, basePressure, density);
    }
}
