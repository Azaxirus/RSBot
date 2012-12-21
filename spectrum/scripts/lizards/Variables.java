package spectrum.scripts.lizards;

import java.awt.image.BufferedImage;
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
	public static Timer runTime = new Timer(0);
	public static String error;
	public static long startTime = System.currentTimeMillis();
	public static long millis = 0;
	public static long hours = 0;
	public static long minutes = 0;
	public static long seconds = 0;
	public static int startExp;
	public static int currentExp;
	public static int startLvl;
	public static int currentLvl;
	public static int loop = 0;
	public static boolean stratsProvided = true;
	public static boolean startScript = false;
	public static boolean guiWait = true;
	public static boolean trapping = false;
	public static boolean walking = false;
	public static boolean banking = false;
	public static boolean walkingToBank = false;
	public static boolean teleportToBankRunes = false;
	public static boolean teleportToBankTabs = false;
	public static boolean[] bankOptions = { walkingToBank, teleportToBankRunes };
	public static boolean setReleasePickup = false;
	public static boolean releaseSetPickup = false;
	public static boolean setPickupRelease = false;
	public static boolean pickupSetRelease = false;
	public static boolean pickupReleaseSet = false;
	public static boolean[] options = { setReleasePickup, releaseSetPickup,
			setPickupRelease, pickupSetRelease, pickupReleaseSet };
	public static boolean familiarTimer = false;
	public static boolean usingFamiliar = false;
	public static boolean familiarIsFull = false;
	public static boolean deathWalk = false;
	public static int startLizards = 0;
	public static int lizardsGained = 0;
	public static int lizardsNew = 0;
	public static int expGained = 0;
	public static int expPerHour = 0;
	public static int lizardsHour = 0;
	public static int lizardsTotal = 0;
	public static int lizardsPrice = 0;
	public static int lvlsGained = 0;
	public static int curRope = 0;
	public static int curNet = 0;
	public static int maxTraps = 0;
	public static int familiarId;
	public static String equipmentCount = "";
	public static String bankingMethod = "";
	public static String familiarSelected = "";
	public static String trapMethod = "";
	public static String activity;
	public static String trappedLizards;
	public static int[] itemsToWithdraw = { 565, 563 };
	// GUI IDS
	public static int[] TRAP_ID;
	public static Tile[] swampPath = new Tile[500];
	public static ArrayList<Tile> trapLocation = new ArrayList<Tile>();
	public static final Tile[][] allTraps = { new Tile[] {} };
	public static Tile[] centralArea;
	public static Tile[][] allTrapAreas = { new Tile[] {}, new Tile[] {},
			new Tile[] {}, new Tile[] {} };

	public static Area trappingSpot;
	public static BufferedImage banner;
	public static BufferedImage cursorResting;
	public static BufferedImage cursorClicked;
	public static BufferedImage cursor;
	public static boolean drawPaintHigh = true;
	public static boolean drawPaintLow = false;

}
