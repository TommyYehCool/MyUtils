package com.exfantasy.utils.tools;

import java.util.ArrayList;
import java.util.List;

public class Reward {
	private String section;
	private RewardType rewardType;
	private List<String> nos;

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

	public List<String> getNos() {
		return nos;
	}

	public void addNo(String no) {
		if (nos == null) {
			nos = new ArrayList<>();
		}
		nos.add(no);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reward [section=").append(section).append(", rewardType=").append(rewardType).append(", nos=")
				.append(nos).append("]");
		return builder.toString();
	}
}
