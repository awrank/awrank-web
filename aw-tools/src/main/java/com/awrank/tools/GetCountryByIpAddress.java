package com.awrank.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alex Polyakov
 */
public class GetCountryByIpAddress {

	public static void main(String args[]) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://api.wipmania.com/212.66.60.247");
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String s = in.readLine();
			System.out.print(s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}
}
