package spectrum.scripts.lizards.nodes;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.structure.Strategies;
import spectrum.tools.structure.Strategy;

public class Strats implements Strategy {
	int loop = 0;
	int loop1 = 0;

	@Override
	public void execute() {
		if (Game.isLoggedIn()) {
			System.out.println("Providing strategies");
			if (Players.getLocal() != null) {
				Camera.setPitch(Random.nextInt(30, 50));
			}
			Strategies.remove(new Strats());
			Strategy[] option1 = { new RenewFamiliar(), new RenewPoints(),
					new Trap(), new ReturnToTrap(), new Walk(), new Banking() };
			Strategy[] option2 = { new RenewFamiliar(), new RenewPoints(),
					new Trap(), new ReturnToTrap(), new Teleport(),
					new BankingTeleport(), new WalkTo() };

			Strategy[][] options = { option1, option2 };
			for (int i = 0; i < Variables.bankOptions.length; i++) {
				if (Variables.bankOptions[i]) {
					Strategies.add(options[i]);
				}
			}
			Variables.stratsProvided = false;
		}
	}

	@Override
	public boolean validate() {
		return Variables.stratsProvided;
	}
}