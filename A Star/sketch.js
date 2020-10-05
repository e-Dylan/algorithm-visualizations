let cols = 30;
let rows = 30;
let grid = new Array(cols);

let openSet = [];
let closedSet = [];
let start;
let end;

let w, h;

let path = [];

let showFScore = true;
let useDiagonals = true;

function Cell(i, j) {
  this.i = i;
  this.j = j;
  this.f = 0;
  this.g = 0;
  this.h = 0;
  this.neighbours = [];
  this.previous = undefined;
  this.wall = false;

  if (random(1) < 0.5) {
    this.wall = true;
  }

  this.show = function(colour) {
    fill(colour);

    if (this.wall == true) {
      fill(0);
    }

    noStroke();
    rect(this.i * w, this.j * h, w - 1, h - 1);
    fill(0);

    // drawing f value of each cell;
    //if (mouseX > this.i * w && mouseX < this.i * w + w) {
      //if (mouseY > this.j * h && mouseY < this.j * h + h) {
        //text(this.f, this.i * w + (w / 3), this.j * h - (h / 3));
      //}
    //}
  }

  this.addNeighbours = function(grid) {

    var i = this.i;
    var j = this.j;

    if (i < cols - 1) {
      this.neighbours.push(grid[i + 1][j]); // n to the right
    }
    if (i > 0) {
      this.neighbours.push(grid[i - 1][j]); // no to the left
    }
    if (j > 0) {
      this.neighbours.push(grid[i][j - 1]); // n up
    }
    if (j < rows - 1) {
      this.neighbours.push(grid[i][j + 1]); // n down
    }

    // adding diagonal neighbours

    if (useDiagonals) {

      if (i > 0 && j > 0) {
        this.neighbours.push(grid[i - 1][j - 1]); // top left
      }
      if (i < cols - 1 && j > 0 ) {
        this.neighbours.push(grid[i + 1][j - 1]); // top right
      }
      if (i > 0 && j < rows - 1) {
        this.neighbours.push(grid[i - 1][j + 1]); // bottom left
      }
      if (i < cols - 1 && j < rows - 1) {
        this.neighbours.push(grid[i + 1][j + 1]); // bottom right
      }
    }

  }

}

function removeFromArray(arr, node) {

  // loops through openSet array to find node to remove

  for (let i = arr.length - 1; i >= 0; i--) {
    if (arr[i] == node) {
      // splice removes an element from an array at a given index
      // if [i] is the node, remove[i]
      arr.splice(i, 1);
    }
  }

}

function heuristic(a, b) {

  var d = dist(a.i, a.j, b.i, b.j); // euclytic distance (xy to xy direct)
  //var d = abs(a.i - b.i) + abs(a.j - b.j);
  return d;

}

function setup() {

  createCanvas(600, 600);

  console.log("A*");

  w = width / cols;
  h = height / rows;

  // creating 2d array
  for (let i = 0; i < cols; i++) {
    grid[i] = new Array(rows);
  }

  // creating each cell in the grid

  for (let i = 0; i < cols; i++) {
    for (let j = 0; j < rows; j++) {

      grid[i][j] = new Cell(i, j);

    }
  }

  // filling neighbours array with all neighbours of cells
  for (let i = 0; i < cols; i++) {
    for (let j = 0; j < rows; j++) {
      grid[i][j].addNeighbours(grid);
    }
  }

  start = grid[0][0];
  end = grid[cols - 1][rows - 1];
  start.wall = false;
  end.wall = false;

  openSet.push(start);

  //console.log(grid);

}

function draw() {

  //openSet = nodes to be evalutated
  //closedSet = evaluated nodes

  if (openSet.length > 0) {
    // if there are nodes to be evalutated

    let winner = 0;
    for (let i = 0; i < openSet.length; i++) {
      if (openSet[i].f < openSet[winner].f) {
        // checks total overall score (g(cost) + h(dist from end)) and sets
        // winner to the lowest overall score(f)
        winner = i;
        // setting winner to the cell in openset with smallest f
      }
    }
    // current node is the openSet node with sallest total score (f)
    var current = openSet[winner];

    if (current == end) {
      noLoop();
      console.log("A*: End Reachedss.");
    }

    // remove the evaluated current node from openSet, add to closed
    removeFromArray(openSet, current);
    closedSet.push(current);

    // checking every neighbour, of current cell
    // g = cost to get to here

    var neighbours = current.neighbours;
    for (let i = 0; i < neighbours.length; i++) {
      let neighbour = neighbours[i];

      if (!closedSet.includes(neighbour) && !neighbour.wall == true) {
        // if neighbour hasnt already been evalutated
        // if neighbour is a wall, spot is not possible to evaluate
        var tempG = current.g + 1;

        var newPath = false;
        // if we've gotten here before,
        if (openSet.includes(neighbour)) {
          // if this path is more efficient, make it the new path
          if (tempG < neighbour.g) {
            neighbour.g = tempG;
            newPath = true;
          }
        } else {
          // getting here for the first time, adding to open set, then gets checked
          neighbour.g = tempG;
          newPath = true;
          openSet.push(neighbour);
        }

        if (newPath) {
          neighbour.h = heuristic(neighbour, end); // distance from end
          // node's total score - cost + distance from end;
          neighbour.f = neighbour.g + neighbour.h;
          neighbour.previous = current;
        }

      }
    }


  } else {
    // when openSet.length isnt > 0 (no nodes left in the openSet)
    console.log("No solution.")
    noLoop();
    return;
    // no nodes left to evaluate, no solution if not reached end
  }

  background(0);

  for (let i = 0; i < cols; i++) {
    for (let j = 0; j < rows; j++) {
      grid[i][j].show(color(255));
    }
  }

  for (let i = 0; i < closedSet.length; i++) {
    closedSet[i].show(color(255, 0, 0));
  }

  for (let i = 0; i < openSet.length; i++) {
    openSet[i].show(color(0, 255, 0));
    //console.log(openSet[i]);
  }

    // as long as there is a solution at the end node, it can
    // backtrack from the final node and fill the path.

  path = [];
  var temp = current;
  path.push(temp);
  // while there is a previous node in the path, add it to the path array
  // then go to the previous before that, and repeat
  while (temp.previous) {
    path.push(temp.previous);
    temp = temp.previous;
  }

  for (var i = 0; i < path.length; i++) {
    path[i].show(color(0, 0, 255));
  }

  // drawing fScores of each cell

  if (showFScore) {
    for (let i = 0; i < cols; i++) {
      for (let j = 0; j < cols; j++) {
        textSize(12)
        var fScore = floor(grid[i][j].f);
        text(fScore, i * w + (w / 5), j * h - (h / 5));
      }
    }
  }

  // looping through entire grid, drawing f value at each grid
  // can just be done in constructor function of cells

  //for (let i = 0; i < cols; i++) {
    //for (let j = 0; j < rows; j++) {
      //let fValue = grid[i][j].f;
      //fill(0);
      //text(fValue, i * w, j * h);
    //}
  //}

}
