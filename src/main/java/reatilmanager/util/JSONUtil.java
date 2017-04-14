package reatilmanager.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * 
 * Using Google APi to Fetch Details of Shop Address
 * Geocoding: converting addresses into geographic coordinates
 * 
 * @author Neha Singla
 *
 */

public class JSONUtil {
	private static String GeoCodeGoogleURL ="https://maps.googleapis.com/maps/api/geocode/json?address=enteraddress&key=AIzaSyDaW2cf1KrVYj2EwbwM0C1h58i0e-OEBmU";

	public static String readLatLongFromAddress(String address) throws Exception {
		String url =GeoCodeGoogleURL.replaceAll("enteraddress", address) ;
		String jsonText = getJSON(url);
		JSONObject json = new JSONObject(jsonText);
		
		System.out.println(json);
		JSONArray mainAry=json.getJSONArray("results");
		JSONObject jsonAry =(JSONObject) mainAry.get(0);
		JSONObject geoObj =(JSONObject) jsonAry.get("geometry");
		JSONObject locaObj =(JSONObject) geoObj.get("location");
		return locaObj.getString("lng")+":"+locaObj.getString("lat");
	}

	public static String getJSON(String url) {
		HttpURLConnection c = null;
		try {
			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);

			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line+"\n");
				}
				br.close();
				return sb.toString();
			}

		} catch (Exception ex) {
			System.out.println("No JSON Data Found");
		}finally {
			if (c != null) {
				try {
					c.disconnect();
				} catch (Exception ex) {
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {

		System.out.println(readLatLongFromAddress("kurukshetra"));
	}
}
