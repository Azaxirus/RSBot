package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class InteractObelisk implements Strategy {

	@Override
	public void execute() {

		try {
			System.out.println("InteractObelisk.java");
			SceneObject obelisk = SceneEntities
					.getNearest(Ids.OBJECT_OBELISK);
			if (obelisk != null) {
				Camera.turnTo(obelisk);
				if (!obelisk.isOnScreen()) {
					Walking.walk(new Tile(2333, 10014, 0));
				}
				if (Widgets.get(1370).validate()) {
					if (Widgets.get(1370, 20) != null) {
						if (Inventory
								.contains(new int[] { Variables.pouchSecondary })
								&& !Inventory
										.contains(new int[] { Variables.pouchId })
								&& Inventory
										.contains(new int[] { Variables.pouchCharm })
								&& Inventory
										.contains(new int[] { Ids.INVEN_SPIRIT_SHARD })
								&& Inventory
										.contains(new int[] { Ids.INVEN_POUCH_EMPTY })) {
							if (Widgets.get(1370, 20).click(true)) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& Widgets.get(1370) != null) {
									Task.sleep(15);
								}
							}
						} else {
							Variables.doneWithObelisk = true;
						}
					}
				} else if (obelisk.hover()) {
					if (Menu.select("Infuse-pouch")) {
						final Timer timer = new Timer(800);
						while (timer.isRunning()
								&& !Widgets.get(1370).validate()) {
							Task.sleep(15);
							if (Players.getLocal().isMoving()) {
								timer.reset();
							}
						}
					}
				}
				if (Inventory.contains(new int[] { Variables.pouchId })) {
					Variables.doneWithObelisk = true;
				}
			} else {
				if (Variables.obeliskArea.contains(Players.getLocal())) {
					Walking.walk(new Tile(2333, 10014, 0));
				}

			}
			Variables.walkToObelisk = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		return Variables.obeliskArea.contains(Players.getLocal())
				&& !Variables.doneWithObelisk || Variables.walkToObelisk;
	}
}