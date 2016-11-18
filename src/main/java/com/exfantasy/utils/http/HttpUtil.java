package com.exfantasy.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {
	
	private static HttpClient client = HttpClientBuilder.create().build();
	
	public static String sendGetRequest(String url) throws HttpUtilException {
		try {
			HttpGet get = new HttpGet(url);
			
			HttpResponse response = client.execute(get);
			
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode != HttpStatus.SC_OK) {
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String responseData = null;
			String output;
			while ((output = br.readLine()) != null) {
				responseData = output;
			}
			System.out.println("HttpStatusCode: " + httpStatusCode + ", response: " + responseData);
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException("IOException raised while sending HTTP GET request to url: " + url, e);
		}
	}
	
	public static String sendPostRequest(String url, String jsonData) throws HttpUtilException {
		try {
			HttpPost post = new HttpPost(url);
			
			StringEntity input = new StringEntity(jsonData);
			input.setContentType("application/json");
			post.setEntity(input);
			
			HttpResponse response = client.execute(post);
			
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode != HttpStatus.SC_OK) {
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String responseData = null;
			String output;
			while ((output = br.readLine()) != null) {
				responseData = output;
			}
			System.out.println("HttpStatusCode: " + httpStatusCode + ", response: " + responseData);
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException("IOException raised while sending HTTP POST request to url: " + url, e);
		}
	}
}
