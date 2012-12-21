package spectrum.scripts.lizards.nodes;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class WalkTo implements Strategy {

	@Override
	public void execute() {
		Variables.trapping = false;
		Variables.walking = true;
		if (!Methods.playerAtTrappingSpot()
				&& Inventory.getCount(Ids.rope_ID) == Variables.maxTraps
				&& Inventory.getCount(Ids.net_ID) == Variables.maxTraps
				&& Inventory.contains(Variables.itemsToWithdraw)
				&& !Inventory.contains(Ids.ALL_LIZARD_ID)) {
			Walking.newTilePath(Variables.swampPath).reverse().traverse();
		}
		Variables.activity = "Walking..";
	}

	@Override
	public boolean validate() {
		int count1 = Inventory.getCount(Ids.rope_ID);
		int count2 = Inventory.getCount(Ids.net_ID);
		Item item1 = Inventory
				.getItem(new int[] { Variables.itemsToWithdraw[0] });
		Item item2 = Inventory
				.getItem(new int[] { Variables.itemsToWithdraw[1] });
		return !Methods.playerAtTrappingSpot() && count1 == Variables.maxTraps
				&& count2 == Variables.maxTraps && item1 != null
				&& item1.getStackSize() == 1 && item2 != null
				&& item2.getStackSize() == 2
				&& !Inventory.contains(Ids.ALL_LIZARD_ID)
				|| Variables.trapLocation.size() == 0 && count1 == 0
				&& count2 == 0 && !Methods.playerAtBank();
	}
}