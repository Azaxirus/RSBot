package spectrum.scripts.lizards.nodes;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.Methods;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

public class Trap implements Strategy {
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
		Methods.checkSettings();
		if (Variables.options[0]) {
			Methods.setTrap(trap);
			Methods.releaseTrap();
			Methods.pickUp();
		} else if (Variables.options[1]) {
			Methods.releaseTrap();
			Methods.setTrap(trap);
			Methods.pickUp();
		} else if (Variables.options[2]) {
			Methods.setTrap(trap);
			Methods.pickUp();
			Methods.releaseTrap();
		} else if (Variables.options[3]) {
			Methods.pickUp();
			Methods.setTrap(trap);
			Methods.releaseTrap();
		} else if (Variables.options[4]) {
			Methods.pickUp();
			Methods.releaseTrap();
			Methods.setTrap(trap);
		}
		for (Tile t : Variables.trapLocation.toArray(new Tile[0])) {
			SceneObject[] nets = SceneEntities
					.getLoaded(new Filter<SceneObject>() {
						@Override
						public boolean accept(final SceneObject l) {
							for (int id : Ids.trap_ID) {
								if (l.getId() == id
										&& Variables.trappingSpot.contains(l))
									return true;
							}
							return false;

						}
					});
			if (t.equals(new Tile(3550, 3452, 0))) {
				if (Variables.trapLocation.size() > 0) {
					Variables.trapLocation.remove(t);
				}
			} else {
				for (SceneObject s : nets)
					if (Methods.whichArray(s.getLocation()).equals(
							Methods.whichArray(t))) {
						if (Variables.trapLocation.size() > 0) {
							Variables.trapLocation.remove(t);
						}
					}
			}
		}
		/* System.out.println(trap.length); */
		if ((Inventory.getCount(Ids.rope_ID) == 0
				&& Inventory.getCount(Ids.net_ID) == 0 || trap.length == 0)
				&& Players.getLocal().isIdle()) {
			Methods.walkToCenter();
		}
	}

	@Override
	public boolean validate() {
		return Methods.playerAtTrappingSpot() && !Inventory.isFull();
	}
}