package com.exfantasy.utils.tools.receipt_lottery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiptLotteryNoUtil {
	private static Logger logger = LoggerFactory.getLogger(ReceiptLotteryNoUtil.class);
	
	private final static String CONNECTION_USER_AGENT = "Mozilla/5.0";
	
	private final static int CONNECTION_TIMEOUT_IN_SECS = 5 * 1000;
	
	private final static String WEB_SITE_NAME = "財政部稅務入口網";
	private final static String URL = "http://invoice.etax.nat.gov.tw";

	private static void start() {
		List<Reward> rewards = getReceiptLotteryNo();
		for (Reward reward : rewards) {
			System.out.println(reward);
		}
	}
	
	public static List<Reward> getReceiptLotteryNo() {
		long startTime = 0;
		try {
			startTime = System.currentTimeMillis();
			logger.info(">>>>> 嘗試到 " + WEB_SITE_NAME + " [" + URL + "] 取得 Document");
			Document doc = getDocument(URL);
			logger.info("<<<<< 成功從 " + WEB_SITE_NAME + " [" + URL + "] 取得 Document, time-spent: " + (System.currentTimeMillis() - startTime) + " ms");
			
			startTime = System.currentTimeMillis();
			logger.info(">>>>> 嘗試從 " + WEB_SITE_NAME + " [" + URL + "] 取得中獎號碼");
			Element divWrapper = doc.select("div.wrapper").first();
			Element divMain = divWrapper.select("div.main").first();
			Element divBlockRight = divMain.select("div.block_right").first();
			Element divNumber = divBlockRight.select("div#number").first();
			List<Element> divAreas = getDivAreas(divNumber);
			List<Reward> rewards = getRewardNo(divAreas);
			logger.info("<<<<< 成功從 " + WEB_SITE_NAME + " [" + URL + "] 取得中獎號碼, time-spent: " + (System.currentTimeMillis() - startTime) + " ms");
			
			return rewards;
		} catch (IOException e) {
			logger.error("<<<<< 從 " + WEB_SITE_NAME + " [" + URL + "] 取得中獎資訊, 發生錯誤", e);
			return null;
		}
	}
	
	private static List<Element> getDivAreas(Element divNumber) {
		List<Element> divAreas = new ArrayList<>();
		
		String cssQuery = "div#area";
		
		int i = 1;
		boolean end = false;
		while (!end) {
			Element divArea = divNumber.select(cssQuery + i).first();
			boolean elemIsNull = divArea == null;
			if (!elemIsNull) {
				divAreas.add(divArea);
				i++;
			}
			end = elemIsNull;
		}
		return divAreas;
	}
	
	private static List<Reward> getRewardNo(List<Element> divAreas) {
		List<Reward> rewards = new ArrayList<>();
		
		for (Element divArea : divAreas) {
			String section = divArea.select("h2:contains(年)").first().text();
			section = convertSectionContent(section);

			Elements trs = divArea.select("table > tbody > tr");
			for (Element tr : trs) {
				String sRewardType = tr.select("td.title").text();
				RewardType rewardType = RewardType.convertByKeyword(sRewardType);
				if (rewardType != null) {
					String rewardNo = tr.select("td > span").text();
					if (rewardType == RewardType.THIRD_REWARD || rewardType == RewardType.SPECIAL_SIX) {
						if (rewardNo.contains("、")) {
							String[] rewardNos = rewardNo.split("、");
							for (String no : rewardNos) {
								Reward reward = new Reward();
								reward.setSection(section);
								reward.setRewardType(rewardType);
								reward.setNo(no);
								rewards.add(reward);
							}
						}
						else {
							Reward reward = new Reward();
							reward.setSection(section);
							reward.setRewardType(rewardType);
							reward.setNo(rewardNo);
							rewards.add(reward);
						}
					}
					else {
						Reward reward = new Reward();
						reward.setSection(section);
						reward.setRewardType(rewardType);
						reward.setNo(rewardNo);
						rewards.add(reward);
					}
				}
			}
		}
		return rewards;
	}

	private static Document getDocument(String url) throws IOException {
		return getDocument(url, null);
	}
	
	private static String convertSectionContent(String section) {
		String[] splitByYear = section.split("年");
		int chineseYear = Integer.parseInt(splitByYear[0]);
		String year = String.valueOf(chineseYear + 1911);
		String months = splitByYear[1].substring(0, splitByYear[1].length() - 1);
		
		return year + "_" + months;
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
	
	public static String getSection(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		String section = String.valueOf(year) + "_";
		int divide = (month + 1) / 2;
		switch (divide) {
			case 1:
				section += "01-02";
				break;
			case 2:
				section += "03-04";
				break;
			case 3:
				section += "05-06";
				break;
			case 4:
				section += "07-08";
				break;
			case 5:
				section += "09-10";
				break;
			case 6:
				section += "11-12";
				break;
		}
		return section;
	}
	
	public static Bingo checkIsBingo(String lotteryNo, List<Reward> rewards) {
		Bingo bingo = new Bingo(lotteryNo);
		for (Reward reward : rewards) {
			RewardType rewardType = reward.getRewardType();
			String number = reward.getNo();
			
			switch (rewardType) {
				// 特別獎
				case FIRST_REWARD:
					// 號碼完全相同
					if (lotteryNo.equals(number)) {
						bingo.setBingo(true);
						bingo.setPrize(10000000L);
						return bingo;
					}
					break;

				// 特獎
				case SEONCD_REWARD:
					// 號碼完全相同
					if (lotteryNo.equals(number)) {
						bingo.setBingo(true);
						bingo.setPrize(2000000L);
						return bingo;
					}
					break;

				// 頭獎
				case THIRD_REWARD:
					// 號碼完全相同
					if (lotteryNo.equals(number)) {
						bingo.setBingo(true);
						bingo.setPrize(200000L);
						return bingo;
					}
					else {
						// 最後7碼: 40000
						String last7OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 7, lotteryNo.length());
						String last7OfRewardNo = number.substring(number.length() - 7, number.length());
						if (last7OfLotteryNo.equals(last7OfRewardNo)) {
							bingo.setBingo(true);
							bingo.setPrize(40000L);
							return bingo;
						}
						
						// 最後6碼: 10000
						String last6OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 6, lotteryNo.length());
						String last6OfRewardNo = number.substring(number.length() - 6, number.length());
						if (last6OfLotteryNo.equals(last6OfRewardNo)) {
							bingo.setBingo(true);
							bingo.setPrize(10000L);
							return bingo;
						}
						
						// 最後5碼: 4000
						String last5OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 5, lotteryNo.length());
						String last5OfRewardNo = number.substring(number.length() - 5, number.length());
						if (last5OfLotteryNo.equals(last5OfRewardNo)) {
							bingo.setBingo(true);
							bingo.setPrize(10000L);
							return bingo;
						}
						
						// 最後4碼: 1000
						String last4OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 4, lotteryNo.length());
						String last4OfRewardNo = number.substring(number.length() - 4, number.length());
						if (last4OfLotteryNo.equals(last4OfRewardNo)) {
							bingo.setBingo(true);
							bingo.setPrize(10000L);
							return bingo;
						}
						
						// 最後3碼: 200
						String last3OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 3, lotteryNo.length());
						String last3OfRewardNo = number.substring(number.length() - 3, number.length());
						if (last3OfLotteryNo.equals(last3OfRewardNo)) {
							bingo.setBingo(true);
							bingo.setPrize(200L);
							return bingo;
						}
					}
					break;

				// 增開六獎
				case SPECIAL_SIX:
					String last3OfLotteryNo = lotteryNo.substring(lotteryNo.length() - 3, lotteryNo.length());
					if (last3OfLotteryNo.equals(number)) {
						bingo.setBingo(true);
						bingo.setPrize(200L);
						return bingo;
					}
					break;
			}
		}
		return bingo;
	}
	
	public static void main(String[] args) {
		start();
	}
}
