package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class SummonKyatt implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("SummonKyatt.java");
			if (Inventory.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
				if (Bank.isOpen()) {
					Bank.close();
				}
				Item kyattPouch = Inventory
						.getItem(Ids.INVEN_POUCH_KYATT);
				if (kyattPouch != null) {
					if (kyattPouch.getWidgetChild().hover()) {
						if (Menu.select("Summon")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning() && Settings.get(1781) == 0) {
								Task.sleep(15);
								if (Settings.get(1781) != 0) {
									break;
								}
							}
						}
					}
				}
			} else {
				if (!Bank.isOpen()) {
					Bank.open();
				}
				if (Inventory.isFull()) {
					Bank.depositInventory();
					final Timer timer = new Timer(800);
					while (timer.isRunning() && Inventory.isFull()) {
						Task.sleep(15);

					}
				}
				if (Bank.getItem(Ids.INVEN_POUCH_KYATT) != null) {
					if (Bank.withdraw(Ids.INVEN_POUCH_KYATT, 1)) {
						System.out.println("Withdrawing kyatt pouch");
						final Timer timer = new Timer(2000);
						while (timer.isRunning()
								&& !Inventory
										.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
							if (!Inventory
									.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
								break;
							} else {
								Task.sleep(15);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		return Settings.get(1781) == 0 && !Variables.setupInventory;
	}
}