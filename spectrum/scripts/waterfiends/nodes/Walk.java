package spectrum.scripts.waterfiends.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Ids;
import spectrum.tools.map.Paths;
import spectrum.tools.structure.Strategy;

public class Walk implements Strategy {

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return Areas.ZANARIS.contains(Players.getLocal());
	}

	@Override
	public void execute() {
		if (finished()) {
			WidgetChild summoning = Widgets.get(747, 23);
			SceneObject obelisk = SceneEntities.getNearest(Ids.ZANARIS_OBELISK);
			if (summoning != null && Integer.parseInt(summoning.getText()) < 40
					&& obelisk != null && Calculations.distanceTo(obelisk) < 10) {
				if (!obelisk.isOnScreen()) {
					Camera.turnTo(obelisk);
				}
				if (obelisk.interact("Renew-points", "Small obelisk")) {
					final Timer timer = new Timer(2000);
					while (timer.isRunning() && summoning != null
							&& Integer.parseInt(summoning.getText()) < 40) {
						if (Players.getLocal().getAnimation() != -1)
							timer.reset();
						Task.sleep(15);
					}
				}
			} else {
				SceneObject ring = SceneEntities
						.getNearest(Ids.ZANARIS_FAIRY_RING);
				WidgetChild teleportOption = Widgets.get(734, 21);
				if (teleportOption != null && teleportOption.isOnScreen()) {
					if (findCavernWidget() != null
							&& findCavernWidget().interact("B J Q")) {
						if (teleportOption != null
								&& teleportOption.interact("Teleport")) {
							final Timer timer = new Timer(2000);
							while (timer.isRunning()) {
								if (Players.getLocal().getAnimation() != -1)
									timer.reset();
								Task.sleep(15);
							}
						}
					}
				} else if (ring != null && Calculations.distanceTo(ring) < 10) {
					if (ring.isOnScreen()) {
						if (ring.interact("Use", "Fairy ring")) {
							final Timer timer = new Timer(2000);
							while (timer.isRunning()
									&& (teleportOption != null
											&& !teleportOption.isOnScreen() || teleportOption == null)) {
								if (Players.getLocal().isMoving())
									timer.reset();
								Task.sleep(15);
							}
						}
					} else {
						Walking.walk(ring);
						Camera.turnTo(ring);
					}

				} else
					Walking.newTilePath(Paths.PATH_TO_RING).traverse();
			}
		} else if (!Areas.ZANARIS_BANK.contains(Players.getLocal())) {
			Walking.newTilePath(Paths.PATH_TO_RING).reverse().traverse();
		}
	}

	public WidgetChild findCavernWidget() {
		WidgetChild teleportOptions = Widgets.get(735, 13);
		for (WidgetChild w : teleportOptions.getChildren()) {
			if (w != null && w.getText().equals("B J Q"))
				return w;
		}
		return null;
	}

	public int numberOfPotions(int[] ids) {
		int count = 0;
		for (int i : ids)
			if (Inventory.contains(i))
				count = count + Inventory.getCount(i);
		return count;
	}

	public boolean finished() {
		if (numberOfPotions(Ids.FLASK_SUMMONING) != 1)
			return false;
		if (numberOfPotions(Ids.FLASK_RANGING) != 2)
			return false;
		if (numberOfPotions(Ids.UNICORN_POUCH) != 2)
			return false;
		if (Inventory.getItem(Ids.UNICORN_SCROLL[0]).getStackSize() != 100)
			return false;
		if (Inventory.getCount(Ids.FOOD_SHARK[0]) != 14)
			return false;
		return true;
	}
}
