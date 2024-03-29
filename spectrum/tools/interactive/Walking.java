package spectrum.tools.interactive;

import java.awt.Point;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.util.internal.Multipliers;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.ViewportEntity;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;
import org.powerbot.game.client.RSGroundData;
import org.powerbot.game.client.RSInfo;

/**
 * A utility for the manipulation of information required for walking.
 * 
 * @author Timer
 */
public class Walking {
	private static final int WIDGET = 750;
	private static final int WIDGET_RUN = 2;
	private static final int WIDGET_RUN_ENERGY = 6;

	public static LocalPath findPath(final Locatable mobile) {
		return new LocalPath(mobile.getLocation());
	}

	public static Tile getClosestOnMap(Tile tile) {
		if (tile.isOnMap())
			return tile;

		final Tile location = Players.getLocal().getLocation();
		tile = tile.derive(-location.getX(), -location.getY());
		final double angle = Math.atan2(tile.getY(), tile.getX());
		return new Tile(location.getX() + (int) (16d * Math.cos(angle)),
				location.getY() + (int) (16d * Math.sin(angle)),
				tile.getPlane());
	}

	/**
	 * @param plane
	 *            The plane of which to provide the collision flags for.
	 * @return The collision flags of the current map block.
	 */
	public static int[][] getCollisionFlags(final int plane) {
		final RSInfo info = (RSInfo) Context.client().getRSGroundInfo();
		final RSGroundData data = ((RSGroundData[]) info.getGroundData())[plane];
		return (int[][]) data.getBlocks();
	}

	/**
	 * @param plane
	 *            The plane of which to determine the collision offset for.
	 * @return The <code>Tile</code> of the offset location (different than map
	 *         base!).
	 */
	public static Tile getCollisionOffset(final int plane) {
		final Multipliers multipliers = Context.multipliers();
		final RSInfo info = (RSInfo) Context.client().getRSGroundInfo();
		final RSGroundData data = ((RSGroundData[]) info.getGroundData())[plane];
		return new Tile(data.getX() * multipliers.GROUNDDATA_X, data.getY()
				* multipliers.GROUNDDATA_Y, plane);
	}

	public static Tile getDestination() {
		final Client client = Context.client();
		final Multipliers multipliers = Context.multipliers();
		final int lx = client.getDestX() * multipliers.GLOBAL_DESTX / 4;
		final int ly = client.getDestY() * multipliers.GLOBAL_DESTY / 4;
		if (lx == -1 || ly == -1)
			return new Tile(-1, -1, -1);
		return new Tile(Game.getBaseX() + lx, Game.getBaseY() + ly,
				Game.getPlane());
	}

	public static int getEnergy() {
		try {
			return Integer.parseInt(Widgets.get(WIDGET, WIDGET_RUN_ENERGY)
					.getText());
		} catch (final NumberFormatException ignored) {
			return -1;
		}
	}

	public static boolean isRunEnabled() {
		return Settings.get(463) == 1;
	}

	public static TilePath newTilePath(final Tile[] path) {
		return new TilePath(path);
	}

	public static void setRun(final boolean enabled) {
		if (isRunEnabled() != enabled
				&& Widgets.get(WIDGET, WIDGET_RUN).click(true)) {
			final Timer t = new Timer(1800);
			while (t.isRunning() && isRunEnabled() != enabled) {
				Task.sleep(5);
			}
		}
	}

	/**
	 * Clicks a tile on the minimap.
	 * 
	 * @param mobile
	 *            The mobile to click (global).
	 * @return <tt>true</tt> if the mobile was clicked; otherwise <tt>false</tt>
	 *         .
	 */
	public static boolean walk(final Locatable mobile) {
		Tile stepDirection = mobile.getLocation();
		if (!stepDirection.isOnMap()) {
			stepDirection = getClosestOnMap(stepDirection);
		}
		final Tile tile = stepDirection;
		return Mouse.apply(new ViewportEntity() {
			@Override
			public boolean contains(final Point point) {
				return getCentralPoint().distance(point) <= 2;
			}

			@Override
			public Point getCentralPoint() {
				return Calculations.worldToMap(tile.getX(), tile.getY());
			}

			@Override
			public Point getNextViewportPoint() {
				return getCentralPoint();
			}

			@Override
			public boolean validate() {
				return tile.isOnMap();
			}
		}, new Filter<Point>() {
			@Override
			public boolean accept(final Point point) {
				Mouse.click(true);
				return true;
			}
		});
	}
}