package spectrum.scripts.waterfiends.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.tab.Summoning.Familiar;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.tools.Log;
import spectrum.tools.Methods;
import spectrum.tools.interactive.Actionbar;
import spectrum.tools.interactive.Walking;
import spectrum.tools.interactive.Actionbar.Constitution_Abilities;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class Fighting implements Strategy {
	int[] loot = { 448, 571, 888, 1147, 1163, 12158, 12159, 12160, 12163, };
	String[] names = { "Mithril ore", "Water orb", "Mithril arrow",
			"Rune helm", "Rune full helm", "Gold charm", "Green charm",
			"Crimson charm", "Blue charm", };

	@Override
	public void execute() {
		if (Inventory.contains(385)) {
			if (Actionbar.adrenalinePercentage() == 100) {
				turnOnMomentum();
			}
			Methods.turnOnRun();
			Camera.setPitch(0);
			if (Inventory.contains(Ids.UNICORN_POUCH)) {
				NPC unicorn = NPCs.getNearest(6822);
				if (unicorn == null) {
					Log.log("INFO", "Summoning Unicorn");
					if (Summoning.getFamiliar() != null)
						System.out.println(Summoning.getFamiliar().getName());
					Item pouch = Inventory.getItem(Ids.UNICORN_POUCH);
					if (pouch != null) {
						pouch.getWidgetChild().interact("Summon",
								"Unicorn stallion pouch");
					}
				}
			}
			NPC waterfiend;
			if (Players.getLocal().getInteracting() != null) {
				waterfiend = (NPC) Players.getLocal().getInteracting();
			} else
				waterfiend = NPCs.getNearest(new Filter<NPC>() {
					@Override
					public boolean accept(final NPC l) {
						if (l.getId() == 5361 && !l.isInCombat() && l.isIdle()
								&& l.getLocation().getX() < 1749)
							return true;
						return false;

					}
				});
			if (!Players.getLocal().isInCombat()
					&& (Players.getLocal().isMoving() || Players.getLocal()
							.isIdle())) {
				if (waterfiend != null) {
					if (!waterfiend.isOnScreen()) {
						Camera.turnTo(waterfiend);
					}
					if (waterfiend.interact("Attack")) {
						final Timer timer = new Timer(800);
						while (timer.isRunning()
								&& !Players.getLocal().isInCombat()) {
							if (Players.getLocal().isInCombat()) {
								break;
							} else if (Players.getLocal().isMoving()) {
								timer.reset();
							} else {
								Task.sleep(15);
							}
						}
					}
				}
			}
		} else {
			SceneObject ring = SceneEntities.getAt(new Tile(1737, 5342, 0));
			if (ring != null) {
				if (ring.isOnScreen()) {
					if (ring.interact("Use", "Fairy ring")) {
						final Timer timer = new Timer(2000);
						while (timer.isRunning()
								&& !Areas.ZANARIS.contains(Players.getLocal())) {
							if (Players.getLocal().isMoving())
								timer.reset();
							Task.sleep(15);
						}
					}
				} else {
					Walking.walk(ring);
					Camera.turnTo(ring);
				}
			}
		}
	}

	public void turnOnMomentum() {
		if (!Actionbar.isOpen())
			Actionbar.open();
		if (Actionbar.getSlot(0).activate(false)) {
			final Timer timer = new Timer(800);
			while (timer.isRunning() && Actionbar.adrenalinePercentage() == 100) {
				Task.sleep(15);
			}
		}
		Actionbar.close();
	}

	@Override
	public boolean validate() {
		return NPCs.getNearest(5361) != null && Inventory.contains(385);
	}

}
