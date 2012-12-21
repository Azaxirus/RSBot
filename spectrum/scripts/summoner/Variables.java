package spectrum.scripts.summoner;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

import spectrum.tools.structure.Strategy;

public class Variables {
	public static Tree jobs = null;
	public static Node[] n = new Node[] {};
	public static List<Strategy> strategyList = new ArrayList<Strategy>();
	public static final Timer runTime = new Timer(0);
	public static Color col;
	public static Image banner;
	public static Image cursor;
	public static Image cursorResting;
	public static Image cursorClicked;
	public static String pouchName;
	public static String error;
	public static String charmName = "";
	public static long startTime = System.currentTimeMillis();
	public static long millis = 0;
	public static long hours = 0;
	public static long minutes = 0;
	public static long seconds = 0;
	public static long lastPressed;
	public static int startExp;
	public static int currentExp;
	public static int startLvl;
	public static int currentLvl;
	public static int loop = 0;
	public static int pouchId = 0;
	public static int scrollId = 0;
	public static int pouchCharm = 0;
	public static int pouchSecondary = 0;
	public static boolean drawPaintHigh = true;
	public static boolean drawPaintLow = false;
	public static boolean startScript = false;
	public static boolean guiWait = true;
	public static boolean doneWithObelisk = false;
	public static boolean walkToObelisk = false;
	public static boolean walkToBank = false;
	public static boolean setupInventory = true;
	public static boolean leftClickOption = false;
	public static boolean checkForRing = false;
	public static int[] itemsToWithdraw = {};

	public static int[] itemsToWithdrawAmounts = { 0, 0, 0, 0 };
	public final static Area dungArea = new Area(new Tile[] {
			new Tile(3434, 3688, 0), new Tile(3464, 3684, 0),
			new Tile(3463, 3714, 0), new Tile(3453, 3726, 0),
			new Tile(3441, 3725, 0), new Tile(3433, 3716, 0) });
	public final static Area pitsArea = new Area(new Tile[] {
			new Tile(4600, 5140, 0), new Tile(4630, 5140, 0),
			new Tile(4630, 5120, 0), new Tile(4600, 5120, 0)

	});
	public final static Area obeliskArea = new Area(new Tile[] {
			new Tile(2329, 10017, 0), new Tile(2337, 10017, 0),
			new Tile(2337, 10007, 0), new Tile(2329, 10007, 0) });
	public final static Area doorArea = new Area(new Tile[] {
			new Tile(2300, 3660, 0), new Tile(2375, 3660, 0),
			new Tile(2375, 3600, 0), new Tile(2300, 3600, 0) });
	public final static Tile[] walkToBanker = { new Tile(3448, 3700, 0),
			new Tile(3450, 3705, 0), new Tile(3449, 3710, 0),
			new Tile(3450, 3715, 0), new Tile(3449, 3717, 0) };
}