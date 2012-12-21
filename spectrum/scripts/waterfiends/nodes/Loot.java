package spectrum.scripts.waterfiends.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Areas;
import spectrum.tools.structure.Strategy;

public class Loot implements Strategy {
	public enum Items {
		SNAPEGRASS(232, "Snape grass"), SHARK(385, "Shark"), SEAWEED(402,
				"Seaweed"), MITHRILORE(448, "Mithril Ore"), WATERORB(572,
				"Water orb"), MITHRILARROW(888, "Mithril arrow"), RUNEHELM(
				1147, "Rune helm"), RUNEFULL(1163, "Rune full helm"), WATERBSTAFF(
				1395, "Water battlestaff"), BLUEVAMBS(2487,
				"Blue d'hide vambraces"), ADDYBAR(2361, "Adamant bar"), SAPPBOLTS(
				9337, "Sapphire bolts"), GOLDCHARM(12158, "Gold charm"), GREENCHARM(
				12159, "Green charm"), CRIMSONCHARM(12160, "Crimson charm"), BLUECHARM(
				12163, "Blue charm");
		int id;
		String name;

		Items(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

	}

	@Override
	public void execute() {
		for (final Items i : Items.values()) {
			GroundItem g = GroundItems.getNearest(new Filter<GroundItem>() {
				@Override
				public boolean accept(final GroundItem l) {
					if (l.getId() == i.getId() && l.getLocation().getX() < 1749)
						return true;
					return false;

				}
			});
			if (g != null) {
				if (!g.isOnScreen()) {
					Camera.turnTo(g);
				}
				if (Calculations.distanceTo(g) > 10)
					Walking.walk(g);
				if (Inventory.isFull()) {
					Item shark = Inventory.getItem(385);
					if (shark != null) {
						shark.getWidgetChild().interact("Eat", "Shark");
					}
				}
				if (g.interact("Take", i.getName())) {
					final Timer timer = new Timer(800);
					while (timer.isRunning() && g != null) {
						if (Players.getLocal().isMoving()) {
							timer.reset();
						} else {
							Task.sleep(15);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean validate() {
		if (Areas.ZANARIS.contains(Players.getLocal()))
			return false;
		for (Items i : Items.values()) {
			GroundItem g = GroundItems.getNearest(i.getId());
			if (g != null)
				return true;
		}
		return false;
	}
}
