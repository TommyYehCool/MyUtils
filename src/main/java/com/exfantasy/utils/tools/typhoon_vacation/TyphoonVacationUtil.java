package com.exfantasy.utils.tools.typhoon_vacation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TyphoonVacationUtil {
	private static Logger logger = LoggerFactory.getLogger(TyphoonVacationUtil.class);
	
	private final static String CONNECTION_USER_AGENT = "Mozilla/5.0";
	
	private final static int CONNECTION_TIMEOUT_IN_SECS = 5 * 1000;
	
	private final static String WEB_SITE_NAME = "台灣颱風資訊中心";
	private final static String URL = "http://typhoon.ws/lifeinfo/stop_working";
	
	private static void start() {
		List<TyphoonVacationInfo> typhoonVacationInfos = getTyphoonVactionInfo();
		System.out.println(typhoonVacationInfos);
	}

	private static List<TyphoonVacationInfo> getTyphoonVactionInfo() {
		long startTime = 0;
		try {
			startTime = System.currentTimeMillis();
			logger.info(">>>>> 嘗試到 " + WEB_SITE_NAME + " [" + URL + "] 取得 Document");
			Document doc = getDocument(URL);
			logger.info("<<<<< 成功從 " + WEB_SITE_NAME + " [" + URL + "] 取得 Document, time-spent: " + (System.currentTimeMillis() - startTime) + " ms");
			
			startTime = System.currentTimeMillis();
			logger.info(">>>>> 嘗試從 " + WEB_SITE_NAME + " [" + URL + "] 取得颱風假資訊");
			Elements divVacations = doc.select("div.col_2of3_r");
			List<TyphoonVacationInfo> typhoonVacationInfos = getVacationInfo(divVacations);
			logger.info("<<<<< " + WEB_SITE_NAME + " [" + URL + "] 取得颱風假資訊, time-spent: " + (System.currentTimeMillis() - startTime) + " ms");
			
			return typhoonVacationInfos;
		} catch (IOException e) {
			logger.error("<<<<< 從 " + WEB_SITE_NAME + " [" + URL + "] 取得颱風假資訊, 發生錯誤", e);
			return null;
		}
	}
	
	private static List<TyphoonVacationInfo> getVacationInfo(Elements divVacations) {
		List<TyphoonVacationInfo> typhoonInfos = new ArrayList<>();
		for (Element divVacation : divVacations) {
			String region = divVacation.select("div.col_head").text();
			TyphoonVacationInfo typhoonInfo = new TyphoonVacationInfo();
			typhoonInfo.setRegion(region);
			
			Elements trs = divVacation.select("table > tbody > tr");
			for (Element tr : trs) {
				CountiesInfo countiesInfo = new CountiesInfo();
				
				Elements tds = tr.select("td");
				for (int index = 0; index < tds.size(); index++) {
					Element td = tds.get(index);
					switch (index) {
						case 0:
							countiesInfo.setCounties(td.text());
							break;
						case 1:
							countiesInfo.setVacationInfo(td.text());
							break;
					}
				}
				typhoonInfo.addCountiesInfo(countiesInfo);
			}
			typhoonInfos.add(typhoonInfo);
		}
		return typhoonInfos;
	}

	private static Document getDocument(String url) throws IOException {
		return getDocument(url, null);
	}
	
	private static Document getDocument(String url, Map<String, String> cookie) throws IOException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(CONNECTION_TIMEOUT_IN_SECS);
		connection.userAgent(CONNECTION_USER_AGENT);
		if (cookie != null) {
			connection.cookies(cookie);
		}
		Document doc = connection.get();
		return doc;
	}
	
	public static void main(String[] args) {
		start();
	}
}
