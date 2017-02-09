package com.exfantasy.utils.http;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
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
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode, HttpUtilException.UNKNOWN_ERROR);
			}
			
			String responseData = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			
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
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode, HttpUtilException.UNKNOWN_ERROR);
			}
			
			String responseData = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			
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
			
			HttpClientContext context = HttpClientContext.create();
			
			HttpResponse response = client.execute(post, context);
			int httpStatusCode = response.getStatusLine().getStatusCode();
			if (httpStatusCode / 100 != 2) {
				logger.warn("<<<<< Send POST request to url: <{}> got http status code: <{}>", url, httpStatusCode);
				throw new HttpUtilException("Failed - HTTP error code: " + httpStatusCode, httpStatusCode, HttpUtilException.UNKNOWN_ERROR);
			}
			
			List<URI> redirectURIs = context.getRedirectLocations();
			if (redirectURIs != null && !redirectURIs.isEmpty()) {
				URI redirectURI = redirectURIs.get(0);
				String parameter = redirectURI.getQuery();
				if (parameter != null && parameter.contains("login_failed")) {
					throw new HttpUtilException("<<<<< Login failed got parameter: " + parameter, httpStatusCode, HttpUtilException.LOGIN_FAILED);
				}
			}
			
			String responseData = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			
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
