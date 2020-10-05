
function calculateFitness() {
  var currentRecordDistance = Infinity;
  for (var i = 0; i < population.length; i++) {
    // population is array of 10 different orders of paths
    // calculating the distance of each 10 different orders
    // setting bestDistance to population with lowest distance (highest fitness)
    var d = calcDistance(nodes, population[i]);

    if (d < bestDistance) {
      bestDistance = d;
      bestPop = population[i];
    }

    if (d < currentRecordDistance) {
      currentRecordDistance = d;
      currentBestPop = population[i];
    }

    // the lower the distance, the higher the fitness (better path)
    // inverting (inverse) -> higher distance = lower fitness
    fitness[i] = 1 / (d + 1);
  }
}

function normalizeFitness() {
  var sum = 0;
  for (let i = 0; i < fitness.length; i++) {
    sum += fitness[i];
  }

  for (let i = 0; i < fitness.length; i++) {
    fitness[i] = fitness[i] / sum;
  }

}

function nextGeneration() {
  var newPopulation = [];

  for (let i = 0; i < population.length; i++) {
    // higher fitness value, higher chance of getting picked
    var orderA = pickOne(population, fitness);
    var orderB = pickOne(population, fitness);
    // crosses over two random orders of high fitness, returns slice of new array
    var order = crossOver(orderA, orderB);
    // newPopulation will fill more with always higher fitness populations
    mutate(order, 0.01);
    newPopulation[i] = order;
    // which will then mutate to produce changed populations ->
    // which will then factor out higher populations, and so on. evolution.
  }
  population = newPopulation;
}

function pickOne(list, prob) {
  var index = 0;
  var r = random(1);

  while (r > 0) {
    r = r - prob[index];
    index++;
  }
  index--;
  return list[index].slice();
}

function crossOver(orderA, orderB) {

  var start = floor(random(orderA.length));
  var end = floor(random(start + 1, orderA.length));
  var newOrder = orderA.slice(start, end);

  for (let i = 0; i < orderB.length; i++) {
    var node = orderB[i];
    if (!newOrder.includes(node)) {
      newOrder.push(node);
    }
  }
  // returns the array of crossed over array's A and B;
  return newOrder;
}

function mutate(order, mutationRate) {
  for (let i = 0; i < totalNodes; i++) {
    if (random(1) < mutationRate) {
      var indexA = floor(random(order.length));
      //var indexB = floor(random(order.length));
      var indexB = (indexA + 1) % totalNodes;
      swap(order, indexA, indexB)
    }
  }
}
