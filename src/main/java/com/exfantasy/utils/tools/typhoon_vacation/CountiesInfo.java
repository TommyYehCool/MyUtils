package com.exfantasy.utils.tools.typhoon_vacation;

public class CountiesInfo {
	private String counties;
	
	private String vacationInfo;

	public String getCounties() {
		return counties;
	}

	public void setCounties(String counties) {
		this.counties = counties;
	}

	public String getVacationInfo() {
		return vacationInfo;
	}

	public void setVacationInfo(String vacationInfo) {
		this.vacationInfo = vacationInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountiesInfo [counties=").append(counties).append(", vacationInfo=").append(vacationInfo)
				.append("]");
		return builder.toString();
	}
}
