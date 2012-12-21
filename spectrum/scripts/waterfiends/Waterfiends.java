package spectrum.scripts.waterfiends;

import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.Context;

import spectrum.scripts.waterfiends.nodes.*;
import spectrum.tools.Log;
import spectrum.tools.map.Areas;
import spectrum.tools.structure.Strategies;

@Manifest(authors = { "Spectrum / 537065637472756d" }, name = "sWaterfiends", description = "v1.0 | Kills Waterfiends.", website = "", version = 1.0, hidden = true)
public class Waterfiends extends ActiveScript implements PaintListener {
	@Override
	public int loop() {
		Strategies.loop(this);
		return 0;
	}

	@Override
	public void onStart() {
		Log.log("info", "Welcome " + Environment.getDisplayName() + "!");
		Mouse.setSpeed(Speed.FAST);
		Strategies.add(new Banking(), new Walk(), new Eat(), new Loot(),
				new Fighting());
	}

	@Override
	public void onStop() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd [HH-mm]");
		Date date = new Date();
		Context.saveScreenCapture(dateFormat.format(date));
	}

	@Override
	public void onRepaint(Graphics g) {
		for (Tile t : Areas.ZANARIS_BANK.getTileArray()) {
			t.draw(g);
		}
	}
}
