package spectrum.tools;

public class Log {
	public static void log(String type, String notice) {
		System.out.println("[" + type.toUpperCase() + "] " + notice);
	}
}
