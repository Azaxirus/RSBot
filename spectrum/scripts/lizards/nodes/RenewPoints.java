package spectrum.scripts.lizards.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.map.Paths;
import spectrum.tools.structure.Strategy;

public class RenewPoints implements Strategy {

	@Override
	public void execute() {
		SceneObject obelisk = SceneEntities.getNearest(29953);
		if (Summoning.getPoints() < 30) {
			if (obelisk != null && Calculations.distanceTo(obelisk) < 15) {
				if (!obelisk.isOnScreen()) {
					Camera.turnTo(obelisk);
				}
				if (obelisk.interact("Renew-points")) {
					final Timer timer = new Timer(800);
					while (timer.isRunning() && Summoning.getPoints() > 30) {
						Task.sleep(15);
						if (Summoning.getPoints() < 30) {
							break;
						}
					}
				}
			} else {
				Walking.newTilePath(Paths.toObelisk).traverse();
			}
		} else {
			Walking.newTilePath(Paths.toObelisk).reverse().traverse();
		}
	}

	@Override
	public boolean validate() {
		return (Summoning.getPoints() < 30 || Methods.playerAtObelisk())
				&& Variables.usingFamiliar;
	}

}