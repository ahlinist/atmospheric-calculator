import { GRAVITATIONAL_ACCELERATION, AIR_GAS_CONSTANT } from "../constants/generalConstants"

export default class PressureCalculator {
    calculatePressure(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight) {
        if (lapseRate) {
            return basePressure * (temperature/baseTemperature)**(-GRAVITATIONAL_ACCELERATION/(lapseRate * AIR_GAS_CONSTANT));
        } else {
            const exponentArgument = -(currentHeight - initialHeight) * GRAVITATIONAL_ACCELERATION / (AIR_GAS_CONSTANT * baseTemperature);
            return basePressure * Math.exp(exponentArgument);
        }
    }
}
