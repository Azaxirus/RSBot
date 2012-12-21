package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.bot.Context;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.Methods;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;
import spectrum.tools.web.Access;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class SetupInventoryScrolls implements Strategy {

	public static boolean checkInventory() {
		for (int i : Variables.itemsToWithdraw) {
			if (!Inventory.contains(new int[] { i }))
				return false;
		}
		return true;

	}

	@Override
	public void execute() {
		try {
			System.out.println("SetupIventoryScrolls.java");
			NPC dungBanker = (NPC) Bank.getNearest();
			if (dungBanker != null && !Bank.isOpen()) {
				Camera.turnTo(dungBanker);
			}
			if (Inventory.contains(new int[] { Variables.pouchId })) {
				Variables.setupInventory = false;
			}

			if (!Bank.isOpen()) {
				Bank.open();
			}
			if (Settings.get(1781) == 0) {
				if (Inventory.contains(new int[] { Ids.INVEN_POUCH_KYATT })) {
					if (Bank.isOpen()) {
						Bank.close();
					}
					if (Inventory.isFull()) {
						Bank.depositInventory();
						final Timer timer = new Timer(800);
						while (timer.isRunning() && Inventory.isFull()) {
							Task.sleep(15);

						}
					}
					Item kyattPouch = Inventory.getItem(Ids.INVEN_POUCH_KYATT);
					if (kyattPouch != null) {
						if (kyattPouch.getWidgetChild().hover()) {
							if (Menu.select("Summon")) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& Settings.get(1781) == 0) {
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
					if (Bank.getItems() != null
							&& Bank.getItem(Ids.INVEN_POUCH_KYATT) == null) {
						Variables.error = "Out of item: "
								+ Access.getName(Ids.INVEN_POUCH_KYATT);
						Context.get().getScriptHandler().shutdown();
					}
				}
			} else {
				if (Inventory.contains(new int[] { Variables.scrollId })) {
					Bank.depositInventory();
				} else if (!Inventory.contains(new int[] { Variables.pouchId })) {
					for (int i = 0; i < Variables.itemsToWithdraw.length; i++) {
						withdraw(i);
					}
					if (Inventory.isFull()
							&& !Inventory
									.contains(new int[] { Variables.pouchId })) {
						Bank.depositInventory();
						final Timer timer = new Timer(800);
						while (timer.isRunning() && !Inventory.isFull()) {
							if (Inventory.isFull()) {
								break;
							} else {
								Task.sleep(15);
							}

						}
					}
				}
			}
			if (Inventory.contains(new int[] { Variables.pouchId })) {
				Variables.setupInventory = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		NPC banker = (NPC) Bank.getNearest();
		return Variables.setupInventory && banker != null
				&& Variables.leftClickOption;
	}

	public void withdraw(int i) {
		if (Inventory.contains(new int[] { Variables.pouchId })) {
			if (Bank.deposit(Variables.pouchId,
					Inventory.getCount(Variables.pouchId))) {
				final Timer timer = new Timer(200);
				while (timer.isRunning()
						&& Inventory.contains(new int[] { Variables.pouchId })) {
					if (!Inventory.contains(new int[] { Variables.pouchId })) {
						break;
					} else {
						Task.sleep(15);
					}
				}
			}
		}
		if (!Inventory.contains(new int[] { Variables.itemsToWithdraw[i] })) {
			if (Bank.getItem(Variables.itemsToWithdraw[i]) != null) {
				if (Bank.getItem(Variables.itemsToWithdraw[i]).getStackSize() > 0) {
					if (Bank.withdraw(Variables.itemsToWithdraw[i], Bank
							.getItem(Variables.itemsToWithdraw[i])
							.getStackSize())) {
						final Timer timer = new Timer(2000);
						while (timer.isRunning()
								&& !Inventory
										.contains(new int[] { Variables.itemsToWithdraw[i] })) {
							System.out.println("Sleeping");
							if (!Inventory
									.contains(new int[] { Variables.itemsToWithdraw[i] })) {
								break;
							} else {
								Task.sleep(15);
							}
						}
					}
				}
			}
			if (Bank.getItems() != null
					&& Bank.getItem(Variables.itemsToWithdraw[i]) == null
					&& !Inventory
							.contains(new int[] { Variables.itemsToWithdraw[i] })) {
				Variables.error = "Out of item: "
						+ Access.getName(Variables.itemsToWithdraw[i]);
				Context.get().getScriptHandler().shutdown();
			}
		}

	}
}