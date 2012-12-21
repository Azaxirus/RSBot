package spectrum.scripts.summoner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.bot.Context;

import spectrum.scripts.summoner.nodes.InteractObelisk;
import spectrum.scripts.summoner.nodes.InteractScrolls;
import spectrum.scripts.summoner.nodes.SetupInventory;
import spectrum.scripts.summoner.nodes.SetupInventoryScrolls;
import spectrum.scripts.summoner.nodes.SummonKyatt;
import spectrum.scripts.summoner.nodes.WalkToTrapDoor;
import spectrum.scripts.summoner.nodes.dung.InteractKyatt;
import spectrum.scripts.summoner.nodes.dung.TeleportDung;
import spectrum.scripts.summoner.nodes.dung.WalkToBanker;
import spectrum.scripts.summoner.nodes.dung.scroll.InteractKyattScrolls;
import spectrum.scripts.summoner.nodes.dung.scroll.TeleportDungScrolls;
import spectrum.scripts.summoner.nodes.pits.InteractKyattPits;
import spectrum.scripts.summoner.nodes.pits.TeleportPits;
import spectrum.scripts.summoner.nodes.pits.scroll.InteractKyattPitsScrolls;
import spectrum.scripts.summoner.nodes.pits.scroll.TeleportPitsScrolls;
import spectrum.tools.Methods;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategies;
import spectrum.tools.web.Access;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel p = new JPanel();
	JPanel pList = new JPanel(new BorderLayout());
	JPanel pList2 = new JPanel(new BorderLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	JLabel warning = new JLabel(
			"Please allow time for the script to collect item info after start.");
	JButton start = new JButton("Start");
	JTabbedPane tabbedPane = new JTabbedPane();
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new GridLayout(4, 1));
	JPanel panel3 = new JPanel(new BorderLayout());
	JLabel choosePouch = new JLabel("Choose the pouch below");
	JComboBox<String> pouchList = new JComboBox<String>(Ids.POUCH_NAMES);
	JLabel chooseScroll = new JLabel("Choose the scroll below");
	JComboBox<String> scrollList = new JComboBox<String>(Ids.POUCH_NAMES_SCROLL);
	JLabel chooseKyatt = new JLabel("Using Familiar?");
	JCheckBox usingKyatt = new JCheckBox("Kyatt");
	JLabel chooseBank = new JLabel("Choose your teleportation method");
	String[] bankOptions = { "Ring of Kinship",/* "Ring of Duelling", */
	"TokKul-Zo" };
	JComboBox<String> bankList = new JComboBox<String>(bankOptions);
	String[] columnNames = { "Familiar", "Charm", "Shards", "Exp" };
	String[][] example = new String[88][4];
	JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	JPanel splitTop = new JPanel(new BorderLayout());
	JCheckBox usingPouches = new JCheckBox("Make Pouches");
	JPanel splitBottom = new JPanel(new BorderLayout());
	JCheckBox usingScrolls = new JCheckBox("Make Scrolls");

	public Gui() {
		initComponents();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	public boolean findPouch(String s) {
		for (int i = 0; i < Ids.POUCH_NAMES.length; i++) {
			if (s.equals(Ids.POUCH_NAMES[i])) {
				Variables.pouchName = Ids.POUCH_NAMES[i];
				int j = Variables.pouchName.indexOf("- ");
				String st = Variables.pouchName.substring(j + 2);
				System.out.println("Pouch Name: " + st);
				Variables.pouchId = Ids.POUCH_IDS[i];
				Variables.pouchCharm = Ids.POUCH_CHARMS[i];

				if (Variables.pouchCharm == 12158) {
					Variables.charmName = "Gold charm";
				} else if (Variables.pouchCharm == 12159) {
					Variables.charmName = "Green charm";
				} else if (Variables.pouchCharm == 12163) {
					Variables.charmName = "Blue charm";
				} else if (Variables.pouchCharm == 12160) {
					Variables.charmName = "Crimson charm";
				}
				System.out.println("Pouch Charm: " + Variables.charmName);
				Variables.pouchSecondary = Ids.POUCH_SECONDARIES[i];
				System.out.println(Variables.pouchSecondary);
				try {
					if (Variables.pouchSecondary == 0) {
						Variables.error = "This script does not support " + st
								+ " yet.";
						Context.get().getScriptHandler().shutdown();
					} else if (GeItem.lookup(Variables.pouchSecondary) != null) {
						System.out.println("Pouch Secondary: "
								+ GeItem.lookup(Variables.pouchSecondary)
										.getName());
					} else {
						System.out.println("Pouch Secondary: "
								+ Access.getName(Variables.pouchSecondary));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				int[] itemsToWithdrawNew = { Ids.INVEN_SPIRIT_SHARD,
						Ids.INVEN_POUCH_EMPTY, Variables.pouchCharm,
						Variables.pouchSecondary };
				Variables.itemsToWithdraw = itemsToWithdrawNew;
				return true;
			}
		}
		return false;
	}

	public boolean findScroll(String s) {
		for (int i = 0; i < Ids.POUCH_NAMES_SCROLL.length; i++) {
			if (s.equals(Ids.POUCH_NAMES_SCROLL[i])) {
				Variables.pouchName = Ids.POUCH_NAMES_SCROLL[i];
				int j = Variables.pouchName.indexOf("- ");
				String st = Variables.pouchName.substring(j + 2);
				System.out.println("Pouch Name: " + st);
				Variables.pouchId = Ids.POUCH_IDS_SCROLL[i];
				Variables.scrollId = Ids.POUCH_SCROLLS[i];

				try {
					if (Variables.scrollId == 0) {
						Variables.error = "This script does not support " + st
								+ " yet.";
						Context.get().getScriptHandler().shutdown();
					} else if (GeItem.lookup(Variables.scrollId) != null) {
						System.out.println("Scroll Name: "
								+ GeItem.lookup(Variables.scrollId).getName());
					} else {
						System.out.println("Scroll Name: "
								+ Access.getName(Variables.scrollId));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				int[] itemsToWithdrawNew = { Variables.pouchId };
				Variables.itemsToWithdraw = itemsToWithdrawNew;
				return true;
			}
		}
		return false;
	}

	private void initComponents() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		gbc.insets = new Insets(15, 15, 15, 15);
		p.add(warning, BorderLayout.CENTER);
		p.add(start, BorderLayout.SOUTH);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButtonActionPerformed(e);
				Variables.startScript = true;
			}
		});

		// Panel 1
		// Add Panel1 to TabbedPane
		tabbedPane
				.addTab("Pouch", null, panel1, "Set Your Desired Pouch Here.");
		panel1.add(splitPane, BorderLayout.CENTER);
		splitPane.setTopComponent(splitTop);
		splitTop.add(usingPouches, BorderLayout.NORTH);
		usingPouches.setSelected(true);
		splitTop.add(choosePouch, BorderLayout.CENTER);
		choosePouch.setHorizontalAlignment(SwingConstants.CENTER);
		pList2.add(pouchList, BorderLayout.SOUTH);
		pouchList.setAlignmentX(SwingConstants.CENTER);
		splitTop.add(pList2, BorderLayout.SOUTH);
		usingPouches.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (usingPouches.isSelected()) {
					usingPouches.setSelected(true);
					usingScrolls.setSelected(false);
					pouchList.setEnabled(true);
					scrollList.setEnabled(false);
				} else {
					usingPouches.setSelected(false);
					usingScrolls.setSelected(true);
					pouchList.setEnabled(false);
					scrollList.setEnabled(true);
				}
			}
		});
		splitPane.setBottomComponent(splitBottom);
		splitPane.setTopComponent(splitTop);
		splitPane.setDividerLocation(100);
		splitBottom.add(usingScrolls, BorderLayout.NORTH);
		usingPouches.setSelected(true);
		splitBottom.add(chooseScroll, BorderLayout.CENTER);
		chooseScroll.setHorizontalAlignment(SwingConstants.CENTER);
		scrollList.setEnabled(false);
		pList.add(scrollList, BorderLayout.SOUTH);
		scrollList.setAlignmentX(SwingConstants.CENTER);
		splitBottom.add(pList, BorderLayout.SOUTH);
		usingScrolls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (usingScrolls.isSelected()) {
					usingPouches.setSelected(false);
					pouchList.setEnabled(false);
					scrollList.setEnabled(true);
				} else {
					usingPouches.setSelected(true);
					usingScrolls.setSelected(false);
					pouchList.setEnabled(true);
					scrollList.setEnabled(false);
				}
			}
		});
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		panel2.setPreferredSize(new Dimension(150, 100));
		chooseKyatt.setHorizontalAlignment(SwingConstants.CENTER);
		panel2.add(chooseKyatt);
		usingKyatt.setHorizontalAlignment(SwingConstants.CENTER);
		panel2.add(usingKyatt);
		usingKyatt.setEnabled(false);
		usingKyatt.setSelected(true);
		chooseBank.setHorizontalAlignment(SwingConstants.CENTER);
		panel2.add(chooseBank);
		bankList.setAlignmentX(SwingConstants.CENTER);
		panel2.add(bankList);
		// Add Panel2 to TabbedPane
		tabbedPane.addTab("Settings", null, panel2,
				"Set Your Desired Settings Here.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		panel1.setPreferredSize(new Dimension(300, 200));
		tabbedPane.addTab("Familiar List", null, panel3,
				"View all possible Familar Settings");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
		setupArray();
		final JTable table = new JTable(example, columnNames);
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		TableColumn col2 = table.getColumnModel().getColumn(2);
		col2.setPreferredWidth(30);
		TableColumn col = table.getColumnModel().getColumn(3);
		col.setPreferredWidth(30);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		panel3.add(scrollPane);
		panel3.setPreferredSize(new Dimension(300, 100));
		// Setup JPanel with TabbedPane and Start Button
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		contentPane.add(p, BorderLayout.SOUTH);
		Manifest data = Summoner.class.getAnnotation(Manifest.class);
		setTitle("jSummoner - v" + data.version());
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

	public void setupArray() {
		for (int i = 0; i < Ids.POUCH_NAMES.length; i++) {
			String s = null;
			example[i][0] = Ids.POUCH_NAMES[i];
			if (Ids.POUCH_CHARMS[i] == 12158) {
				s = "Gold charm";
			} else if (Ids.POUCH_CHARMS[i] == 12159) {
				s = "Green charm";
			} else if (Ids.POUCH_CHARMS[i] == 12163) {
				s = "Blue charm";
			} else if (Ids.POUCH_CHARMS[i] == 12160) {
				s = "Crimson charm";
			}
			example[i][1] = s;
			example[i][2] = Ids.POUCH_CHARM_AMOUNT[i];
			example[i][3] = Ids.POUCH_CREATE_EXP[i];
		}
	}

	private void startButtonActionPerformed(ActionEvent e) {
		reset();
		if (usingPouches.isSelected()) {
			findPouch(pouchList.getSelectedItem().toString());
		} else {
			findScroll(scrollList.getSelectedItem().toString());
		}
		if (!usingKyatt.isSelected()) {
			Variables.error = "Kyatt must be enabled until Taverley support is implemented.";
			Context.get().getScriptHandler().shutdown();
		}
		if (bankList.getSelectedItem() == "Ring of Kinship") {
			System.out.println("Ring selected: Ring of Kinship");
			if (usingPouches.isSelected()) {
				Strategies.add(new WalkToBanker(), new SetupInventory(),
						new InteractKyatt(), new WalkToTrapDoor(),
						new InteractObelisk(), new TeleportDung(),
						new SummonKyatt());
			} else if (usingScrolls.isSelected()) {
				Strategies.add(new WalkToBanker(), new SetupInventoryScrolls(),
						new InteractKyattScrolls(), new WalkToTrapDoor(),
						new InteractScrolls(), new TeleportDungScrolls(),
						new SummonKyatt());
			}

		} else if (bankList.getSelectedItem() == "Ring of Duelling") {
			Variables.error = "Ring of Duelling not supported. Please use Ring of Kinship for the moment.";
			Context.get().getScriptHandler().shutdown();
		} else if (bankList.getSelectedItem() == "TokKul-Zo") {
			System.out.println("Ring selected: TokKul-Zo");
			if (usingPouches.isSelected()) {
				Strategies.add(new SetupInventory(), new InteractKyattPits(),
						new WalkToTrapDoor(), new InteractObelisk(),
						new TeleportPits(), new SummonKyatt());
			} else if (usingScrolls.isSelected()) {
				Strategies.add(new SetupInventoryScrolls(),
						new InteractKyattPitsScrolls(), new WalkToTrapDoor(),
						new InteractScrolls(), new TeleportPitsScrolls(),
						new SummonKyatt());
			}
		}

		this.setVisible(false);
		this.dispose();
	}
}
