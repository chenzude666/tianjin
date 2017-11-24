package com.stq.util;

import java.math.BigDecimal;

/**
 * 消除加减乘除的精度,解决Float与Double类型进度不准确的问题.
 */
public class DoubleUtil {
	/**
	 * 加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}


	/**
	 * 减法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}


	/**
	 * 乘法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}


	/**
	 * 除法运算
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 商
	 */
	public static double div(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2).doubleValue();
	}


	/**
	 * 除法运算
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 商和余数
	 */
	public static BigDecimal[] divideAndRemainder(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal[] arr = b1.divideAndRemainder(b2);
		return arr;
	}


	/**
	 * 求商（向下舍入）
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal divideToIntegralValue(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		System.out.println("------------");
		return b1.divideToIntegralValue(b2);
	}


	public static void main(String[] args) {
		testUnPrecision();
		System.out.println("----------使用BigDecimal消除精度影响------------\n"
				+ DoubleUtil.add(0.05, 0.01));
		System.out.println(DoubleUtil.sub(1.0, 0.54));
		System.out.println(DoubleUtil.mul(4.015, 1000));
		System.out.println(DoubleUtil.div(12.3, 10));
		// 得到商和余数
		BigDecimal[] arr = DoubleUtil.divideAndRemainder(12.3, 10);
		System.out.println("得到商和余数");
		for (BigDecimal bigDecimal : arr) {
			System.out.println(bigDecimal);
		}
		System.out.println(DoubleUtil.divideToIntegralValue(4.5, 2));
	}


	/**
	 * 不准确问题示例
	 */
	private static void testUnPrecision() {
		System.out.println("--------Java自身的Double类型有精度损失----------");
		System.out.println(0.05 + 0.01);
		System.out.println(1.0 - 0.54);
		System.out.println(4.015 * 1000);
		System.out.println(12.3 / 100);
	}
}