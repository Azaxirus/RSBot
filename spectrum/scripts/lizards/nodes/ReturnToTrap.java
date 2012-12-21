package spectrum.scripts.lizards.nodes;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.tab.Inventory;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.structure.Strategy;

public class ReturnToTrap implements Strategy {
	@Override
	public void execute() {
		Methods.walkToCenter();
	}

	@Override
	public boolean validate() {
		return !Methods.playerAtTrappingSpot()
				&& !Inventory.isFull()
				&& Calculations.distanceTo(Variables.trappingSpot.getNearest()) < 10;
	}
}