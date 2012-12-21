package spectrum.scripts.summoner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.Context;

import spectrum.scripts.summoner.nodes.SetLeftClickOption;
import spectrum.tools.interactive.Actionbar;
import spectrum.tools.interactive.Walking;
import spectrum.tools.structure.Strategies;

@Manifest(authors = { "Spectrum / 537065637472756d" }, name = "sSummoner", description = "v2.03 EoC BETA | Creates Pouches for Summoning Experience.", website = "http://www.powerbot.org/community/topic/813794-jsummoner/", version = 2.03)
public class Summoner extends ActiveScript implements PaintListener,
		MessageListener, MouseListener {
	@Override
	public int loop() {
		if (Actionbar.isOpen())
			Actionbar.close();
		Strategies.loop(this);
		return 200;
	}

	@Override
	public void onStart() {
		try {
			Variables.banner = ImageIO.read(new URL(
					"http://i48.tinypic.com/idrwyg.jpg"));
			Variables.cursorResting = ImageIO.read(new URL(
					"http://i49.tinypic.com/20f4uo8.jpg"));
			Variables.cursorClicked = ImageIO.read(new URL(
					"http://i48.tinypic.com/cuz5d.jpg"));
			Variables.cursor = Variables.cursorResting;
		} catch (IOException e) {
			e.printStackTrace();
		}
		Strategies.add(new SetLeftClickOption());
		startGui();
		Mouse.setSpeed(Speed.FAST);
	}

	@Override
	public void onStop() {
		if (Variables.error != null)
			System.out.println("Error: " + Variables.error);
		System.out.println("Total run time: "
				+ Variables.runTime.toElapsedString());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd [HH-mm]");
		Date date = new Date();
		Context.saveScreenCapture(dateFormat.format(date));

	}

	public void startGui() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Gui jSummoner = new Gui();
				jSummoner.setVisible(true);
			}
		});
	}

	@Override
	public void messageReceived(MessageEvent e) {
		System.out.println("Message: " + e.getMessage() + " ID: " + e.getId()
				+ " Sender: " + e.getSender());
		if (e.getMessage().toString()
				.contains("You do not have the necessary materials")) {
			Variables.error = "You ran into material issues at the obelisk.";
			Context.get().getScriptHandler().shutdown();
		}
		if (e.getMessage().toString()
				.contains("Your familiar is too big to fit here")) {
			if (Calculations.distanceTo(new Tile(4614, 5130, 0)) < 20)
				Walking.walk(new Tile(4614, 5130, 0));
		}
	}

	private class MousePathPoint extends Point { // All credits to Enfilade

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private long finishTime;

		public MousePathPoint(int x, int y, int lastingTime) {
			super(x, y);
			finishTime = System.currentTimeMillis() + lastingTime;
		}

		public boolean isUp() {
			return System.currentTimeMillis() > finishTime;
		}
	}

	private final LinkedList<MousePathPoint> mousePath = new LinkedList<MousePathPoint>();

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (Game.isLoggedIn() && Players.getLocal().isOnScreen()) {
			if (Variables.drawPaintHigh) {
				g.drawImage(Variables.banner, 0, 382, null);
				g.setColor(Color.white);
				StringBuilder paint = new StringBuilder(String.format("%s\n",
						Variables.runTime.toElapsedString()));
				g.drawString(paint.toString(), 151, 451);
				Variables.currentLvl = Skills.getRealLevel(Skills.SUMMONING);
				Variables.currentExp = Skills.getExperience(Skills.SUMMONING);
				int expGained = Variables.currentExp - Variables.startExp;
				int expPerHour = (int) Math
						.floor((expGained * 3600000D / Variables.runTime
								.getElapsed()));
				g.drawString("" + Variables.currentLvl, 186, 488);
				g.drawString("" + expGained, 383, 486);
				g.drawString("" + expPerHour, 375, 451);
				Point p = Mouse.getLocation();
				g.drawImage(Variables.cursor, (int) p.getX() - 2,
						(int) p.getY(), null);
			} else if (Variables.drawPaintLow) {
				Point p = Mouse.getLocation();
				g.drawLine(p.x - 3, p.y + 3, p.x + 3, p.y - 3);
				g.drawLine(p.x + 3, p.y + 3, p.x - 3, p.y - 3);
				Variables.currentLvl = Skills.getRealLevel(Skills.SUMMONING);
				Variables.currentExp = Skills.getExperience(Skills.SUMMONING);
				int expGained = Variables.currentExp - Variables.startExp;
				int expPerHour = (int) Math
						.floor((expGained * 3600000D / Variables.runTime
								.getElapsed()));
				g.setColor(Color.black);
				StringBuilder paint2 = new StringBuilder(String.format("%s\n",
						Variables.runTime.toElapsedString()));
				g.drawString("Time run: " + paint2.toString(), 11, 251);
				g.drawString("Summoning level: " + Variables.currentLvl, 11,
						266);
				g.drawString("Exp Gained: " + expGained, 11, 281);
				g.drawString("Exp/hour: " + expPerHour, 11, 296);
				g.setColor(Color.cyan);
				StringBuilder paint = new StringBuilder(String.format("%s\n",
						Variables.runTime.toElapsedString()));
				g.drawString("Time run: " + paint.toString(), 10, 250);
				g.drawString("Summoning level: " + Variables.currentLvl, 10,
						265);
				g.drawString("Exp Gained: " + expGained, 10, 280);
				g.drawString("Exp/hour: " + expPerHour, 10, 295);
				g.setColor(Color.black);
				String s = "Press middle mouse button(scroll wheel) for high def paint.";
				g.drawString(s, 11, 311);
				g.setColor(Color.red);
				String s1 = "Press middle mouse button(scroll wheel) for high def paint.";
				g.drawString(s1, 10, 310);

			}
			while (!mousePath.isEmpty() && mousePath.peek().isUp())
				mousePath.remove();
			Point clientCursor = Mouse.getLocation();
			MousePathPoint mpp = new MousePathPoint(clientCursor.x,
					clientCursor.y, 1000); // 1000 = lasting time/MS
			if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
				mousePath.add(mpp);
			MousePathPoint lastPoint = null;
			for (MousePathPoint a : mousePath) {
				if (lastPoint != null) {
					g.setColor(Color.white);
					g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
				}
				lastPoint = a;
			}
			/* drawMouse(g1); */
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Variables.cursor = Variables.cursorClicked;
		if (e.getButton() == 2)
			if (Variables.drawPaintHigh) {
				Variables.drawPaintHigh = false;
				Variables.drawPaintLow = true;
			} else {
				Variables.drawPaintHigh = true;
				Variables.drawPaintLow = false;
			}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Variables.cursor = Variables.cursorResting;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
