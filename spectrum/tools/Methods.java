package spectrum.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.graphics.CapturedModel;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import spectrum.scripts.lizards.Variables;
import spectrum.tools.interactive.Actionbar;
import spectrum.tools.map.Areas;
import spectrum.tools.map.Ids;

public class Methods implements PaintListener {

	static WidgetChild summoningMenuClose = Widgets.get(671, 13);

	public static Tile[] checkIt() {
		for (Tile[] allTrap : Variables.allTraps) {
			for (Tile v : Variables.trapLocation) {
				if (v != null) {
					if (Methods.contains(allTrap, v))
						return allTrap;
				}
			}
		}
		return null;
	}

	public static void checkSettings() {
		turnOnRun();
		if (Actionbar.isOpen()) {
			Actionbar.close();
		}
	}

	public static boolean contains(Tile[] a, Tile b) {
		for (Tile t : a) {
			if (t.equals(b))
				return true;
		}
		return false;
	}

	public static void drawTile(Graphics2D g, CapturedModel model, Color white) {
		for (Polygon poly : model.getBounds()) {
			boolean drawThisOne = true;
			for (int i = 0; i < poly.npoints; i++) {
				Point p = new Point(poly.xpoints[i], poly.ypoints[i]);
				if (!Calculations.isOnScreen(p)) {
					drawThisOne = false;
				}
			}
			if (drawThisOne) {
				Color col2 = new Color(white.getRed(), white.getGreen(),
						white.getBlue(), 80);
				g.setColor(col2);
				g.fillPolygon(poly);
				g.setColor(white);
				g.drawPolygon(poly);
			}
		}
	}

	public static boolean isTrapNearby(Tile t) {
		for (Tile[] a : Variables.allTrapAreas) {
			for (Tile b : a) {
				if (b != null) {
					if (b.equals(Players.getLocal().getLocation()))
						return true;
				}
			}
		}
		return false;
	}

	public static void pickUp() {
		GroundItem[] g = GroundItems.getLoaded(new Filter<GroundItem>() {
			@Override
			public boolean accept(final GroundItem l) {
				for (int id : Ids.equipment) {
					if (l.getId() == id && Variables.trappingSpot.contains(l))
						return true;
				}
				return false;

			}
		});
		if (Inventory.getCount(Ids.net_ID) + Variables.trapLocation.size() <= Variables.maxTraps
				|| Inventory.getCount(Ids.rope_ID)
						+ Variables.trapLocation.size() <= Variables.maxTraps) {
			for (GroundItem i : g) {
				if (i != null) {
					if (!i.isOnScreen()) {
						Camera.turnTo(i);
					}
					Variables.activity = "Retrieving equipment..";
					if (!summoningMenuClose.isOnScreen()) {
						if (i.interact("Take")) {
							Task.sleep(Random.nextInt(250, 750));
						}
						while (Players.getLocal().isMoving()) {
							Task.sleep(15);
						}
					} else if (summoningMenuClose != null
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
				}
			}
		}

	}

	public static boolean playerAtBank() {
		return Areas.bank.contains(Players.getLocal().getLocation());
	}

	public static boolean playerAtBar() {
		return Areas.barArea.contains(Players.getLocal().getLocation());
	}

	public static boolean playerAtLizards() {
		return Areas.lizards.contains(Players.getLocal().getLocation());
	}

	public static boolean playerAtObelisk() {
		return Areas.obelisk.contains(Players.getLocal().getLocation());
	}

	public static boolean playerAtTrappingSpot() {
		return Variables.trappingSpot
				.contains(Players.getLocal().getLocation());
	}

	public static boolean playerInsideBottingArea() {
		return Areas.bottingArea.contains(Players.getLocal().getLocation());
	}

	public static void releaseTrap() {
		GroundItem[] g = GroundItems.getLoaded(new Filter<GroundItem>() {
			@Override
			public boolean accept(final GroundItem l) {
				for (int id : Ids.equipment) {
					if (l.getId() == id && Variables.trappingSpot.contains(l))
						return true;
				}
				return false;

			}
		});
		if (g.length == 0) {
			synchronized (Variables.trapLocation) {
				for (Tile t : Variables.trapLocation.toArray(new Tile[0])) {
					if (Variables.trapLocation.size() > 0) {
						ArrayList<Tile> tiles = trapTiles(t, 1);
						SceneObject[] nets = SceneEntities
								.getLoaded(new Filter<SceneObject>() {
									@Override
									public boolean accept(final SceneObject l) {
										for (int id : Ids.trappedNet_ID) {
											if (l.getId() == id
													&& Variables.trappingSpot
															.contains(l))
												return true;
										}
										return false;

									}
								});
						for (SceneObject s : nets)
							if (s != null) {
								if (!s.isOnScreen()) {
									Camera.turnTo(s);
								}
								if (tiles.contains(s.getLocation())) {
									if (Inventory.getCount() == 27) {
										if (Inventory.getItem(Ids.LIZARD_ID)
												.getWidgetChild().hover()) {
											if (Menu.select("Release")) {
												final Timer timer = new Timer(
														1500);
												while (timer.isRunning()
														&& Inventory.getCount() == 27) {
													if (Inventory.getCount() < 27) {
														break;
													} else {
														Task.sleep(15);
													}
												}
											}
										}
									}
									if (Inventory.getCount() == 26) {
										if (Inventory.getItem(Ids.LIZARD_ID)
												.getWidgetChild().hover()) {
											if (Menu.select("Release")) {
												final Timer timer = new Timer(
														1500);
												while (timer.isRunning()
														&& Inventory.getCount() == 26) {
													if (Inventory.getCount() < 26) {
														break;
													} else {
														Task.sleep(15);
													}
												}
											}
										}
									}

									if (!summoningMenuClose.isOnScreen()) {
										if (s.interact("Check", "Net trap")) {
											Variables.activity = "Releasing Trap..";
											final Timer timer = new Timer(1500);
											while (timer.isRunning()
													&& Players.getLocal()
															.getAnimation() != 5207) {
												if (Players.getLocal()
														.isMoving()) {
													timer.reset();
												} else if (Players.getLocal()
														.getAnimation() == 5207) {
													Task.sleep(1250, 1750);
													break;
												} else {
													Task.sleep(15);
												}
											}
										}
									} else if (summoningMenuClose != null
											&& summoningMenuClose
													.interact("Close")) {
										final Timer timer = new Timer(800);
										while (timer.isRunning()
												&& summoningMenuClose
														.isOnScreen()) {
											if (!summoningMenuClose
													.isOnScreen()) {
												break;
											} else {
												Task.sleep(15);
											}
										}
									}
								}
							}
					}
				}
			}
		}
	}

	public static void setTrap(SceneObject[] s) {
		GroundItem[] g = GroundItems.getLoaded(new Filter<GroundItem>() {
			@Override
			public boolean accept(final GroundItem l) {
				for (int id : Ids.equipment) {
					if (l.getId() == id && Variables.trappingSpot.contains(l))
						return true;
				}
				return false;

			}
		});
		if (g.length == 0) {
			for (SceneObject t : s) {
				if (Inventory.getCount(Ids.net_ID) > 0
						&& Inventory.getCount(Ids.rope_ID) > 0
						&& Variables.trapLocation.size() * 3
								+ Inventory.getCount() < 28) {
					if (t != null && !Players.getLocal().isMoving()) {
						if (!t.isOnScreen()) {
							Camera.turnTo(t);
						}
						if (Variables.trappingSpot.contains(t.getLocation())) {
							if (!summoningMenuClose.isOnScreen()) {
								if (Players.getLocal().getAnimation() == -1
										&& t.interact("Set-trap", "Young tree")) {
									Variables.activity = "Setting trap..";
									final Timer timer = new Timer(1500);
									while (timer.isRunning()
											&& Players.getLocal()
													.getAnimation() != 5215) {
										if (Players.getLocal().isMoving()) {
											timer.reset();
										} else if (Players.getLocal()
												.getAnimation() == 5215) {
											Task.sleep(1250, 1750);
											break;
										} else {
											Task.sleep(15);
										}

									}
								}
							} else if (summoningMenuClose != null
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
						}
					}
				}
			}
		}
	}

	public static void setValues() {
		Variables.startExp = Skills.getExperiences()[Skills.HUNTER];
		Variables.startLvl = Skills.getLevels()[Skills.HUNTER];
		System.out.println("Hunter Lvl:" + Variables.startLvl);
		Variables.startLizards = 0;
		Variables.lizardsGained = 0;
		Variables.lizardsHour = 0;
		Variables.lizardsTotal = 0;
	}

	public static ArrayList<Tile> trapTiles(Tile loc, int i) {
		ArrayList<Tile> trapTiles = new ArrayList<Tile>();
		for (int x = -i; x <= i; x++) {
			for (int y = -i; y <= i; y++) {
				Tile add = new Tile(loc.getX() + x, loc.getY() + y, 0);
				if (!trapTiles.contains(add)) {
					trapTiles.add(add);
				}
			}
		}
		return trapTiles;
	}

	public static Tile trapTilesContainsTrapArrayTile() {
		for (Tile[] a : Variables.allTrapAreas) {
			for (Tile b : a) {
				if (b.equals(Players.getLocal().getLocation()))
					return b;
			}
		}

		return null;
	}

	public static void turnOnRun() {
		if (Settings.get(463) == 0) {
			System.out.println("Turning on run to improve effiency");
			if (Widgets.get(750, 2).click(true)) {
				final Timer timer = new Timer(800);
				while (timer.isRunning() && Settings.get(463) == 0) {
					if (Settings.get(463) != 0) {
						break;
					} else {
						Task.sleep(15);
					}
				}
			}
		}
	}

	public static void walkToCenter() {
		if (!Methods.contains(Variables.centralArea, Players.getLocal()
				.getLocation())) {
			if (Variables.centralArea[Random.nextInt(0,
					Variables.centralArea.length)].isOnScreen()
					&& !summoningMenuClose.isOnScreen()) {
				if (Variables.centralArea[Random.nextInt(0,
						Variables.centralArea.length)].interact("Walk here")) {
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& !Methods.contains(Variables.centralArea, Players
									.getLocal().getLocation())) {
						if (Methods.contains(Variables.centralArea, Players
								.getLocal().getLocation())) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}
			} else {
				if (Variables.centralArea[Random.nextInt(0,
						Variables.centralArea.length)].clickOnMap()) {
					final Timer timer = new Timer(800);
					while (timer.isRunning()
							&& !Methods.contains(Variables.centralArea, Players
									.getLocal().getLocation())) {
						if (Methods.contains(Variables.centralArea, Players
								.getLocal().getLocation())) {
							break;
						} else {
							Task.sleep(15);
						}
					}
				}
			}
		}

	}

	public static Tile[] whichArray(Tile t) {
		for (Tile[] a : Variables.allTrapAreas) {
			for (Tile b : a) {
				if (a != null && b != null && t != null) {
					if (b.equals(t))
						return a;
				}
			}
		}
		return new Tile[] {};
	}

	@Override
	public void onRepaint(Graphics g) {
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	}
}
