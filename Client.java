import java.awt.Color;
import java.util.Random;
import java.util.Arrays;

public class Client {
  public static void main(String[] args) {
    int gridSize = 1000;
    int cellSize = 1;

    //PART 1
    //Langton's ant
    langton(gridSize, cellSize);

    //PART 2
    //Generate random rules and run
    Random rand = new Random();
    for (int i = 0; i < 1; i++) {
      //generate random number of colors for grid
      int colors = rand.nextInt(9);
      //select random number of internal states
      int states = rand.nextInt(9);
      //create an array containing all possible combinations of rules
      int[][] ruleArray = generateRuleArray(colors, states);
    }
  }

  /*
  * Generates a langton ant
  */
  public static void langton(int gridSize, int cellSize) {
    //create ruleArray
    int[][] ruleArray = {{0, 0, 1, 0, 0}, {1, 0, 0, 0, 1}};
    Turmite langtonAnt = new Turmite(gridSize, cellSize, ruleArray);

    langtonAnt.step(100);
    langtonAnt.getImage().saveImage("langton100.png");

    langtonAnt.step(1000);
    langtonAnt.getImage().saveImage("langton1k.png");

    langtonAnt.step(10000);
    langtonAnt.getImage().saveImage("langton10k.png");
    
    langtonAnt.step(100000);
    langtonAnt.getImage().saveImage("langton100k.png");
  }

  /*
  * This method generates and returns an array
  * containing a numerical representation of all the rules for an ant
  */
  public static int[][] generateRuleArray(int colors, int states) {
    //a rule consists of:
    //  a new color to be written
    //  a new internal state
    //  a turning direction (left or right)

    //length of array indexes are:
    //  color, state, new color, new state, turning direction
    //height of array will include:
    //  colors*states different rules
    int[][] ruleArray = new int[(colors+1)*(states+1)][5];

    Random rand = new Random();
    int current = 0;

    for (int oldColor = 0; oldColor <= colors; oldColor++) {
      for (int oldState = 0; oldState <= states; oldState++) {
        ruleArray[current][0] = oldColor;
        ruleArray[current][1] = oldState;
        ruleArray[current][2] = rand.nextInt(9);
        ruleArray[current][3] = rand.nextInt(9);
        ruleArray[current][4] = rand.nextInt(2);
        current++;
      }
    }

    return ruleArray;
  }
}
