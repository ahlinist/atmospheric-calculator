import { calculateAtmosphere } from "./calculator/calculator.js";

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
  const result = calculateAtmosphere(e.target.value);
  populateCalculationContainer(result);
  /*fetch('/calculate?height=' + e.target.value)
    .then(res => {
      if (!res.ok) {
        return res.json().then(data => { throw new Error(data.message) })
      }
      return res.json();
    })
    .then(populateCalculationContainer)
    .catch(displayError);*/
};
