package spectrum.scripts.lizards.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.structure.Strategy;

public class RenewFamiliar implements Strategy {

	@Override
	public void execute() {
		Item pouch = Inventory.getItem(Variables.familiarId);
		if (pouch != null) {
			if (pouch.getWidgetChild().hover()) {
				if (Menu.select("Summon")) {
					final Timer timer = new Timer(800);
					while (timer.isRunning() && Settings.get(1176) == 0) {
						Task.sleep(15);
						if (Settings.get(1176) != 0) {
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean validate() {
		return Summoning.getTimeLeft() / 60 < 1 && Variables.usingFamiliar;
	}

}