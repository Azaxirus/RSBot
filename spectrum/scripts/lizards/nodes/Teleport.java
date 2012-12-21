package spectrum.scripts.lizards.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Timer;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Log;
import spectrum.tools.Methods;
import spectrum.tools.interactive.Actionbar;
import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Ids;
import spectrum.tools.map.Paths;
import spectrum.tools.structure.Strategies;
import spectrum.tools.structure.Strategy;

public class Teleport implements Strategy {

	@Override
	public void execute() {
		Variables.trapping = false;
		Variables.walking = true;
		if (Methods.playerAtTrappingSpot()) {
			if (Inventory.contains(new int[] { 563, 565 })) {
				if (!Actionbar.isOpen()) {
					Actionbar.open();
				}
				if (Actionbar.getSlotWithTeleport(1430) != null) {
					if (Actionbar.getSlotWithTeleport(1430).activate(false)) {
						final Timer timer = new Timer(800);
						while (timer.isRunning()
								&& Methods.playerAtTrappingSpot()) {
							if (!Methods.playerAtTrappingSpot()) {
								break;
							} else if (Players.getLocal().getAnimation() != -1) {
								timer.reset();
							} else {
								Task.sleep(15);
							}
						}
					}
				} else {
					Log.log("ERROR",
							"Please put the Kharyll Teleport onto your Ability Bar.");
					Strategies.remove(new BankingTeleport(), new WalkTo());
					Strategies.add(new Banking(), new Walk());
				}
			}
		} else {
			Walking.newTilePath(Paths.barToBank).traverse();
		}

	}

	@Override
	public boolean validate() {
		return Inventory.isFull()
				&& Inventory.contains(Variables.itemsToWithdraw)
				|| Inventory.contains(Ids.ALL_LIZARD_ID)
				&& Methods.playerAtBar() && !Methods.playerAtBank();
	}
}