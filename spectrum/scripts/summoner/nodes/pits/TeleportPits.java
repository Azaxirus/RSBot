package spectrum.scripts.summoner.nodes.pits;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.CameraNew;
import spectrum.tools.interactive.Equipment;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class TeleportPits implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("TeleportPits.java");
			if (Inventory.contains(new int[] { Variables.pouchId })) {
				if (!Tabs.EQUIPMENT.isOpen()) {
					if (Tabs.EQUIPMENT.open()) {
						final Timer timer = new Timer(800);
						while (timer.isRunning() && !Tabs.EQUIPMENT.isOpen()) {
							Task.sleep(15);
						}
					}
				}
				if (Widgets.get(387, 32) != null) {
					if (Equipment.getItem(Ids.INVEN_TOKKUL_ZO_RING) != null) {
						if (Equipment.getItem(Ids.INVEN_TOKKUL_ZO_RING)
								.getWidgetChild().hover()) {
							if (Menu.select("Teleport")) {
								Task.sleep(2000);
							}
						}
					}
				}
				if (Widgets.get(1188, 28) != null) {
					if (Widgets.get(1188, 28).hover()) {
						if (Menu.select("Continue")) {
							final Timer timer1 = new Timer(2000);
							while (timer1.isRunning()
									&& Players.getLocal().getAnimation() != 8941) {
								Task.sleep(15);
								if (Players.getLocal().getAnimation() == 8939) {
									timer1.reset();
									CameraNew.setCamera(Random.nextInt(0, 15),
											0);
								}
							}
						}
					}
				}
				if (Inventory.contains(new int[] { Variables.pouchId })) {
					Variables.doneWithObelisk = true;
					Variables.walkToBank = true;
				} else if (Inventory
						.contains(new int[] { Variables.pouchSecondary })) {
					Variables.doneWithObelisk = false;
					Variables.walkToBank = false;
				} else {
					Variables.walkToBank = true;
				}
			} else {
				Variables.doneWithObelisk = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		SceneObject obelisk = SceneEntities.getNearest(Ids.OBJECT_OBELISK);
		return Variables.doneWithObelisk && obelisk != null
				&& obelisk.isOnScreen();
	}
}