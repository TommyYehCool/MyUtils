package com.exfantasy.utils.tools;

public class Reward {
	private String section;
	private RewardType rewardType;
	private String no;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reward [section=").append(section).append(", rewardType=").append(rewardType).append(", no=")
				.append(no).append("]");
		return builder.toString();
	}
	
}
