package spectrum.scripts.lizards;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.bot.Context;

import spectrum.scripts.lizards.nodes.Strats;
import spectrum.tools.Methods;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Ids;
import spectrum.tools.map.Paths;
import spectrum.tools.structure.Strategies;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel p = new JPanel(new GridBagLayout());
	JPanel p2 = new JPanel(new GridLayout(4, 1));
	JPanel p3 = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	JLabel title = new JLabel("sSwampLizards");
	JLabel title2 = new JLabel("Trapping Area");
	JLabel title3 = new JLabel("Equipment Count");
	JButton start = new JButton("Start");
	String[] selectedArea = { "Area 1 [Northwest]", "Area 2 [Northeast]",
	// "Area 3 [Southeast]"
	};
	JComboBox<String> chooseArea = new JComboBox<String>(selectedArea);
	String[] selectedTraps = { "1", "2", "3", "4" };
	String[] selectedTraps2 = { "1", "2", "3" };
	JComboBox<String> chooseNumOfTraps = new JComboBox<String>(selectedTraps);
	JPanel panel1 = new JPanel();
	JPanel panel2A = new JPanel(new GridLayout(2, 1));
	JPanel panel2 = new JPanel();
	JLabel title4 = new JLabel("Select your banking method below");
	String[] selectedMethod = { "Walk to Bank", "Teleport to Bank(Runes)",
	/* "Teleport to Bank(Tabs)" */};
	JComboBox<String> chooseBank = new JComboBox<String>(selectedMethod);
	JLabel title5 = new JLabel("Select your trapping order below");
	String[] selectedTrapMethod = { "Set / Release / Pick up",
			"Release / Set / Pick up", "Set / Pick up / Release",
			"Pick up / Set / Release", "Pick up / Release / Set" };
	JComboBox<String> chooseTrapMethod = new JComboBox<String>(
			selectedTrapMethod);
	JPanel panel3A = new JPanel(new GridLayout(2, 1));
	JPanel panel3 = new JPanel();
	JLabel title6 = new JLabel("Select your summoning familiar below");
	String[] selectedFamiliar = { "None", "Bull Ant", "Spirit Terrorbird",
			"War Tortoise", "Pack Yak" };
	int[] selectedFamiliarId = { 0, 12087, 12007, 12031, 12093 };
	JComboBox<String> chooseFamiliar = new JComboBox<String>(selectedFamiliar);
	JPanel panel4A = new JPanel(new GridLayout(2, 1));
	JPanel panel4 = new JPanel();
	JTabbedPane pane = new JTabbedPane();

	public Gui() {
		initComponents();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	private void initComponents() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		int i = 45;
		gbc.insets = new Insets(i, i, i, i);
		title.setFont(new Font("Arial", Font.BOLD, 18));

		p.add(title);
		p2.add(title2);
		p2.add(chooseArea);
		p2.add(title3);
		p2.add(chooseNumOfTraps);
		p3.add(start);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButtonActionPerformed(e);
				Variables.startScript = true;
			}
		});
		contentPane.add(p, BorderLayout.NORTH);
		contentPane.add(p3, BorderLayout.SOUTH);
		contentPane.add(pane, BorderLayout.CENTER);
		pane.addTab("Home", null, panel1,
				"Navigate through the tabs to select your script options.");
		pane.setMnemonicAt(0, KeyEvent.VK_1);
		panel1.setPreferredSize(new Dimension(300, 200));
		panel1.add(p2, BorderLayout.CENTER);

		pane.addTab("Banking", null, panel2, "Select your banking method here.");
		pane.setMnemonicAt(1, KeyEvent.VK_2);
		panel2.setPreferredSize(new Dimension(300, 200));
		panel2A.add(title4);
		panel2A.add(chooseBank);
		panel2.add(panel2A);
		pane.addTab("Trapping", null, panel3,
				"Select your trapping method here.");
		pane.setMnemonicAt(1, KeyEvent.VK_2);
		panel3.setPreferredSize(new Dimension(300, 200));
		panel3A.add(title5);
		panel3A.add(chooseTrapMethod);
		panel3.add(panel3A);
		pane.setMnemonicAt(2, KeyEvent.VK_3);
		pane.addTab("Summoning", null, panel4,
				"Select your summoning familiar here.");
		panel4.setPreferredSize(new Dimension(320, 200));
		panel4A.add(title6);
		panel4A.add(chooseFamiliar);
		panel4.add(panel4A);
		pane.setMnemonicAt(3, KeyEvent.VK_4);
		Dimension d = new Dimension(208, 200);
		contentPane.setPreferredSize(d);
		Manifest data = Lizards.class.getAnnotation(Manifest.class);
		setTitle("sSwampLizards - v" + data.version());
		pack();
		setLocationRelativeTo(getOwner());

	}

	public boolean isGuiWait() {
		return Variables.guiWait;
	}

	public void reset() {
		Variables.runTime.reset();
	}

	public void setGuiWait(boolean guiWait) {
		Variables.guiWait = guiWait;
	}

	private void startButtonActionPerformed(ActionEvent e) {
		System.out.println("Welcome " + Context.get().getDisplayName() + "!");
		System.out.println("Here are the settings you set.");
		String chosenArea = chooseArea.getSelectedItem().toString();
		Ids.LIZARD_ID = Ids.ALL_LIZARD_ID;
		Variables.TRAP_ID = Ids.trap_ID;
		Variables.trappedLizards = "Swamp Lizards";
		if (chosenArea.contains("Area 1")) {
			Variables.trappingSpot = Areas.area1;
			Variables.centralArea = Areas.centralArea;
			for (int i = 0; i < Variables.allTrapAreas.length; i++) {
				Variables.allTrapAreas[i] = Areas.trapAreas1[i];
			}
			Variables.allTraps[0] = Areas.TRAP_1;
			Variables.swampPath = Paths.path1;
		} else if (chosenArea.contains("Area 2")) {
			Variables.trappingSpot = Areas.area2;
			Variables.centralArea = Areas.centralArea2;
			for (int i = 0; i < Variables.allTrapAreas.length; i++) {
				Variables.allTrapAreas[i] = Areas.trapAreas2[i];
			}
			Variables.allTraps[0] = Areas.TRAP_2;
			Variables.swampPath = Paths.path2;
		} else if (chosenArea.contains("Area 3")) {
			Variables.trappingSpot = Areas.area3;
			// Variables.centralArea = Areas.centralArea3;
			for (int i = 0; i < Variables.allTrapAreas.length; i++) {
				Variables.allTrapAreas[i] = Areas.trapAreas3[i];
			}
			Variables.allTraps[0] = Areas.TRAP_3;
			Variables.swampPath = Paths.path3;

		}
		String chosenNumTraps = chooseNumOfTraps.getSelectedItem().toString();
		Variables.trappedLizards = "Swamp Lizards";
		Methods.setValues();
		if (chosenNumTraps.equals("1")) {
			Variables.maxTraps = 1;
		} else if (chosenNumTraps.equals("2")) {
			Variables.maxTraps = 2;
		} else if (chosenNumTraps.equals("3")) {
			Variables.maxTraps = 3;
		} else if (chosenNumTraps.equals("4")) {
			Variables.maxTraps = 4;
		}
		System.out.println("Equipment Amount: " + Variables.maxTraps);
		Variables.equipmentCount = "" + Variables.maxTraps;
		String chosenBank = chooseBank.getSelectedItem().toString();
		for (int i = 0; i < selectedMethod.length; i++)
			if (chosenBank.contains(selectedMethod[i])) {
				Variables.bankOptions[i] = true;
				System.out.println("Bank Method: " + selectedMethod[i]);
				Variables.bankingMethod = "" + selectedMethod[i];
			}

		String chosenFamiliar = chooseFamiliar.getSelectedItem().toString();
		for (int i = 0; i < selectedFamiliar.length; i++)
			if (chosenFamiliar.contains(selectedFamiliar[i])) {
				System.out.println("Familiar Selected: " + selectedFamiliar[i]);
				Variables.familiarId = selectedFamiliarId[i];
				Variables.familiarSelected = "" + selectedFamiliar[i];
				if (Variables.familiarId > 0) {
					Variables.usingFamiliar = true;
				}
			}
		String chosenTrapMethod = chooseTrapMethod.getSelectedItem().toString();
		for (int i = 0; i < selectedTrapMethod.length; i++)
			if (chosenTrapMethod.contains(selectedTrapMethod[i])) {
				Variables.options[i] = true;
				System.out.println("Trap Method: " + selectedTrapMethod[i]);
				Variables.trapMethod = "" + selectedTrapMethod[i];
			}
		final JOptionPane optionPane = new JOptionPane(
				"Is the following information correct?\n" + "Hunter Level: "
						+ Variables.currentLvl + "\n" + "Equipment Amount: "
						+ Variables.equipmentCount + "\n" + "Trapping Area: "
						+ chosenArea + "\n" + "Banking Method: "
						+ Variables.bankingMethod + "\n" + "Trapping Method: "
						+ Variables.trapMethod + "\n" + "Familiar Selected: "
						+ Variables.familiarSelected + "\n",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		final JDialog dialog = new JDialog(this, "Click a button", true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if (dialog.isVisible() && e.getSource() == optionPane
						&& prop.equals(JOptionPane.VALUE_PROPERTY)) {
					// If you were going to check something
					// before closing the window, you'd do
					// it here.
					dialog.setVisible(false);
				}
			}
		});
		dialog.pack();
		dialog.setLocationRelativeTo(getOwner());
		dialog.setVisible(true);
		int value = (int) optionPane.getValue();
		if (value == JOptionPane.YES_OPTION) {
			Variables.runTime = new Timer(0);
			Strategies.add(new Strats());
			this.setVisible(false);
			this.dispose();
		} else if (value == JOptionPane.NO_OPTION) {
			dialog.setVisible(false);
			dialog.dispose();
		}

	}
}