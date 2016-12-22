package com.exfantasy.utils.tools.typhoon_vacation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class TyphoonVacationInfo {
	private String region;
	/**
	 * ref: http://stackoverflow.com/questions/10978748/jackson-deserialization-with-contained-arraylistt
	 */
	@JsonDeserialize(as = ArrayList.class, contentAs = CountiesInfo.class)
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
	
	public List<CountiesInfo> getCountiesInfos() {
		return countiesInfos;
	}

	public void setCountiesInfos(List<CountiesInfo> countiesInfos) {
		this.countiesInfos = countiesInfos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TyphoonVacationInfo [region=").append(region).append(", countiesInfos=").append(countiesInfos)
				.append("]");
		return builder.toString();
	}
}
