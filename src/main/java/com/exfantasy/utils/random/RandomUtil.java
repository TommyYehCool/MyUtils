package com.exfantasy.utils.random;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

public class RandomUtil {
	// 隨機碼 英文
	private final static char[] characters 
		= { 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
			'Y', 'Z' 
		  };

	// 隨機碼 數字
	private final static char[] numbers 
		= { 
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 
		  };

	// 隨機碼 全部
	private final static char[] mixed = ArrayUtils.addAll(characters, numbers);
	
	public enum CodeType {
		CHARACTERS, NUMBERS, MIXED;
	}
	
	public static String getRandomCode(CodeType codeType, int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(getRandomCodeFromResource(codeType));
		}
		return buffer.toString();
	}

	private static String getRandomCodeFromResource(CodeType codeType) {
		char[] resources = mixed;
		switch (codeType) {
			case CHARACTERS:
				resources = characters;
				break;
			case NUMBERS:
				resources = numbers;
				break;
			case MIXED:
				resources = mixed;
				break;
		}
		Random random = new Random();
		return String.valueOf(resources[random.nextInt(resources.length)]);
	}
}
