package com.exfantasy.testing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;

/**
 * http://www.baeldung.com/httpclient-4-basic-authentication
 * 
 * @author tommy.feng
 */
public class TestHttpUtil {
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		String url = "http://localhost:8080/login";
		String email = "tommy.yeh1112@gmail.com";
		String password = "1111";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		try {
			HttpUtil.sendPostRequest(url, params);
		} catch (HttpUtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
