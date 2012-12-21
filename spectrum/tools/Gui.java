package spectrum.tools;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	LayoutManager layout;
	JPanel panel;
	Dimension dimension;
	String title;

	public Gui(JPanel panel, Dimension dimension) {
		this.panel = panel;
		this.dimension = dimension;
	}

	public Gui(JPanel panel, Dimension dimension, LayoutManager layout) {
		this.panel = panel;
		this.dimension = dimension;
		this.layout = layout;
	}

	public Gui(JPanel panel, LayoutManager layout) {
		this.panel = panel;
		this.layout = layout;
	}

	public void setupFrame() {
		setContentPane(panel);
		pack();
		setSize(dimension);
		setLayout(layout);
		setTitle(title);
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}
}
