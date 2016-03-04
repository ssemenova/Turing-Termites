
public interface DisplayableAutomaton {
	/**
	 * Return the AutomatonImage on which this automaton draws its lattice
	 * @return an AutomatonImage object
	 */
	public AutomatonImage getImage();
	/**
	 * Advance the automaton for a given number of steps, updating its image along the way.
	 * @param numSteps The number of steps to advance
	 */
	public void step(int numSteps);
	/**
	 * Getter method for the size of the lattice on which this automaton runs
	 * @return the grid size
	 */
	public int getGridSize();
}
