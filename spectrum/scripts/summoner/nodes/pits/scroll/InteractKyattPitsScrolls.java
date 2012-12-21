package spectrum.scripts.summoner.nodes.pits.scroll;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class InteractKyattPitsScrolls implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("InteractKyattPitsScolls.java");
			if (Inventory.contains(new int[] { Variables.pouchId })) {
				if (Widgets.get(1188, 24) != null
						&& Widgets.get(1188, 24).isOnScreen()) {
					if (Widgets.get(1188, 24).hover()) {
						if (Menu.select("Continue")) {
							final Timer timer1 = new Timer(2000);
							while (timer1.isRunning()
									&& !Variables.doorArea.contains(Players
											.getLocal())) {
								Task.sleep(15);
								if (Players.getLocal().getAnimation() == 8939) {
									timer1.reset();
									Camera.setAngle(0);
								}
							}
						}
					}
				} else if (Widgets.get(747, 2) != null) {
					if (Widgets.get(747, 2).hover()) {
						if (Widgets.get(747, 2).click(false)) {
							if (Menu.select("Interact")) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& !Widgets.get(1188, 24).isOnScreen()) {
									Task.sleep(15);

								}

							}
						}
					}
				}
			} else {
				Variables.setupInventory = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		NPC dungBanker = NPCs.getNearest(Ids.BANKER_PITS);
		return Settings.get(1781) != 0 && dungBanker != null
				&& dungBanker.isOnScreen() && !Variables.setupInventory;
	}
}