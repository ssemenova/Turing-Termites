import java.awt.Color;

public class Turmite implements DisplayableAutomaton{
  private AutomatonImage image;
  private Color defaultColor = Color.WHITE;
  private Color color;
  //gridSize * cellSize = # of pixels in image
  private int gridSize;
  private int cellSize;
  //a "fake" image that contains all the colors on the real image, makes it easier for me to keep track of what's up
  private int[][] colorArray;
  //an array containing a numerical representation of all the rules
  private int[][] ruleArray;
  //variables that deal with current location and state of ant:
  private int state;
  private int direction;
  private int row;
  private int col;

  public Turmite(int gridSize, int cellSize, int[][] ruleArray) {
    this.gridSize = gridSize;
    this.cellSize = cellSize;
    image = new AutomatonImage(gridSize, cellSize, defaultColor);
    colorArray = new int[gridSize][gridSize];
    this.ruleArray = ruleArray;

    //defaults
    state = 0;
    direction = 2; //0 - Left, 1 - Up, 2 - Right, 3 - Down
    row = gridSize / 2; //starts off in middle
    col = gridSize / 2; //starts off in middle
  }

  /**
	 * Return the AutomatonImage on which this automaton draws its lattice
	 * @return an AutomatonImage object
	 */
	public AutomatonImage getImage() {
    return image;
  }

	/**
	 * Advance the automaton for a given number of steps, updating its image along the way.
	 * @param numSteps The number of steps to advance
	 */
	public void step(int numSteps) {
    for (int i = 0; i < numSteps; i++) {
      applyRule();
      color = convertNumToColor(colorArray[row][col]);
      image.colorCell(col, row, color);
      moveForward();
    }

    image.markComplete();
  }

  /*
  * Within each step, apply the rule,
  * which will turn the ant, color the current cell, and change the state
  */
  private void applyRule() {
    if (state == 0 && colorArray[row][col] == 0) {
      turn(0); //turn right
      colorArray[row][col] = 1; //change current color
      state = 0; //change state
    } else if (state == 0 && colorArray[row][col] == 1) {
      turn(1); //turn left
      colorArray[row][col] = 0; //change current color
      state = 0; //change state
    }
  }

  private void moveForward() {
    //facing left
    if (direction == 0)
      col = col - 1;
    //facing up
    else if (direction == 1)
      row = row + 1;
    // facing right
    else if (direction == 2)
      col = col + 1;
    // facing down
    else if (direction == 3)
      row = row - 1;

    if (row >= gridSize)
      row = 0;
    if (col >= gridSize)
      col = 0;
    if (row < 0)
      row = gridSize - 1;
    if (col < 0)
      col = gridSize - 1;
  }

  /*
  * Turns the termite either left or right
  */
  private void turn(int turnDirection) {
    //turn right
    if (turnDirection == 0)
      direction = (direction + 1) % 4;
    //turn left
    else
      direction = (direction + 3) % 4;
  }

  /*
  * Converts a number to a color object
  */
  private Color convertNumToColor(int num) {
    if (num == 0)
      return Color.WHITE;
    else if (num == 1)
      return Color.BLACK;
    else
      return Color.BLACK;
  }

	/**
	 * Getter method for the size of the lattice on which this automaton runs
	 * @return the grid size
	 */
	public int getGridSize() {
    return gridSize;
  }
}
