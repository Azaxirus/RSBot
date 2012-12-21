package spectrum.scripts.lizards.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class TrapSummoning implements Strategy {
	@Override
	public void execute() {
		/* System.out.println("Trap"); */
		Variables.trapping = true;
		SceneObject[] trap = SceneEntities.getLoaded(new Filter<SceneObject>() {
			@Override
			public boolean accept(final SceneObject l) {
				for (int id : Ids.trap_ID) {
					if (l.getId() == id && Variables.trappingSpot.contains(l))
						return true;
				}
				return false;

			}
		});
		WidgetChild summoningMenuClose = Widgets.get(671, 13);
		if (!Variables.familiarIsFull) {
			if (Inventory.contains(Ids.ALL_LIZARD_ID)
					&& Inventory.getCount(Ids.ALL_LIZARD_ID) > 10) {
				if (!summoningMenuClose.isOnScreen()) {
					if (Summoning.getFamiliar() != null)
						if (Summoning.getFamiliar().interact("Store")) {
							final Timer timer = new Timer(3000);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				} else {
					for (final Item item : Inventory.getItems()) {
						for (int id : Ids.ALL_LIZARD_ID) {
							Variables.activity = "Storing Lizards";
							if (!Variables.familiarIsFull) {
								if (item.getId() == id
										&& Inventory.getCount(id) > 1
										&& item.getWidgetChild().interact(
												"Store-All")) {
									Task.sleep(Random.nextInt(50, 100));
								} else if (item.getId() == id
										&& Inventory.getCount(id) == 1
										&& item.getWidgetChild().interact(
												"Store")) {
									Task.sleep(Random.nextInt(50, 100));

								}
							} else {
								Variables.familiarIsFull = true;
								if (summoningMenuClose != null
										&& summoningMenuClose.interact("Close")) {
									final Timer timer = new Timer(800);
									while (timer.isRunning()
											&& summoningMenuClose.isOnScreen()) {
										if (!summoningMenuClose.isOnScreen()) {
											break;
										} else {
											Task.sleep(15);
										}
									}
								}
								break;
							}
						}
					}
				}
			} else if (!Inventory.contains(Ids.ALL_LIZARD_ID)) {
				if (summoningMenuClose != null
						&& summoningMenuClose.interact("Close")) {
					final Timer timer = new Timer(800);
					while (timer.isRunning() && summoningMenuClose.isOnScreen()) {
						if (!summoningMenuClose.isOnScreen()) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}
			}
		}
		if (!summoningMenuClose.isOnScreen()) {
			Methods.checkSettings();
			if (Variables.options[0]) {
				if (!summoningMenuClose.isOnScreen()) {
					Methods.setTrap(trap);
					Methods.releaseTrap();
					Methods.pickUp();
				} else {
					if (Variables.familiarIsFull)
						if (summoningMenuClose.interact("Close")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				}
			} else if (Variables.options[1]) {
				if (!summoningMenuClose.isOnScreen()) {
					Methods.releaseTrap();
					Methods.setTrap(trap);
					Methods.pickUp();
				} else {
					if (Variables.familiarIsFull)
						if (summoningMenuClose.interact("Close")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				}
			} else if (Variables.options[2]) {
				if (!summoningMenuClose.isOnScreen()) {
					Methods.setTrap(trap);
					Methods.pickUp();
					Methods.releaseTrap();
				} else {
					if (Variables.familiarIsFull)
						if (summoningMenuClose.interact("Close")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				}
			} else if (Variables.options[3]) {
				if (!summoningMenuClose.isOnScreen()) {
					Methods.pickUp();
					Methods.setTrap(trap);
					Methods.releaseTrap();
				} else {
					if (Variables.familiarIsFull)
						if (summoningMenuClose.interact("Close")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				}
			} else if (Variables.options[4]) {
				if (!summoningMenuClose.isOnScreen()) {
					Methods.pickUp();
					Methods.releaseTrap();
					Methods.setTrap(trap);
				} else {
					if (Variables.familiarIsFull)
						if (summoningMenuClose.interact("Close")) {
							final Timer timer = new Timer(800);
							while (timer.isRunning()
									&& !summoningMenuClose.isOnScreen()) {
								if (summoningMenuClose.isOnScreen()) {
									break;
								} else {
									Task.sleep(15);
								}
							}
						}
				}
			}
			for (Tile t : Variables.trapLocation.toArray(new Tile[0])) {
				SceneObject[] nets = SceneEntities
						.getLoaded(new Filter<SceneObject>() {
							@Override
							public boolean accept(final SceneObject l) {
								for (int id : Ids.trap_ID) {
									if (l.getId() == id
											&& Variables.trappingSpot
													.contains(l))
										return true;
								}
								return false;

							}
						});

				for (SceneObject s : nets)
					if (Methods.whichArray(s.getLocation()).equals(
							Methods.whichArray(t))
							|| Methods.whichArray(t).equals(null)) {
						if (Variables.trapLocation.size() > 0) {
							Variables.trapLocation.remove(t);
						}
					}
			}
			/* System.out.println(trap.length); */
			if ((Inventory.getCount(Ids.rope_ID) == 0
					&& Inventory.getCount(Ids.net_ID) == 0 || trap.length == 0)
					&& Players.getLocal().isIdle()) {
				Methods.walkToCenter();
			}
		} else {
			if (Variables.familiarIsFull)
				if (summoningMenuClose.interact("Close")) {
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& !summoningMenuClose.isOnScreen()) {
						if (summoningMenuClose.isOnScreen()) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}
		}
	}

	@Override
	public boolean validate() {
		return Methods.playerAtTrappingSpot() && !Inventory.isFull();
	}
}