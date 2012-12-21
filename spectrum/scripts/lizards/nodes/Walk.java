package spectrum.scripts.lizards.nodes;

import org.powerbot.game.api.methods.tab.Inventory;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.interactive.Walking;
import spectrum.tools.structure.Strategy;

public class Walk implements Strategy {

	@Override
	public void execute() {
		Variables.trapping = false;
		Variables.walking = true;
		if (Inventory.getCount() >= 25 && !Methods.playerAtBank()) {
			Walking.newTilePath(Variables.swampPath).traverse();
			Variables.activity = "Walking..";
		} else if (!Methods.playerAtTrappingSpot()
				&& Inventory.getCount() == Variables.maxTraps * 2) {
			Walking.newTilePath(Variables.swampPath).reverse().traverse();
		}
		Variables.activity = "Walking..";

	}

	@Override
	public boolean validate() {
		return Inventory.isFull() && !Methods.playerAtBank()
				|| !Methods.playerAtTrappingSpot()
				&& Inventory.getCount() == Variables.maxTraps * 2;
	}
}