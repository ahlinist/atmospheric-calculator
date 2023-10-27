import TemperatureCalculator from "./temperatureCalculator.js";
import PressureCalculator from "./pressureCalculator.js";
import DensityCalculator from "./densityCalculator.js";

import AtmosphericParametersCalculator from "./atmosphericParametersCalculator.js";

const atmosphericParametersCalculator = new AtmosphericParametersCalculator(
    new TemperatureCalculator(), new PressureCalculator(), new DensityCalculator());

export function calculateAtmosphere(height) {
    return atmosphericParametersCalculator.calculate(height);
}
