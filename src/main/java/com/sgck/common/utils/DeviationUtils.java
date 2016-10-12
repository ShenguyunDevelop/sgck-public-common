package com.sgck.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * 鍋忓樊瀛︿範澶勭悊
 *
 * @author Xiaolei
 */
public class DeviationUtils
{

	/**
	 * 绮剧‘鍒板埌灏忔暟浣嶆暟
	 */
	public static final int DECIMAL_COUNT = 1;

	/**
	 * 璁＄畻鏍囬噺鍙傝�鍊煎強鍋忓樊鍊�
	 *
	 * @param samplingList 閲囨牱鏁版嵁
	 * @param exclude	  鍓旈櫎鐜�
	 * @param scale		鏀惧ぇ鍊嶆暟
	 * @param minDeviation 鍋忓樊闃堝�
	 * @return double[] double[0]=涓哄弬鑰冨�, double[1]=涓哄亸宸�
	 */
	public static double[] getScalarDeviationValue(double[] samplingList,
												   double exclude, double scale, double minDeviation)
	{

		if (samplingList == null || samplingList.length <= 0)
		{
			return null;
		}
		int samplingCount = samplingList.length;
		double[] differenceArr = new double[samplingCount];

		double totalNum = 0;
		for (double num : samplingList)
		{
			totalNum += num;
		}
		double reference = totalNum / samplingCount;
		for (int i = 0; i < samplingCount; i++)
		{
			differenceArr[i] = Math.abs(samplingList[i] - reference);
		}
		double result = getDevResult(differenceArr, exclude, scale,
				minDeviation);
		return new double[]{doubleRound(reference, DECIMAL_COUNT),
				doubleRound(result, DECIMAL_COUNT)};
	}

	/**
	 * 璁＄畻鐭㈤噺鍙傝�鍊煎強鍋忓樊鍊�
	 *
	 * @param samplingList 閲囨牱鏁版嵁
	 *                     <p/>
	 *                     楂樻姤鍊�
	 * @param exclude	  鍓旈櫎鐜�
	 * @param scale		鏀惧ぇ鍊嶆暟
	 * @param minDeviation 鍋忓樊闃堝�
	 * @return double[] double[0]涓哄弬鑰冩尟骞�double[1]涓哄弬鑰冪浉浣�double[2]涓哄亸宸�
	 */
	public static double[] getVectorDeviationValue(double[][] samplingList,
												   double exclude, double scale, double minDeviation)
	{
		if (samplingList == null || samplingList.length <= 0)
		{
			return null;
		}
		int samplingCount = samplingList.length;
		double[] differenceArr = new double[samplingCount];

		double totalamplitudeNum = 0;
		double totalphaseNum = 0;
		for (double[] num : samplingList)
		{
			totalamplitudeNum += num[0];
			totalphaseNum += num[1];
		}
		double phaseRef = totalamplitudeNum / samplingCount;
		double amplitudeRef = totalphaseNum / samplingCount;

		for (int i = 0; i < samplingCount; i++)
		{
			differenceArr[i] = getDistance(
					samplingList[i][1],samplingList[i][0] , amplitudeRef,phaseRef);
		}
		double result = getDevResult(differenceArr, exclude, scale,
				minDeviation);
		return new double[]{doubleRound(amplitudeRef, DECIMAL_COUNT),
				doubleRound(phaseRef, DECIMAL_COUNT),
				doubleRound(result, DECIMAL_COUNT)};
	}

	/**
	 * 璁＄畻鍋忓樊鍊�
	 *
	 * @param differenceArr 閲囨牱鐩稿樊鍊�
	 *                      <p/>
	 *                      楂樻姤
	 * @param exclude	   鍓旈櫎鐜�
	 * @param scale		 鏀惧ぇ鍊嶆暟
	 * @param minDeviation  鍋忓樊闃堝�
	 * @return 鍋忓樊鍊�
	 */
	private static double getDevResult(double[] differenceArr,
									   double exclude, double scale, double minDeviation)
	{
		int len = differenceArr.length;
		int deleteCount = (int) (doubleRound(len * exclude, 0));
		Arrays.sort(differenceArr);
		double result = differenceArr[len - deleteCount - 1];
		result *= scale;
		if (result < minDeviation)
		{
			result = minDeviation;
		}
		return result;
	}

	/**
	 * 鍥涜垗浜斿叆锛岀簿纭暟瀛�
	 *
	 * @param num   绮剧‘鏁版嵁
	 * @param round 淇濈暀灏忔暟浣嶆暟
	 * @return double
	 */
	public static double doubleRound(double num, int round)
	{
		BigDecimal bd = new BigDecimal(num);
		bd = bd.setScale(round, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * 鑾峰彇鐭㈤噺鍧愭爣涓瀬鍧愭爣涓ょ偣璺濈
	 *
	 * @param p1 鎸箙1
	 * @param r1 鐩镐綅1
	 * @param p2 鎸箙2
	 * @param r2 鐩镐綅2
	 * @return double 璺濈
	 */
	public static double getDistance(double p1, double r1, double p2, double r2)
	{
		return Math.sqrt(Math.pow(p1, 2) + Math.pow(p2, 2) + 2 * p1 * p2
				* Math.cos(r1 - r2));
	}
}
