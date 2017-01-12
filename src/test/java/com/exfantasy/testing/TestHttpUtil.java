package com.exfantasy.testing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;

/**
 * <pre>
 * 測試 HttpUtil
 * 
 * 參考: <a href="http://www.baeldung.com/httpclient-4-basic-authentication">httpclient-4-basic-authentication</a>
 * 
 * 參考: <a href="https://blog.goyello.com/2015/10/01/different-ways-of-testing-exceptions-in-java-and-junit/">JUnit handle exception</a>
 * </pre>
 * 
 * @author tommy.feng
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHttpUtil {
	
	@Test
	public void test_1_loginCorrect() throws HttpUtilException {
		String url = "http://localhost:8080/login";
		String email = "tommy.yeh1112@gmail.com";
		String password = "1234qwer";
		
		sendLoginPostRequest(url, email, password);
	}

	@Test(expected = HttpUtilException.class)
	public void test_2_loginFailed() throws HttpUtilException {
		String url = "http://localhost:8080/login";
		String email = "tommy.yeh1112@gmail.com";
		String password = "1111";

		sendLoginPostRequest(url, email, password);
	}

	private void sendLoginPostRequest(String url, String email, String password) throws HttpUtilException {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		HttpUtil.sendPostRequest(url, params);
	}
}
