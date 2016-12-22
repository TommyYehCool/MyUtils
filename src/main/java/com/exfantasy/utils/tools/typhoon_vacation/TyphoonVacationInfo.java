package com.exfantasy.utils.tools.typhoon_vacation;

import java.util.ArrayList;
import java.util.List;

public class TyphoonVacationInfo {
	private String region;
	
	private List<CountiesInfo> countiesInfos;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public void addCountiesInfo(CountiesInfo countiesInfo) {
		if (countiesInfos == null) {
			countiesInfos = new ArrayList<>();
		}
		countiesInfos.add(countiesInfo);
	}
	
	public List<CountiesInfo> getCountiesInfo() {
		return this.countiesInfos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TyphoonVacationInfo [region=").append(region).append(", countiesInfos=").append(countiesInfos)
				.append("]");
		return builder.toString();
	}
}
