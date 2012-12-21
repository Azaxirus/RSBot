package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.bot.Context;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class SummonKyattScrolls implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("SummonKyattScrolls.java");
			NPC dungBanker = NPCs.getNearest(Ids.BANKER_DUNG);
			if (Inventory.isFull()) {
				Bank.depositInventory();
				final Timer timer = new Timer(800);
				while (timer.isRunning() && Inventory.isFull()) {
					Task.sleep(15);

				}
			}
			if (Inventory.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
				if (Inventory.getCount(Ids.INVEN_POUCH_KYATT) > 1) {
					System.out.println("Depositing extra kyatt pouches.");
					if (!Bank.isOpen()) {
						if (dungBanker.hover()) {
							if (Menu.select("Bank")) {
								final Timer timer = new Timer(800);
								while (timer.isRunning() && !Bank.isOpen()) {
									Task.sleep(15);

								}
							}
						}
					} else if (Bank.isOpen()) {
						if (Inventory
								.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
							if (Bank.deposit(
									Ids.INVEN_POUCH_KYATT,
									Inventory
											.getCount(Ids.INVEN_POUCH_KYATT) - 1)) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& Inventory
												.getCount(Ids.INVEN_POUCH_KYATT) > 1) {
									Task.sleep(15);
								}
								if (Inventory
										.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
									Bank.close();
								}
							}
						}
					}
				} else {
					System.out.println("Summoning new kyatt");
					if (Inventory
							.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
						Item kyattPouch = Inventory
								.getItem(Ids.INVEN_POUCH_KYATT);
						if (Bank.isOpen()) {
							Bank.close();
						}
						if (kyattPouch != null) {
							if (kyattPouch.getWidgetChild().hover()) {
								if (Menu.select("Summon")) {
									final Timer timer = new Timer(800);
									while (timer.isRunning()
											&& Settings.get(1781) == 0) {
										Task.sleep(15);
									}
								}
							}
						}
					}
				}
			} else if (dungBanker != null) {
				System.out.println("Grabbing Kyatt Pouch");
				if (Bank.isOpen()) {
					if (Inventory.contains(new int[] { Variables.scrollId })) {
						if (Bank.deposit(Variables.scrollId,
								Inventory.getCount(Variables.scrollId))) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& Inventory.getCount(Variables.scrollId) > 1) {
								Task.sleep(15);
							}
						}
					} else if (Inventory
							.contains(new int[] { Variables.pouchId })) {
						if (Bank.deposit(Variables.pouchId,
								Inventory.getCount(Variables.pouchId))) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& Inventory.getCount(Variables.pouchId) > 1) {
								Task.sleep(15);
							}
						}
					} else if (!Inventory
							.contains(new int[] { Ids.INVEN_POUCH_KYATT })
							&& Inventory.getCount() < 28) {
						Bank.search("ky");
						if (Bank.getItem(Ids.INVEN_POUCH_KYATT) != null) {
							if (Bank.withdraw(Ids.INVEN_POUCH_KYATT, 1)) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& !Inventory
												.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
									Task.sleep(15);
								}
								if (Inventory
										.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
									Bank.close();
								}
							}
						} else {
							Bank.close();
							Variables.error = "Out of kyatt pouches";
							Context.get().getScriptHandler().shutdown();
						}

					} else {
						Bank.close();
					}
				} else if (!Bank.isOpen()) {
					if (!dungBanker.isOnScreen()) {
						Camera.turnTo(dungBanker);
					}
					if (dungBanker.hover()) {
						if (Menu.select("Bank")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning() && !Bank.isOpen()) {
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