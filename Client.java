import java.awt.Color;
import java.util.Random;
import java.util.Arrays;
import java.io.PrintStream;
import java.io.FileNotFoundException;

public class Client {
  public static void main(String[] args) throws FileNotFoundException{
    int gridSize = 1000;
    int cellSize = 1;

    //PART 1
    //Langton's ant
      //create array of rules and pass to new ant object
      int[][] langtonRules = {{0, 0, 1, 0, 0}, {1, 0, 0, 0, 1}};
      Turmite langtonAnt = new Turmite(gridSize, cellSize, langtonRules);

      langtonAnt.step(100);
      langtonAnt.getImage().saveImage("langton100.png");

      langtonAnt.step(1000);
      langtonAnt.getImage().saveImage("langton1k.png");

      langtonAnt.step(10000);
      langtonAnt.getImage().saveImage("langton10k.png");

      langtonAnt.step(100000);
      langtonAnt.getImage().saveImage("langton100k.png");

    //PART 2
    //Generate random rules and run
      Random rand = new Random();
      PrintStream writer = new PrintStream("rules.txt");

      for (int i = 0; i < 100; i++) {
        //generate random number of colors for grid
        int colors = rand.nextInt(9)+2;;
        //select random number of internal states
        int states = rand.nextInt(9)+2;
        //create an array containing all possible combinations of rules
        int[][] ruleArray = generateRuleArray(colors, states);
        Turmite ant = new Turmite(gridSize, cellSize, ruleArray);
        ant.step(300000);
        ant.getImage().saveImage("randomant" + i + ".png");

        //keeping track of rules to reference later
        writer.println("Ruleset for #" + i + " = ");
        writer.println(Arrays.deepToString(ruleArray));
        writer.println("****");
      }

    //PART 3
    //Spiral
      int[][] spiralRule = {{0, 0, 1, 1, 0},
                            {0, 1, 1, 0, 2},
                            {1, 0, 1, 0, 2},
                            {1, 1, 1, 0, 1}};
      Turmite spiral = new Turmite(gridSize, cellSize, spiralRule);
      spiral.step(30000);
      spiral.getImage().saveImage("spiral.png");
  }

  /*
  * This method generates and returns an array
  * containing a numerical representation of all the rules for an ant
  */
  public static int[][] generateRuleArray(int colors, int states) {
    //a rule consists of:
    //  a new color to be written
    //  a new internal state
    //  a turning direction (straight, left, or right)

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
        //can't set a rule to create a new color or state if our total number
        //of colors and states doesn't contain it
        ruleArray[current][2] = rand.nextInt(colors+1);
        ruleArray[current][3] = rand.nextInt(states+1);
        ruleArray[current][4] = rand.nextInt(3);
        current++;
      }
    }

    return ruleArray;
  }
}
