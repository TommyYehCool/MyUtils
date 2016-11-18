package com.exfantasy.utils.arith;

import java.math.BigDecimal;

public class ArithUtil {
	public static final int ROUND_UP = 0;
	public static final int ROUND_DOWN = 1;
	public static final int ROUND_CEILING = 2;
	public static final int ROUND_FLOOR = 3;
	public static final int ROUND_HALF_UP = 4;
	public static final int ROUND_HALF_DOWN = 5;
	public static final int ROUND_HALF_EVEN = 6;
	public static final int ROUND_UNNECESSARY = 7;

	public static double add(double value1, double value2) {
		BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

		return bigDecimal1.add(bigDecimal2).doubleValue();
	}

	public static double divide(double value1, double value2) {
		return divide(value1, value2, 10);
	}

	public static double divide(double value1, double value2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

		return bigDecimal1.divide(bigDecimal2, scale, 4).doubleValue();
	}

	public static int gcm(int value1, int value2) {
		if (value1 == 0) {
			return value2;
		}
		if (value2 == 0) {
			return value1;
		}
		if (value1 < 0) {
			return gcm(value1 * -1, value2);
		}
		if (value2 < 0) {
			return gcm(value1, value2 * -1);
		}
		if (value1 > value2) {
			return gcm(value1 % value2, value2);
		}
		return gcm(value2 % value1, value1);
	}

	public static int gcm(int[] valueArray) {
		int num = 0;
		for (int i = 0; i < valueArray.length; i++) {
			if ((num = gcm(num, valueArray[i])) == 1) {
				return num;
			}
		}
		return num;
	}

	public static double max(double[] valueArray) {
		double max = valueArray[0];
		for (int i = 1; i < valueArray.length; i++) {
			if (max < valueArray[i]) {
				max = valueArray[i];
			}
		}
		return max;
	}

	public static int max(int[] valueArray) {
		int max = valueArray[0];
		for (int i = 1; i < valueArray.length; i++) {
			if (max < valueArray[i]) {
				max = valueArray[i];
			}
		}
		return max;
	}

	public static double min(double[] valueArray) {
		double min = valueArray[0];
		for (int i = 1; i < valueArray.length; i++) {
			if (min > valueArray[i]) {
				min = valueArray[i];
			}
		}
		return min;
	}

	public static int min(int[] valueArray) {
		int min = valueArray[0];
		for (int i = 1; i < valueArray.length; i++) {
			if (min > valueArray[i]) {
				min = valueArray[i];
			}
		}
		return min;
	}

	public static double mul(double value1, double value2) {
		BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

		return bigDecimal1.multiply(bigDecimal2).doubleValue();
	}

	public static int[] prime(int[] valueArray) {
		int[] prime = new int[valueArray.length];

		int gcm = gcm(valueArray);
		for (int i = 0; (i < valueArray.length) && (gcm >= 1); i++) {
			valueArray[i] /= gcm;
		}
		return prime;
	}

	public static double round(double value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return bigDecimal.divide(one, scale, 4).doubleValue();
	}

	public static double round(double value, int scale, int roundingMode) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return bigDecimal.divide(one, scale, roundingMode).doubleValue();
	}

	public static int round(int value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		int powerValue10Base = (int) Math.pow(10.0D, scale);
		int modValue = value % powerValue10Base;
		int cmpPowerValue10Base = (int) Math.pow(10.0D, scale - 1);

		return value - modValue + (modValue < 5 * cmpPowerValue10Base ? 0 : powerValue10Base);
	}

	public static double sub(double value1, double value2) {
		BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

		return bigDecimal1.subtract(bigDecimal2).doubleValue();
	}
}
