
var nodes = [];
//var totalNodes = random(4, 9);

var order = [];

var bestDistance;
var bestOrder;

var checkedSeq;
var possibleSeq;

var paused;
var complete;

function swap(a, i, j) {

  var temp = a[i];
  a[i] = a[j];
  a[j] = temp;

}

function calcDistance(points, order) {

  var sum = 0;
  for (var i = 0; i < points.length - 1; i++) {

    var cityAIndex = order[i];
    var cityA = points[cityAIndex];

    var cityBIndex = order[i + 1];
    var cityB = points[cityBIndex];

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

function nextOrder() {
  var largestI = -1;
  for (let i = 0; i < order.length - 1; i++) {
    if (order[i] < order[i + 1]) {
      largestI = i;
    }
  }

  if (largestI == -1) {
    noLoop();
    console.log("finished");
    complete = true;
    //document.location.reload();
  }

  // finding largestJ, largest index with smaller value than largestI;
  var largestJ = -1;
  for (let j = 0; j < order.length; j++) {
    if (order[largestI] < order[j]) {
      // furthest in the array thats bigger than largest I;
      largestJ = j;
    }
  }

  swap(order, largestI, largestJ)

  // reverse from largestI + 1 to end of array
  var endArray = order.splice(largestI + 1);
  endArray.reverse();
  // replacing original array with reversed section after largestI
  order = order.concat(endArray);
}

function setup() {

  createCanvas(600, 800);

  totalNodes = random(3, 7);

  paused = false;
  complete = false;

  for (let i = 0; i < totalNodes; i++ ) {
    var v = createVector(random(width), random(height - 200));
    nodes[i] = v;
    order[i] = i;
  }

  checkedSeq = 0;
  possibleSeq = factorial(order.length);

  var d = calcDistance(nodes, order);
  bestDistance = d;
  bestOrder = order.slice();

}

function draw() {

  background(0);
  fill(255);

  for (let i = 0; i < nodes.length; i++) {

    ellipse(nodes[i].x, nodes[i].y, 16, 16);

  }

  // drawing the path in the current order

  stroke(255);
  strokeWeight(1);
  noFill();
  beginShape();
  for (let i = 0; i < order.length; i++) {
    var n = order[i];
    vertex(nodes[n].x, nodes[n].y)
  }

  endShape();

  // calculating the total distance, checking if its the best order
  // saving it, then sorting to change to the next order

  if (!paused) {

    var d = calcDistance(nodes, order);

    if (d < bestDistance) {
      bestDistance = d;
      bestOrder = order.slice();
      console.log("New best distance: " + d);
      console.log(bestOrder);
    }

    checkedSeq += 1;
    nextOrder();
  }

  // drawing the path in the best order

  stroke(0, 0, 255);
  strokeWeight(4);
  noFill();
  beginShape();
  for (let i = 0; i < order.length; i++) {
    var n = bestOrder[i];
    vertex(nodes[n].x, nodes[n].y);
  }
  endShape();

  // drawing the current order;

  textSize(64);
  var s = "";
  for (let i = 0; i < order.length; i++) {
    s += order[i];
  }
  fill(255);
  text(s, 20, height - 50);

  // drawing the completion
  textSize(32);
  var percentComplete = (checkedSeq / possibleSeq) * 100;
  text(percentComplete.toFixed(2) + " %", 20, height - 110);

}

function keyPressed() {
  if (keyCode == LEFT_ARROW) {
    paused = !paused;
    console.log(paused);
  }
}
