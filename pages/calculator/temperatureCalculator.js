export default class TemperatureCalculator {
    calculateTemperature(baseTemperature, lapseRate, initialHeight, currentHeight) {
        return baseTemperature + (lapseRate * (currentHeight - initialHeight));
    }
}
