package spectrum.tools.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Access {

	public static String getName(int name) {
		try {
			URL url = new URL("http://xero-rsps.webs.com/itemdump.txt");
			InputStream in = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (strLine.contains("" + name))
					return strLine.split("-")[1].replaceFirst(" ", "");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int getPrice(int id) {
		try {
			String price;
			URL url = new URL("http://open.tip.it/json/ge_single_item?item="
					+ id);
			URLConnection con = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				if (line.contains("mark_price")) {
					price = line.substring(line.indexOf("mark_price") + 13,
							line.indexOf(",\"daily_gp") - 1);
					price = price.replace(",", "");
					return Integer.parseInt(price);

				}
			}
		} catch (Exception e) {
			return -1;
		}

		return -1;
	}

	public static boolean updateSignature(String url) {
		try {
			URL submit = new URL(url);
			URLConnection con = submit.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			final BufferedReader rd = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
				if (line.toLowerCase().contains("success"))
					return true;
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
