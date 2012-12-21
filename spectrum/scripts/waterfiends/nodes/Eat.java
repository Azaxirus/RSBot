package spectrum.scripts.waterfiends.nodes;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.tools.structure.Strategy;

public class Eat implements Strategy {

	@Override
	public void execute() {
		Item shark = Inventory.getItem(385);
		if (shark != null)
			shark.getWidgetChild().interact("Eat", "Shark");
	}

	public static int getHpPercent() {
		int currHP = Widgets.get(748, 5).getHeight();
		return Math.abs(100 - (100 * currHP / 28));
	}

	@Override
	public boolean validate() {
		return getHpPercent() < 50;
	}
}
