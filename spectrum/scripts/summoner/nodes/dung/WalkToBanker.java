package spectrum.scripts.summoner.nodes.dung;

import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.NPC;

import spectrum.scripts.summoner.Variables;
import spectrum.tools.interactive.Walking;
import spectrum.tools.map.Ids;
import spectrum.tools.structure.Strategy;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:53 PM
 */

public class WalkToBanker implements Strategy {

	@Override
	public void execute() {
		try {
			System.out.println("WalkToBanker.java");
			NPC dungBank = NPCs.getNearest(Ids.BANKER_DUNG);
			Walking.newTilePath(Variables.walkToBanker).traverse();
			if (dungBank != null) {
				Camera.turnTo(dungBank);
				if (!dungBank.isOnScreen()) {
					Camera.setPitch(Random.nextInt(5, 10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		NPC dungBank = NPCs.getNearest(Ids.BANKER_DUNG);
		NPC dungTrader = NPCs.getNearest(9711);
		return (dungBank == null || dungBank != null && !dungBank.isOnScreen() || dungTrader != null)
				&& (Players.getLocal() != null
						&& Variables.dungArea.contains(Players.getLocal()) || Players
						.getLocal().getAnimation() == 13654);
	}
}