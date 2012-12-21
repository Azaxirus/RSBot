package spectrum.tools;

import java.awt.event.KeyEvent;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.util.internal.Multipliers;
import org.powerbot.game.bot.Context;
import org.powerbot.game.client.Client;

/**
 * @author Timer
 */
public class CameraNew {
	public static int getYaw() {
		final Client client = Context.client();
		final Multipliers multipliers = Context.multipliers();
		return (int) ((client.getCameraYaw() * multipliers.GLOBAL_CAMERAYAW) / 45.51);
	}

	public static int getAngleTo(final int degrees) {
		int ca = getYaw();
		if (ca < degrees) {
			ca += 360;
		}
		int da = ca - degrees;
		if (da > 180) {
			da -= 360;
		}
		return da;
	}

	public static int getPitch() {
		final Client client = Context.client();
		final Multipliers multipliers = Context.multipliers();
		return (int) (((client.getCameraPitch() * multipliers.GLOBAL_CAMERAPITCH) - 1024) / 20.48);
	}

	public synchronized static boolean setPitch(final int percent) {
		int curAlt = getPitch();
		int lastAlt = 0;
		if (curAlt == percent) {
			return true;
		}

		final boolean up = curAlt < percent;
		Keyboard.pressKey(up ? (char) KeyEvent.VK_UP : (char) KeyEvent.VK_DOWN,
				0, 0);

		final Timer timer = new Timer(100);
		while (timer.isRunning()) {
			if (lastAlt != curAlt) {
				timer.reset();
			}

			lastAlt = curAlt;
			Task.sleep(Random.nextInt(5, 10));
			curAlt = getPitch();

			if (up && curAlt >= percent) {
				break;
			} else if (!up && curAlt <= percent) {
				break;
			}
		}

		Keyboard.releaseKey(up ? (char) KeyEvent.VK_UP
				: (char) KeyEvent.VK_DOWN, 0, 0);
		return curAlt == percent;
	}

	public synchronized static void setCamera(int angle, int pitch) {
		angle %= 360;
		if (getAngleTo(angle) > 5) {
			Keyboard.pressKey((char) KeyEvent.VK_LEFT, 0, 0);
			final Timer timer = new Timer(500);
			int ang, prev = -1;
			while ((ang = getAngleTo(angle)) > 5 && Game.getClientState() == 11
					&& timer.isRunning()) {
				setPitch(pitch);
				if (ang != prev) {
					timer.reset();
				}
				prev = ang;
				Task.sleep(10);
			}
			Keyboard.releaseKey((char) KeyEvent.VK_LEFT, 0, 0);
		} else if (getAngleTo(angle) < -5) {
			Keyboard.pressKey((char) KeyEvent.VK_RIGHT, 0, 0);
			final Timer timer = new Timer(500);
			int ang, prev = -1;
			while ((ang = getAngleTo(angle)) < -5
					&& Game.getClientState() == 11 && timer.isRunning()) {
				setPitch(pitch);
				if (ang != prev) {
					timer.reset();
				}
				prev = ang;
				Task.sleep(10);
			}
			Keyboard.releaseKey((char) KeyEvent.VK_RIGHT, 0, 0);
		}
	}
}