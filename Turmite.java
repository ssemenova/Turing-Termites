import java.awt.Color;
import java.util.Arrays;

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
    direction = 1; //0 - Left, 1 - Up, 2 - Right, 3 - Down
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
    int readState, readColor;
    int newState, newColor, newTurn;
    int count = 0;

    //look through all rules until we find one that matches our current predicament
    //  color, state, new color, new state, turning direction
    do {
      readColor = ruleArray[count][0];
      readState = ruleArray[count][1];
      newColor = ruleArray[count][2];
      newState = ruleArray[count][3];
      newTurn = ruleArray[count][4];
      count++;
      // System.out.println("COLOR=" + readColor + " " + colorArray[row][col]);
      // System.out.println("STATE=" + readState + " " + state);
      // System.out.println("COUNT=" + count);
      // System.out.println();
    } while (readState!=state || readColor!=colorArray[row][col]);

    turn(newTurn);
    colorArray[row][col] = newColor;
    state = newState;
  }

  /*
  * Moves the termite forward, wrapping around if it goes off the board
  */
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

    //wrap around if goes off the screen
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
    else if (turnDirection == 1)
      direction = (direction + 3) % 4;
    //if turn direction = 2, turmite keeps going straight
  }

  /*
  * Converts a number to a color object
  */
  private Color convertNumToColor(int num) {
    if (num == 0)
      return Color.WHITE;
    else if (num == 1)
      return Color.BLACK;
    else if (num == 2)
      return Color.BLUE;
    else if (num == 3)
      return Color.RED;
    else if (num == 4)
      return Color.GREEN;
    else if (num == 5)
      return Color.MAGENTA;
    else if (num == 6)
      return Color.YELLOW;
    else if (num == 7)
      return Color.ORANGE;
    else if (num == 8)
      return Color.PINK;
    else if (num == 9)
      return Color.CYAN;
    else
      return Color.GRAY;
  }

	/**
	 * Getter method for the size of the lattice on which this automaton runs
	 * @return the grid size
	 */
	public int getGridSize() {
    return gridSize;
  }
}
