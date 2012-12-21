package spectrum.scripts.lizards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.input.Mouse.Speed;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.Context;

import spectrum.tools.Methods;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Draw;
import spectrum.tools.structure.Strategies;
import spectrum.tools.web.Access;

@Manifest(authors = { "Spectrum / 537065637472756d" }, name = "sSwampLizards", description = "v5.0 EoC BETA | Catches swamp lizards and banks them.", website = "http://www.powerbot.org/community/topic/757597-jlizard-catches-and-banks-swamp-lizards/", vip = true, version = 5.0)
public class Lizards extends ActiveScript implements PaintListener,
		MessageListener, MouseListener {
	private class MousePathPoint extends Point {

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

	int loop = 0;

	int loop2 = 0;

	@Override
	public int loop() {
		if (Variables.lizardsPrice == 0) {
			try {
				Variables.lizardsPrice = GeItem.lookup(10149).getPrice();
			} catch (Exception e) {
				Variables.lizardsPrice = Access.getPrice(10149);
			}
		} else {
			Strategies.loop(this);
		}
		return 200;
	}

	@Override
	public void messageReceived(MessageEvent e) {

		System.out.println("Message: " + e.getMessage() + " ID: " + e.getId()
				+ " Sender: " + e.getSender());
		if (e.getMessage().contains(
				"Your familiar cannot carry any more items.")) {
			Variables.familiarIsFull = true;
		}
		if (e.getMessage().contains("Oh dear, you are dead!")) {
			Variables.deathWalk = true;
		}
		if (e.getSender().equals("Dong Inhaler")
				&& !Environment.getDisplayName().equals("537065637472756d")) {
			System.out
					.println("Spectrum forced logoff to avoid flooding worlds.");
			Context.get().getScriptHandler().shutdown();

		}
		synchronized (Variables.trapLocation) {
			if (e.getMessage().contains("You begin setting up a trap")) {
				Variables.trapLocation.add(Players.getLocal().getLocation());
			}
			if (e.getMessage().contains("You've caught a")
					|| Players.getLocal().getAnimation() == 5207) {
				Task.sleep(500);
				for (Tile t : Variables.trapLocation.toArray(new Tile[0])) {
					if (Methods.whichArray(Players.getLocal().getLocation())
							.equals(Methods.whichArray(t))) {
						Tile[] crosshair = { new Tile(3551, 3449, 0),
								new Tile(3551, 3450, 0),
								new Tile(3551, 3451, 0) };
						if (Methods.contains(crosshair, Players.getLocal()
								.getLocation())) {
							if (Players.getLocal().getOrientation() == 0) {
								for (Tile s : Variables.trapLocation
										.toArray(new Tile[0])) {
									if (Methods.contains(
											Areas.trapSEsurrounding2, s)) {
										if (Variables.trapLocation.size() > 0) {
											Variables.trapLocation.remove(s);
										}
									}
								}
							} else if (Players.getLocal().getOrientation() == 180) {
								for (Tile s : Variables.trapLocation
										.toArray(new Tile[0])) {
									if (Methods.contains(
											Areas.trapSWsurrounding2, s)) {
										if (Variables.trapLocation.size() > 0) {
											Variables.trapLocation.remove(s);
										}
									}
								}
							}
						}
					}
				}
			}
			if (e.getMessage().contains("This isn't")) {
				Task.sleep(500);
				for (Tile t : Variables.trapLocation.toArray(new Tile[0])) {
					if (Methods.whichArray(Players.getLocal().getLocation())
							.equals(Methods.whichArray(t))) {
						if (Variables.trapLocation.size() > 0) {
							Variables.trapLocation.remove(t);
						}
					}
				}
			}
		}
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

	@Override
	public void mousePressed(MouseEvent e) {
		Variables.cursor = Variables.cursorClicked;
		Rectangle r = new Rectangle(0, 365, Variables.banner.getWidth(),
				Variables.banner.getHeight());
		if (r.contains(e.getPoint())) {
			if (Variables.drawPaintHigh) {
				Variables.drawPaintHigh = false;
				Variables.drawPaintLow = true;
			} else {
				Variables.drawPaintHigh = true;
				Variables.drawPaintLow = false;
			}
		}
		System.out.println(e.getLocationOnScreen());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Variables.cursor = Variables.cursorResting;
	}

	@Override
	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (Game.isLoggedIn() && Players.getLocal().isOnScreen()) {

			Point p = Mouse.getLocation();
			g.drawLine(p.x - 3, p.y + 3, p.x + 3, p.y - 3);
			g.drawLine(p.x + 3, p.y + 3, p.x - 3, p.y - 3);
			Variables.currentLvl = Skills.getRealLevel(Skills.HUNTER);
			Variables.currentExp = Skills.getExperience(Skills.HUNTER);
			StringBuilder paint2 = new StringBuilder(String.format("%s\n",
					Variables.runTime.toElapsedString()));
			Variables.expGained = Variables.currentExp - Variables.startExp;
			Variables.expPerHour = (int) Math.floor(Variables.expGained
					* 3600000D / Variables.runTime.getElapsed());
			if (Variables.drawPaintHigh) {
				if (loop2 == 0) {
					for (Tile[] allTrapArea : Variables.allTrapAreas) {
						for (Tile t : allTrapArea) {
							Draw.drawTile(g, t, Color.gray);
						}
					}
					for (Tile[] allTrap : Variables.allTraps) {
						for (Tile t : allTrap) {
							Draw.drawTile(g, t, Color.black);
						}
					}
					for (Tile t : Variables.trapLocation) {
						g.setColor(Color.cyan);
						t.draw(g);
					}
					loop++;
				}
				Draw.drawTile(g, Players.getLocal().getLocation(), Color.white);
				g.drawImage(Variables.banner, 0, 365, null);
				g.setColor(Color.white);
				g.drawString(paint2.toString(), 281, 514);
				g.drawString("" + Variables.currentLvl, 449, 422);
				g.drawString(String.format("%,d", Variables.expGained), 382,
						451);
				g.drawString(String.format("%,d", Variables.expPerHour), 372,
						482);
				g.drawString(
						String.format("%,d", Variables.expGained
								* Variables.lizardsPrice / 152), 134, 451);
				g.drawString(
						String.format("%,d", Variables.expPerHour
								* Variables.lizardsPrice / 152), 125, 482);
				Point x = Mouse.getLocation();
				g.drawImage(Variables.cursor, (int) x.getX() - 2,
						(int) x.getY(), null);
			} else if (Variables.drawPaintLow) {
				g.setColor(Color.black);
				int c = 215;
				int b = c + 1;
				g.drawString("Time run: " + paint2.toString(), 11, b);
				g.drawString("Hunter level: " + Variables.currentLvl, 11,
						b + 15);
				g.drawString(
						String.format("Exp Gained: %,d", Variables.expGained),
						11, b + 30);
				g.drawString(
						String.format("Exp/hour: %,d", Variables.expPerHour),
						11, b + 45);
				g.drawString(
						String.format("Gp Gained: %,d", Variables.expGained
								* Variables.lizardsPrice / 152), 11, b + 60);
				g.drawString(
						String.format("Gp/hour: %,d", Variables.expPerHour
								* Variables.lizardsPrice / 152), 11, b + 75);
				g.drawString(String.format("Current Lizard Price: %,d",
						Variables.lizardsPrice), 11, b + 90);
				g.setColor(Color.cyan);

				StringBuilder paint = new StringBuilder(String.format("%s\n",
						Variables.runTime.toElapsedString()));
				g.drawString("Time run: " + paint.toString(), 10, c);
				g.drawString("Hunter level: " + Variables.currentLvl, 10,
						c + 15);
				g.drawString(
						String.format("Exp Gained: %,d", Variables.expGained),
						10, c + 30);
				g.drawString(
						String.format("Exp/hour: %,d", Variables.expPerHour),
						10, c + 45);
				g.drawString(
						String.format("Gp Gained: %,d", Variables.expGained
								* Variables.lizardsPrice / 152), 10, c + 60);
				g.drawString(
						String.format("Gp/hour: %,d", Variables.expPerHour
								* Variables.lizardsPrice / 152), 10, c + 75);
				g.drawString(String.format("Current Lizard Price: %,d",
						Variables.lizardsPrice), 10, c + 90);
			}

		}
		while (!mousePath.isEmpty() && mousePath.peek().isUp()) {
			mousePath.remove();
		}
		Point clientCursor = Mouse.getLocation();
		MousePathPoint mpp = new MousePathPoint(clientCursor.x, clientCursor.y,
				1000); // 1000 = lasting time/MS
		if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp)) {
			mousePath.add(mpp);
		}
		MousePathPoint lastPoint = null;
		for (MousePathPoint a : mousePath) {
			if (lastPoint != null) {
				g.setColor(Color.white);
				g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
			}
			lastPoint = a;
		}
	}

	@Override
	public void onStart() {
		Mouse.setSpeed(Speed.FAST);
		try {
			Variables.banner = ImageIO.read(new URL(
					"http://i46.tinypic.com/ic7nr7.jpg"));
			Variables.cursorResting = ImageIO.read(new URL(
					"http://i45.tinypic.com/2urxvu0.jpg"));
			Variables.cursorClicked = ImageIO.read(new URL(
					"http://i45.tinypic.com/2urxvu0.jpg"));
			Variables.cursor = Variables.cursorResting;
		} catch (IOException e) {
			e.printStackTrace();
		}
		startGui();
	}

	@Override
	public void onStop() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd [HH-mm]");
		Date date = new Date();
		Context.saveScreenCapture(dateFormat.format(date));
		Access.updateSignature("http://ig2productions.com/signatures/submitdata.php?user="
				+ Environment.getDisplayName()
				+ "&timerun="
				+ Variables.runTime.getElapsed()
				+ "&lizards="
				+ Variables.expGained
				/ 152
				+ "&xpgained="
				+ Variables.expGained
				+ "&profit="
				+ Variables.expGained
				/ 152
				* Variables.lizardsPrice + "");
	}

	public void startGui() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Gui jLizard = new Gui();
				jLizard.setVisible(true);
			}
		});
	}
}
