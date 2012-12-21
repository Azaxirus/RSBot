package spectrum.scripts.summoner.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.CameraNew;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class WalkToTrapDoor implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("WalkToTrapDoor.java");
			SceneObject trapDoor = SceneEntities.getNearest(Ids.OBJECT_TRAPDOORS);
			if (trapDoor != null) {
				if (!trapDoor.isOnScreen()) {
					Camera.turnTo(trapDoor);
					Camera.setPitch(Random.nextInt(5, 10));
				}
				if (trapDoor.hover()) {
					if (trapDoor.getId() == 28741) {
						if (Menu.select("Open")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning() && trapDoor != null) {
								Task.sleep(15);
								if (Players.getLocal().isMoving()) {
									timer.reset();
								}
							}
						}
					} else if (trapDoor.getId() == 28742) {
						if (Menu.select("Climb-down")) {
							CameraNew.setCamera(Random.nextInt(170, 180), 0);
							final Timer timer = new Timer(800);
							while (timer.isRunning() && trapDoor != null) {
								Task.sleep(15);
								if (Players.getLocal().isMoving()) {
									timer.reset();
								}
							}
						}
					}
				}
				if (Players.getLocal().getAnimation() == 827) {
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& Players.getLocal().getAnimation() != -1) {
						Task.sleep(15);
						if (Players.getLocal().getAnimation() == 827) {
							timer.reset();
						}
					}
					Variables.walkToObelisk = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		SceneObject holeCheck = SceneEntities.getNearest(19337);
		return (holeCheck != null || Players.getLocal().getAnimation() == 8941)
				&& !Variables.obeliskArea.contains(Players.getLocal());
	}
}