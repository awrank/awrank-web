package com.awrank.web.model.utils.externalService;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alex Polyakov
 */
public class WIPmania {
	private static final Logger LOG = Logger.getLogger(WIPmania.class);

	public static String getCountryCodeByIpAddress(String ipAddress) {
		String result = "";
		if(ipAddress.indexOf("%")>-1) ipAddress = ipAddress.split("[%]")[0];
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://api.wipmania.com/" + ipAddress);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			result = in.readLine();
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (null != connection) {
				try{
					connection.disconnect();
				}catch(Exception ex){
					//so sad connection.connected is a protected field and we can't check it here! 
				}
			}
		}
		if(result.length()>2) result = result.substring(result.length()-2, result.length());
		return result;
	}
}
