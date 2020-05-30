package com.client;

import com.domain.User;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TheClientHTTP {
	
	String url="";
	
	public TheClientHTTP(String url) {
		this.url=url;
	}

	public void sendObject(Object object) throws IOException {
		
		URL urlObj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("Accept", "application/json");

		System.out.println("POSTed. Got the following response:");
		System.out.println(object);

		conn.disconnect();
	}
	
}
