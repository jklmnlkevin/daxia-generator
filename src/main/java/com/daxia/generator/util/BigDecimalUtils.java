package com.daxia.generator.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

	public static BigDecimal moneyValue(BigDecimal old) {
		if (old == null) {
			return new BigDecimal("0.00");
		} else {
			return old.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	public static BigDecimal formatMoney(double money) {
		BigDecimal decimal = new BigDecimal(money);
		return decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal fromDouble(double number) {
		return new BigDecimal(number);
	}

	public static BigDecimal one(BigDecimal old) {
		if (old == null) {
			return new BigDecimal("0.0");
		} else {
			return old.setScale(1, BigDecimal.ROUND_HALF_UP);
		}
    }
	
	public static BigDecimal two(BigDecimal old) {
		if (old == null) {
			return new BigDecimal("0.00");
		} else {
			return old.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
    }
}
