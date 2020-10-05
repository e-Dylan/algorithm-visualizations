var nodes = [];

var totalNodes = 8;
var numberOfPopulations = 44;

var population = [];
var fitness = [];

var bestDistance = Infinity;
var bestPop;
var currentBestPop;

function swap(a, i, j) {
  var temp = a[i];
  a[i] = a[j];
  a[j] = temp;
}

// function shuffle(a, num) {
//   for (let i = 0; i < num; i++) {
//     var indexA = random(a.length);
//     var indexB = random(a.length);
//     swap(a, indexA, indexB);
//   }
// }

function calcDistance(points, order) {
  var sum = 0;
  for (let i = 0; i < order.length - 1; i++) {
    cityAIndex = order[i];
    cityA = points[cityAIndex];
    cityBIndex = order[i + 1];
    cityB = points[cityBIndex];

    var d = dist(cityA.x, cityA.y, cityB.x, cityB.y);
    sum += d;
  }
  return sum;
}

function factorial(n) {
  if (n == 1) {
    return(1);
  } else {
    return(n * factorial(n - 1));
  }
}

function setup() {

  createCanvas(600, 800);

  var order = [];

  for (let i = 0; i < totalNodes; i++) {
    var v = createVector(random(width), random(height / 2));
    nodes[i] = v;
    order[i] = i;
  }

  // creating the number of populations,
  // shuffling to give each a random fitness (order)
  for (let i = 0; i < numberOfPopulations; i++) {
    population[i] = shuffle(order);
  }

  // creating the number of populations
  // shuffling for random order. if any order is the same to existing,
  // reshuffles until different. creates every possibility
  // for (var i = 0; i < numberOfPopulations; i++) {
  //   population[i] = shuffle(order);
  //   for (let j = 0; j < i; j++ ) {
  //     while (population[i] == population[j]) {
  //       population[i] = shuffle(order);
  //     }
  //   }
  // }

  console.log(population);



  // for (let i = 0; i < population.length; i++) {
  //   var pop1 = population[i];
  //   for (let j = 0; j < population.length; j++) {
  //     var pop2 = population[j];
  //     if (pop1 == pop2 && i != j) {
  //       console.log(pop1);
  //       console.log(pop2);
  //     }
  //   }
  // }

}

function draw() {

  background(0);

  // GENETIC ALGORITHM

  // determines fitness' of all populations,
  // normalizes each fitness between 0-100%;

  calculateFitness();
  normalizeFitness();

  // draw current best population before each next generation is produced

  // producing new populations based on population(s) with best fitness;
  nextGeneration();

  for (let i = 0; i < totalNodes; i++) {
    fill(255);
    ellipse(nodes[i].x, nodes[i].y, 10, 10);
  }

  stroke(255);
  strokeWeight(3);
  noFill();
  beginShape();
  for (let i = 0; i < bestPop.length; i++) {
    var n = bestPop[i];
    vertex(nodes[n].x, nodes[n].y);
    ellipse(nodes[n].x, nodes[n].y, 12, 12);

  }
  endShape();

  // first element in bestPop array (start)
  var firstNode = bestPop[0];
  fill(0, 255, 0);
  ellipse(nodes[firstNode].x, nodes[firstNode].y, 16, 16);
  var lastNode = bestPop[bestPop.length - 1];
  fill(255, 0, 0);
  ellipse(nodes[lastNode].x, nodes[lastNode].y, 16, 16);

  for (let i = 0; i < bestPop.length; i++) {
    var s = bestPop;
  }
  textSize(32);
  strokeWeight(1);
  fill(0, 255, 0);
  text(s, 20, height - 100);

  translate(0, height / 2);
  stroke(255);
  strokeWeight(3);
  noFill();
  beginShape();
  for (let i = 0; i < currentBestPop.length; i++) {
    var n = currentBestPop[i];
    vertex(nodes[n].x, nodes[n].y);
    ellipse(nodes[n].x, nodes[n].y, 12, 12);

  }
  endShape();

  // first element in bestPop array (start)
  var firstNode = currentBestPop[0];
  fill(0, 255, 0);
  ellipse(nodes[firstNode].x, nodes[firstNode].y, 16, 16);
  var lastNode = currentBestPop[bestPop.length - 1];
  fill(255, 0, 0);
  ellipse(nodes[lastNode].x, nodes[lastNode].y, 16, 16);

  // translate(0, height / 2);
  // stroke(255);
  // strokeWeight(2);
  // noFill();
  // beginShape();
  // for (let i = 0; i < bestCurrentPop.length; i++) {
  //   var n = bestCurrentPop[i];
  //   vertex(nodes[n].x, nodes[x].y);
  //   ellipse(nodes[n].x, nodes[n].y, 12, 12);
  // }
  // endShape()

}
