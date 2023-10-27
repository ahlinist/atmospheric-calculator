import { AIR_GAS_CONSTANT } from "../constants/generalConstants";

export default class DensityCalculator {
    calculateDensity(temperature, pressure) {
        return pressure / (AIR_GAS_CONSTANT * temperature);
    }
}
