package atmospheric.calculator.components

import spock.lang.Specification
import spock.lang.Subject

class TemperatureCalculatorSpec extends Specification {

    @Subject
    TemperatureCalculator temperatureCalculator = new TemperatureCalculatorImpl()

    def "should return temperature for different heights"() {
        expect:
        temperatureCalculator.calculate(baseTemperature, lapseRate, initialHeight, currentHeight) == temperature

        where:
        baseTemperature | lapseRate | initialHeight | currentHeight | temperature
        288.15          | -0.0065   | 0             | 0             | 288.15
        288.15          | -0.0065   | 0             | 1000          | 281.65
        216.65          | 0         | 11000         | 12000         | 216.65
        216.65          | 0.001     | 20000         | 25000         | 221.65
        270.65          | -0.0028   | 51000         | 60000         | 245.45
    }
}
