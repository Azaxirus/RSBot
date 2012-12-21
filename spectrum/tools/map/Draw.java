package spectrum.tools.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.wrappers.Tile;

public class Draw {

	public static void drawTile(Graphics2D g, Tile tile, Color col) {
		for (Polygon poly : tile.getBounds()) {
			boolean drawThisOne = true;
			for (int i = 0; i < poly.npoints; i++) {
				Point p = new Point(poly.xpoints[i], poly.ypoints[i]);
				if (!Calculations.isOnScreen(p)) {
					drawThisOne = false;
				}
			}
			if (drawThisOne) {
				Color col2 = new Color(col.getRed(), col.getGreen(),
						col.getBlue(), 80);
				g.setColor(col2);
				g.fillPolygon(poly);
				g.setColor(col);
				g.drawPolygon(poly);
			}
		}
	}

}
