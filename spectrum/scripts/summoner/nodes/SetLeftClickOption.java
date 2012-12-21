package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Timer;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class SetLeftClickOption implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("SetLeftClickOption.java");
			if (Bank.isOpen()) {
				Bank.close();
			}
			if (Widgets.get(747, 2) != null) {
				if (Widgets.get(747, 2).hover()) {
					if (Widgets.get(747, 2).click(false)) {
						if (Menu.select("Select left-click option")) {
							final Timer timer = new Timer(2000);
							while (timer.isRunning()
									&& !Widgets.get(880).validate()) {
								Task.sleep(15);
							}
						}
						if (Widgets.get(880, 25) != null) {
							if (Widgets.get(880, 25).click(true)) {
								final Timer timer = new Timer(800);
								while (timer.isRunning()
										&& Settings.get(1790) != 7) {
									Task.sleep(15);
								}
							}
						}
						if (Widgets.get(880, 21).validate()) {
							if (Widgets.get(880, 21).click(true)) {
								final Timer timer1 = new Timer(800);
								while (timer1.isRunning()
										&& Widgets.get(880).validate()) {
									Task.sleep(15);
								}
								if (Settings.get(1790) == 7) {
									Variables.leftClickOption = true;
									Tabs.INVENTORY.open();
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		return !Variables.leftClickOption;
	}
}