import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class AutomatonAnimator {
	/**
	 * 
	 * Generate a GUI for a DisplayableAutomaton
	 * 
	 * @param automaton the automaton to display
	 */
	public AutomatonAnimator(DisplayableAutomaton automaton) {
		AnimationFrame animator = new AnimationFrame(automaton);
		
		animator.setVisible(true);
	}
	
}

class AnimationFrame extends JFrame {

	
	final private AnimationPanel panel;
	public AnimationFrame(DisplayableAutomaton automaton) {
		panel = new AnimationPanel(automaton);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
		this.buildToolbar();
		this.setTitle("TURMITES!");
		this.pack();
	}
	private void buildToolbar() {
		
		JToolBar toolbar = new JToolBar();
		
		JButton run = new JButton("Run");
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.startSimulation();
			}
			
		});
		
		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.pauseSimulation();
			}
			
		});
		
		JButton step = new JButton("Step");
		step.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.stepSimulation();
			}
			
		});
	
		toolbar.add(run);
		toolbar.add(pause);
		toolbar.add(step);
		
		toolbar.addSeparator();
		
		JRadioButton steps1 = new JRadioButton("1");
		steps1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setStepsPerFrame(1);
			}
			
		});
		steps1.setSelected(true);
		JRadioButton steps10 = new JRadioButton("10");
		steps10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setStepsPerFrame(10);
			}
			
		});
		JRadioButton steps100 = new JRadioButton("100");
		steps100.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setStepsPerFrame(100);
			}
			
		});
		JRadioButton steps1000 = new JRadioButton("1000");
		steps1000.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setStepsPerFrame(1000);
			}
			
		});
		
		ButtonGroup stepsGroup = new ButtonGroup();
		stepsGroup.add(steps1);
		stepsGroup.add(steps10);
		stepsGroup.add(steps100);
		stepsGroup.add(steps1000);
	    
		JLabel stepsLabel = new JLabel("Steps per frame: ");
		toolbar.add(stepsLabel);
		
		
		toolbar.add(steps1);
		toolbar.add(steps10);
		toolbar.add(steps100);
		toolbar.add(steps1000);
		
		toolbar.addSeparator();
		
		
		this.add(toolbar,BorderLayout.NORTH);
	}
	
}

class AnimationPanel extends JPanel implements ActionListener {

	
	private Timer frameTimer;
	private boolean simulationRunning = false;
	private DisplayableAutomaton automaton;
	private int stepsPerFrame;
	private BufferedImage displayBuffer = null;
	
	
	public AnimationPanel(DisplayableAutomaton automaton) {
		this.automaton = automaton;
		
		setPreferredSize(new Dimension(automaton.getImage().getDisplayImage().getWidth(),automaton.getImage().getDisplayImage().getWidth()));
		
		frameTimer = new Timer(50,this);
		frameTimer.start();
		
		stepsPerFrame = 1;
	}
	

	
	public void startSimulation() {
		simulationRunning = true;
	}
	public void pauseSimulation() {
		simulationRunning = false;
	}
	public void stepSimulation() {
		automaton.step(stepsPerFrame);
		displayBuffer = automaton.getImage().getDisplayImage();
		repaint();
	}
	public void setStepsPerFrame(int steps) {
		stepsPerFrame = steps;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(displayBuffer != null) {
			g.drawImage(displayBuffer,0,0,null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(simulationRunning) {
			automaton.step(stepsPerFrame);
			displayBuffer = automaton.getImage().getDisplayImage();
			repaint();
		}
	}

}