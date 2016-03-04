import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class AutomatonImage {
	private BufferedImage drawingBuffer;
	private BufferedImage displayBuffer;
	private Graphics graphics;
	private int cellSize;
	private int gridSize;
	/**
	 * @param gridSize The size of the grid of the corresponding automaton
	 * @param cellSize The size in pixels to draw each cell
	 * @param defaultColor The background color to fill the initial grid with
	 */
	public AutomatonImage(int gridSize, int cellSize, Color defaultColor) {
		this.gridSize = gridSize;
		this.cellSize = cellSize;
		 drawingBuffer = new BufferedImage(gridSize*cellSize,gridSize*cellSize,BufferedImage.TYPE_INT_RGB);
		 displayBuffer = new BufferedImage(gridSize*cellSize,gridSize*cellSize,BufferedImage.TYPE_INT_RGB);
		graphics = drawingBuffer.getGraphics();
		graphics.setColor(defaultColor);
		graphics.fillRect(0, 0, gridSize*cellSize, gridSize*cellSize);
		
	}
	/**
	 * Save the image as a PNG file to the specified file name.  If the image is unable to be saved, this method will
	 * trap the exception and output diagnostic information to STDERR
	 * 
	 * @param fileName The filename in which to save the image.  Should probably end in ".png"
	 */
	public void saveImage(String fileName) {
		try {
			ImageIO.write(displayBuffer, "PNG", new File(fileName));
		} catch (IOException e) {
			System.err.println("ERROR: Could not save image to file " + fileName + "!");
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Color in a single cell with the specified color
	 * 
	 * @param xCoordinate X-Coordinate of the cell to color
	 * @param yCoordinate Y-Cordinate of the cell to color
	 * @param color a Color object indicating which color to draw
	 */
	public void colorCell(int xCoordinate, int yCoordinate, Color color) {
		graphics.setColor(color);
		graphics.fillRect(xCoordinate*cellSize,yCoordinate*cellSize,cellSize,cellSize);
	}
	/**
	 * Marks the current changes as a complete update.  This should be done at the end of DisplayableAutomaton.step(int)
	 * This method allows us to make incremental updates as an automaton advances, and only copy them to the output buffer once complete.
	 */
	public void markComplete() {
		displayBuffer.getGraphics().drawImage(drawingBuffer,0,0,null);
	}
	/**
	 * Gets the display buffer for use by AutomatonAnimator
	 * @return the display buffer image
	 */
	public BufferedImage getDisplayImage() {
		return displayBuffer;
	}

}
