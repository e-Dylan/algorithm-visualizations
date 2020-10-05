
var nodes = [];
var totalNodes = 5;

var bestDistance;
var bestOrder;

var checkedSeq = [];


function swap(a, i, j) {

  var temp = a[i];
  a[i] = a[j];
  a[j] = temp;

}

function calcDistance(points) {

  var sum = 0;
  for (var i = 0; i < points.length - 1; i++) {
    var d = dist(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
    sum += d;
  }

  return sum;

}

function setup() {

  createCanvas(600, 600);

  for (let i = 0; i < totalNodes; i++ ) {
    var v = createVector(random(width), random(height));
    nodes[i] = v;
  }

  var d = calcDistance(nodes);
  bestDistance = d;
  bestOrder = nodes.slice();

}

function draw() {

  background(0);
  fill(255);

  for (let i = 0; i < nodes.length; i++) {

    ellipse(nodes[i].x, nodes[i].y, 16, 16);

  }

  stroke(255);
  strokeWeight(1);
  noFill();
  beginShape();
  for (let i = 0; i < nodes.length; i++) {
    vertex(nodes[i].x, nodes[i].y)
  }

  endShape();

  var i = floor(random(nodes.length));
  var j = floor(random(nodes.length));
  swap(nodes, i, j);

  var d = calcDistance(nodes);

  if (d < bestDistance) {
    bestDistance = d;
    bestOrder = nodes.slice();
    console.log("New best distance: " + d);
    console.log(bestOrder);
  }

  stroke(0, 0, 255);
  strokeWeight(4);
  noFill();
  beginShape();
  for (let i = 0; i < bestOrder.length; i++) {
    vertex(bestOrder[i].x, bestOrder[i].y)
  }
  endShape();

}
