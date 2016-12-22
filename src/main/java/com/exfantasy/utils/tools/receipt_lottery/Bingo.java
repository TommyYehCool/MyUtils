package com.exfantasy.utils.tools.receipt_lottery;

public class Bingo {
	private String lotteryNo;

	private boolean bingo;
	
	private long prize;
	
	public Bingo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}

	public String getLotteryNo() {
		return lotteryNo;
	}

	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}

	public boolean isBingo() {
		return bingo;
	}

	public void setBingo(boolean bingo) {
		this.bingo = bingo;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}
}
