package spectrum.tools.structure;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.core.script.job.Task;

import spectrum.tools.Log;

/**
 * Author: kyle Date: 23/09/2012 Time: 2:54 PM
 */

public class Strategies {

	public static List<Strategy> strategyList = new ArrayList<Strategy>();

	/**
	 * @param strategies
	 *            strategies to add to current list
	 */
	public static void add(Strategy... strategies) {
		for (Strategy strategy : strategies) {
			strategyList.add(strategy);
		}
	}

	/**
	 * @return returns list of strategies
	 */
	public static Strategy[] get() {
		return strategyList.toArray(new Strategy[strategyList.size()]);
	}

	public static void loop(Task t) {
		for (Strategy strategy : Strategies.get()) {
			if (t.isAlive() && strategy.validate()) {
				Log.log("notice", strategy.getClass().getName());
				strategy.execute();
			}
		}
	}

	/**
	 * @param strategies
	 *            strategies to remove from current list
	 */
	public static void remove(Strategy... strategies) {
		for (Strategy strategy : strategies) {
			strategyList.remove(strategy);
		}
	}
}