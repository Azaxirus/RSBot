package spectrum.scripts.lodestones;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.bot.Context;

import spectrum.tools.structure.Strategies;

@Manifest(authors = { "jTippy" }, name = "jLodestones", description = "v4.03 | Activates Lodestones.", website = "http://www.powerbot.org/community/topic/777775-jlodestones-completes-all-your-lodestone-needs/", version = 4.03)
public class Lodestones extends ActiveScript {
	@Override
	public int loop() {
		Strategies.loop(this);
		return 0;
	}

	@Override
	public void onStart() {
		Mouse.setSpeed(Speed.FAST);
	}

	@Override
	public void onStop() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd [HH-mm]");
		Date date = new Date();
		Context.saveScreenCapture(dateFormat.format(date));
	}
}
