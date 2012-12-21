package spectrum.scripts.barrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.bot.Context;

import spectrum.tools.structure.Strategies;

@Manifest(authors = { "Spectrum / 537065637472756d" }, name = "sBarrows", description = "v1.0 | Performs the Barrows Minigame", website = "", version = 1.0, hidden = true)
public class Barrows extends ActiveScript {
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
