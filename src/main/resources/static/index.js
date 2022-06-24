const heightInput = document.querySelector("input[name=height]");
const temperatureContainer = document.querySelector("p#temperature");
const pressureContainer = document.querySelector("p#pressure");
const densityContainer = document.querySelector("p#density");

const populateCalculationContainer = (data) => {
  temperatureContainer.innerHTML = `Temperature: ${data.temperature} K`;
  pressureContainer.innerHTML = `Pressure: ${data.pressure} Pa`;
  densityContainer.innerHTML = `Density: ${data.density} kg/m3`;
};

heightInput.oninput = (e) => {
  fetch('/calculate?height=' + e.target.value)
    .then(res => res.json())
    .then(populateCalculationContainer);
};
