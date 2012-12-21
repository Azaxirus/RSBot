package spectrum.scripts.waterfiends.nodes;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.tools.Log;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class Banking implements Strategy {

	@Override
	public boolean validate() {
		return Areas.ZANARIS_BANK.contains(Players.getLocal())
				&& Bank.getNearest() != null && !finished();
	}

	@Override
	public void execute() {
		if (Bank.isOpen()) {
			if (containsLoot(Ids.ALL_ITEMS)) {
				Log.log("depositing", "Clearing Inventory for new trip.");
				Bank.depositInventory();
			}
			if (finished()) {
				Log.log("banking", "Completed inventory. Attempting to close.");
				Bank.close();
			} else
				withdrawInventory();
		} else {
			Log.log("banking", "Opening bank.");
			Bank.open();
		}
	}

	public void withdrawInventory() {
		if (Inventory.getCount(Ids.FLASK_RANGING[0]) != 2) {
			Log.log("withdrawing", "Ranging Flasks");
			Log.log("amount",
					"Have: " + Inventory.getCount(Ids.FLASK_RANGING[0]));
			withdrawItem(Ids.FLASK_RANGING, 2);
		}
		if (Inventory.getCount(Ids.FLASK_SUMMONING[0]) != 1) {
			Log.log("withdrawing", "Summoning Flasks");
			Log.log("amount",
					"Have: " + Inventory.getCount(Ids.FLASK_SUMMONING[0]));
			withdrawItem(Ids.FLASK_SUMMONING, 1);
		}
		if (Inventory.getCount(Ids.UNICORN_POUCH[0]) != 2) {
			Log.log("withdrawing", "Unicorn Pouch");
			Log.log("amount",
					"Have: " + Inventory.getCount(Ids.UNICORN_POUCH[0]));
			withdrawItem(Ids.UNICORN_POUCH, 2);
		}
		if (Inventory.getItem(Ids.UNICORN_SCROLL[0]) == null
				|| Inventory.getItem(Ids.UNICORN_SCROLL[0]) != null
				&& Inventory.getItem(Ids.UNICORN_SCROLL[0]).getStackSize() != 100) {
			Log.log("withdrawing", "Unicorn Scroll");
			Log.log("amount",
					"Have: "
							+ (Inventory.getItem(Ids.UNICORN_SCROLL[0]) != null ? Inventory
									.getItem(Ids.UNICORN_SCROLL[0])
									.getStackSize() : 0));
			withdrawItem(Ids.UNICORN_SCROLL, 100);
		}
		if (Inventory.getCount(Ids.FOOD_SHARK[0]) != 14) {
			Log.log("withdrawing", "Shark");
			Log.log("amount", "Have: " + Inventory.getCount(Ids.FOOD_SHARK[0]));
			withdrawItem(Ids.FOOD_SHARK, 14);
		}

	}

	public boolean finished() {
		if (Inventory.getCount(Ids.FLASK_SUMMONING[0]) != 1)
			return false;
		if (Inventory.getCount(Ids.FLASK_RANGING[0]) != 2)
			return false;
		if (Inventory.getCount(Ids.UNICORN_POUCH[0]) != 2)
			return false;
		if (Inventory.getItem(Ids.UNICORN_SCROLL[0]).getStackSize() != 100)
			return false;
		if (Inventory.getCount(Ids.FOOD_SHARK[0]) != 14)
			return false;
		return true;
	}

	public boolean containsAtleastOneOf(int[] ids) {
		for (int i : ids)
			if (Inventory.contains(i))
				return true;
		return false;
	}

	public int greatestDoseAvailable(int[] ids) {
		for (int i : ids)
			if (Bank.getItem(i) != null && Bank.getItem(i).getStackSize() > 1)
				return i;
		return -1;
	}

	public int numberOfPotions(int[] ids) {
		int count = 0;
		for (int i : ids)
			if (Inventory.contains(i))
				count = count + Inventory.getCount(i);
		return count;
	}

	public boolean contains(int id, int[][] ids) {
		for (int[] i : ids)
			for (int j : i)
				if (id == j)
					return true;
		return false;
	}

	public boolean containsLoot(int[][] ids) {
		for (Item i : Inventory.getItems()) {
			if (!contains(i.getId(), ids)) {
				return true;
			}
		}
		return false;
	}

	public void depositAll(int[] ids) {
		for (int i : ids) {
			Bank.deposit(i, 1000);
		}
	}

	public void withdrawItem(int[] ids, int amount) {
		if (containsAtleastOneOf(ids)) {
			Log.log("amount", "Need: " + (amount - numberOfPotions(ids)));
			if (numberOfPotions(ids) < amount)
				Bank.withdraw(greatestDoseAvailable(ids), amount
						- numberOfPotions(ids));
			else if (numberOfPotions(ids) > amount) {
				System.out.println("deposit");
				depositAll(ids);
			}
		} else {
			Log.log("amount", "Need: " + amount);
			Bank.withdraw(greatestDoseAvailable(ids), amount);
		}
	}

}
