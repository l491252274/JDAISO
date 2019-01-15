package com.unlimited.appserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequest {

	public static String doGet(String url, Map<String, String> para)
			throws IOException {
		if (para == null || para.size() == 0) {
			return doGet(url);
		}
		url += "?";
		for (String key : para.keySet()) {
			url += key + "=" + para.get(key) + "&";
		}
		return doGet(url.substring(0, url.length() - 1));
	}

	public static String doGet(String url) throws IOException {

		HttpURLConnection connection = (HttpURLConnection) new URL(url)
				.openConnection();
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		String ans = new String("");

		String lines;
		while ((lines = reader.readLine()) != null) {
			ans += lines;
		}

		reader.close();
		connection.disconnect();
		return ans;

	}

	public static String doPost(String url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url)
				.openConnection();
		connection.setRequestMethod("post");
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "utf-8"));
		String ans = new String("");

		String lines;
		while ((lines = reader.readLine()) != null) {
			ans += lines;
		}

		reader.close();
		connection.disconnect();
		return ans;
	}
}