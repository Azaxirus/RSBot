package spectrum.scripts.lizards.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class Banking implements Strategy {

	@Override
	public void execute() {
		System.out.println("Bank");
		SceneObject bank = (SceneObject) Bank.getNearest();
		if (bank.isOnScreen() && bank != null) {
			Variables.activity = "Interacting with Banker";
			if (!Bank.isOpen() && !Widgets.get(13).validate()) {
				if (Bank.open()) {
					final Timer timer = new Timer(800);
					while (timer.isRunning() && !Bank.isOpen()) {
						if (Bank.isOpen()) {
							break;
						} else {
							Task.sleep(15);
						}

					}
				}
			} else if (Bank.isOpen()) {
				for (final Item item : Inventory.getItems()) {
					for (int id : Ids.ALL_LIZARD_ID) {
						Variables.activity = "Depositing Lizards";
						if (item.getId() == id
								&& Inventory.getCount(id) > 1
								&& item.getWidgetChild()
										.interact("Deposit-All")) {
							Task.sleep(Random.nextInt(50, 100));
						} else if (item.getId() == id
								&& Inventory.getCount(id) == 1
								&& item.getWidgetChild().interact("Deposit")) {
							Task.sleep(Random.nextInt(50, 100));

						}
					}
				}
				if (Bank.isWithdrawNotedEnabled()) {
					Bank.setWithdrawNoted(false);
				}
				if (Variables.usingFamiliar) {
					if (Bank.depositFamiliarInventory()) {
						Variables.familiarIsFull = false;
					}
					if (Inventory.contains(new int[] { Variables.familiarId })) {
						if (Inventory.getCount(Variables.familiarId) > 1) {
							Bank.deposit(Variables.familiarId, -1);
						}
					} else {
						Bank.withdraw(Variables.familiarId, 1);
					}
					if (Settings.get(1176) == 0) {
						if (Inventory
								.contains(new int[] { Variables.familiarId })) {
							if (Bank.isOpen()) {
								Bank.close();
							}
							Item pouch = Inventory
									.getItem(Variables.familiarId);
							if (pouch != null) {
								if (pouch.getWidgetChild().hover()) {
									if (Menu.select("Summon")) {
										final Timer timer = new Timer(800);
										while (timer.isRunning()
												&& Settings.get(1176) == 0) {
											Task.sleep(15);
											if (Settings.get(1176) != 0) {
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				if (Inventory.getCount(Ids.rope_ID) != Variables.maxTraps) {
					if (Inventory.getCount(Ids.rope_ID) > Variables.maxTraps) {
						Bank.deposit(Ids.rope_ID,
								Inventory.getCount(Ids.rope_ID)
										- Variables.maxTraps);
					} else if (Inventory.getCount(Ids.rope_ID) < Variables.maxTraps) {
						Bank.withdraw(Ids.rope_ID, Variables.maxTraps
								- Inventory.getCount(Ids.rope_ID));
					}
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& Inventory.getCount(Ids.rope_ID) != Variables.maxTraps) {
						if (Inventory.getCount(Ids.rope_ID) == Variables.maxTraps) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}
				if (Inventory.getCount(Ids.net_ID) != Variables.maxTraps) {
					if (Inventory.getCount(Ids.net_ID) > Variables.maxTraps) {
						Bank.deposit(Ids.net_ID, Inventory.getCount(Ids.net_ID)
								- Variables.maxTraps);
					} else if (Inventory.getCount(Ids.net_ID) < Variables.maxTraps) {
						Bank.withdraw(Ids.net_ID, Variables.maxTraps
								- Inventory.getCount(Ids.net_ID));
					}
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& Inventory.getCount(Ids.net_ID) != Variables.maxTraps) {
						if (Inventory.getCount(Ids.net_ID) == Variables.maxTraps) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}

			}
			if (Variables.usingFamiliar) {
				if (Inventory.getCount(Ids.ALL_LIZARD_ID) == 0
						&& Inventory.getCount(Ids.net_ID) == Variables.maxTraps
						&& Inventory.getCount(Ids.rope_ID) == Variables.maxTraps
						&& Inventory
								.contains(new int[] { Variables.familiarId })
						&& Summoning.isFamiliarSummoned()
						&& !Inventory.contains(Ids.ALL_LIZARD_ID)) {
					Variables.banking = false;
					Variables.walking = true;
				}
			} else {
				if (Inventory.getCount(Ids.ALL_LIZARD_ID) == 0
						&& Inventory.getCount(Ids.net_ID) == Variables.maxTraps
						&& Inventory.getCount(Ids.rope_ID) == Variables.maxTraps
						&& !Inventory.contains(Ids.ALL_LIZARD_ID)) {
					Variables.banking = false;
					Variables.walking = true;
				}
			}
		} else {
			Camera.turnTo(bank);
		}
	}

	@Override
	public boolean validate() {
		return Inventory.getCount() != Variables.maxTraps * 2
				&& Methods.playerAtBank();
	}

}