package com.exfantasy.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	// http://stackoverflow.com/questions/5169468/handling-httpclient-redirects
	private final static HttpClient client 
		= HttpClientBuilder.create()
			.setRedirectStrategy(new LaxRedirectStrategy()).build();
	
	public static String sendGetRequest(String url) throws HttpUtilException {
		try {
			HttpGet get = new HttpGet(url);
			
			if (logger.isDebugEnabled()) {
				logger.debug(">>>>> Send GET request to url: <{}>", url);
			}
			
			HttpResponse response = client.execute(get);
			
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode / 100 != 2) {
				logger.warn("<<<<< Send GET request to url: <{}> got http status code: <{}>", url, httpStatusCode);
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			StringBuffer result = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			String responseData = result.toString();
			
			if (logger.isDebugEnabled()) {
				logger.debug("<<<<< Got response succeed with http status code: <{}>, response:{}", httpStatusCode, responseData);
			}
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException(
				"IOException raised while sending HTTP GET request to url: " + url, e, HttpUtilException.COMMUNICATE_ERROR);
		}
	}
	
	public static String sendPostRequest(String url, String jsonData) throws HttpUtilException {
		try {
			HttpPost post = new HttpPost(url);
			
			StringEntity input = new StringEntity(jsonData, "UTF-8");
			input.setContentType("application/json");
			post.setEntity(input);
			
			if (logger.isDebugEnabled()) {
				logger.debug(">>>>> Send Json POST request to url: <{}>, json: <{}>", url, jsonData);
			}
			
			HttpResponse response = client.execute(post);
			
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode / 100 != 2) {
				logger.warn("<<<<< Send Json POST request to url: <{}> got http status code: <{}>", url, httpStatusCode);
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			StringBuffer result = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			String responseData = result.toString();
			
			if (logger.isDebugEnabled()) {
				logger.debug("<<<<< Got response succeed with http status code: <{}>, response:{}", httpStatusCode, responseData);
			}
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException(
				"IOException raised while sending HTTP JSON POST request to url: " + url, e, HttpUtilException.COMMUNICATE_ERROR);
		}
	}
	
	public static String sendPostRequest(String url, List<NameValuePair> params) throws HttpUtilException {
		try {
			HttpPost post = new HttpPost(url);
	
			post.setEntity(new UrlEncodedFormEntity(params));
			
			if (logger.isDebugEnabled()) {
				logger.debug(">>>>> Send POST request to url: <{}>, data: <{}>", url, getLogString(params));
			}
	
			HttpResponse response = client.execute(post);
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode / 100 != 2 && httpStatusCode != 302) {
				logger.warn("<<<<< Send POST request to url: <{}> got http status code: <{}>", url, httpStatusCode);
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			String responseData = result.toString();
			
			if (logger.isDebugEnabled()) {
				logger.debug("<<<<< Got response succeed with http status code: <{}>, response:{}", httpStatusCode, responseData);
			}
			return responseData;
		} catch (IOException e) {
			throw new HttpUtilException(
				"IOException raised while sending HTTP JSON POST request to url: " + url, e, HttpUtilException.COMMUNICATE_ERROR);
		}
	}

	private static String getLogString(List<NameValuePair> params) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			NameValuePair param = params.get(i);
			buffer.append(param.getName()).append(":").append(param.getValue());
			if (i != params.size() - 1) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
}
