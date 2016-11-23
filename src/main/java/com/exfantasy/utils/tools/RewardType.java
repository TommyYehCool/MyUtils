package com.exfantasy.utils.tools;

public enum RewardType {
	FIRST_REWARD(1, "特別獎"),
	SEONCD_REWARD(2, "特獎"),
	THIRD_REWARD(3, "頭獎"),
	SPECIAL_SIX(4, "增開六獎");
	
	private int code;
	private String keyword;
	
	private RewardType(int code, String keyword) {
		this.code = code;
		this.keyword = keyword;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getKeyword() {
		return this.keyword;
	}
	
	public static RewardType convertByCode(int code) {
		for (RewardType type : RewardType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
	
	public static RewardType convertByKeyword(String keyword) {
		for (RewardType type : RewardType.values()) {
			if (type.getKeyword().equals(keyword)) {
				return type;
			}
		}
		return null;
	}
}
