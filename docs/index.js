const PRESSURE = 101325.0; // Pa
const TEMPERATURE = 288.15; // K
const DENSITY = 1.225; // kg/m^3
const HEIGHT = 0.0; // m

const GRAVITATIONAL_ACCELERATION = 9.80665; // m/s^2
const AIR_GAS_CONSTANT = 287.05; // J/(kg*K)

const TROPOSHPERE = { maxHeight: 11000.0, lapseRate: -0.0065 };
const TROPOPAUSE = { maxHeight: 20000.0, lapseRate: 0.0 };
const STRATOSPHERE_LOW = { maxHeight: 32000.0, lapseRate: 0.001 };
const STRATOSPHERE_HIGH = { maxHeight: 47000.0, lapseRate: 0.0028 };
const STRATOPAUSE = { maxHeight: 51000.0, lapseRate: 0.0 };
const MESOSPHERE_LOW = { maxHeight: 71000.0, lapseRate: -0.0028 };

class CalculationResult {
    constructor(temperature, pressure, density) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.density = density;
    }
}

class DensityCalculator {
    calculate(temperature, pressure) {
        return pressure / (AIR_GAS_CONSTANT * temperature);
    }
}

class PressureCalculator {
    calculate(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight) {
        if (lapseRate) {
            return basePressure * (temperature/baseTemperature)**(-GRAVITATIONAL_ACCELERATION/(lapseRate * AIR_GAS_CONSTANT));
        } else {
            const exponentArgument = -(currentHeight - initialHeight) * GRAVITATIONAL_ACCELERATION / (AIR_GAS_CONSTANT * baseTemperature);
            return basePressure * Math.exp(exponentArgument);
        }
    }
}

class TemperatureCalculator {
    calculate(baseTemperature, lapseRate, initialHeight, currentHeight) {
        return baseTemperature + (lapseRate * (currentHeight - initialHeight));
    }
}

class AtmosphericParametersCalculator {
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
            const temperature = this.temperatureCalculator.calculate(baseTemperature, lapseRate, initialHeight, currentHeight);
            const pressure = this.pressureCalculator.calculate(basePressure, baseTemperature, temperature, lapseRate, initialHeight, currentHeight);
            density = this.densityCalculator.calculate(temperature, pressure);
    
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

const atmosphericParametersCalculator = new AtmosphericParametersCalculator(
    new TemperatureCalculator(), new PressureCalculator(), new DensityCalculator());

const calculateAtmosphere = (height) => {
    return atmosphericParametersCalculator.calculate(height);
}

//----------------------------

const heightInput = document.querySelector("input[name=height]");
const temperatureContainer = document.querySelector("p#temperature");
const pressureContainer = document.querySelector("p#pressure");
const densityContainer = document.querySelector("p#density");
const errorContainer = document.querySelector("p#error");

const populateCalculationContainer = (data) => {
  errorContainer.innerHTML = "";
  temperatureContainer.innerHTML = `Temperature: ${data.temperature} K`;
  pressureContainer.innerHTML = `Pressure: ${data.pressure} Pa`;
  densityContainer.innerHTML = `Density: ${data.density} kg/m3`;
};

const displayError = (message) => {
    errorContainer.innerHTML = message;
    temperatureContainer.innerHTML = "";
    pressureContainer.innerHTML = "";
    densityContainer.innerHTML = "";
};

heightInput.oninput = (e) => {
  const height = e.target.value;

  if (height < HEIGHT || height > MESOSPHERE_LOW.maxHeight || isNaN(height)) {
    displayError("Height should be an integer or decimal value between 0 and 71000");
  } else {
    const result = calculateAtmosphere(height);
    populateCalculationContainer(result);
  }
};
