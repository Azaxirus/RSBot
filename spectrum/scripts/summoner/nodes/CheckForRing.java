package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.bot.Context;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class CheckForRing implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("CheckForRing.java");
			if (!Tabs.EQUIPMENT.isOpen()) {
				Tabs.EQUIPMENT.open();
			}
			if (hasRing()) {
				System.out
						.println("You have successfully equipped the proper ring. Continuing with script.");
				Tabs.INVENTORY.open();
				Variables.checkForRing = false;
			} else if (Variables.checkForRing) {
				if (!Tabs.INVENTORY.isOpen()) {
					Tabs.INVENTORY.open();
				}
				if (hasRingInInventory()) {

				} else if (!hasRingInInventory()) {
					Variables.error = "Please start with the proper ring equipped or in your inventory.";
					Context.get().getScriptHandler().shutdown();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasRing() {
		if (Equipment.getItems()[9].getId() == Ids.INVEN_DUNG_RING)
			return true;

		return false;
	}

	public boolean hasRingInInventory() {
		for (Item i : Inventory.getItems()) {
			if (i.getId() == Ids.INVEN_DUNG_RING) {
				if (i != null) {
					if (i.getWidgetChild().hover()) {
						if (Menu.select("Wear")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& Inventory
											.contains(new int[] { i.getId() })) {
								Task.sleep(15);
							}
						}
					}
				}
				if (hasRing())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean validate() {
		return Variables.checkForRing;
	}
}