package com.sgck.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	public static Date getDateUTC() {
		// 1銆佸彇寰楁湰鍦版椂闂达細
		Calendar cal = Calendar.getInstance();

		// 2銆佸彇寰楁椂闂村亸绉婚噺锛�
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		// 3銆佸彇寰楀浠ゆ椂宸細
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		// 4銆佷粠鏈湴鏃堕棿閲屾墸闄よ繖浜涘樊閲忥紝鍗冲彲浠ュ彇寰桿TC鏃堕棿锛�
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// 涔嬪悗璋冪敤cal.get(int x)鎴朿al.getTimeInMillis()鏂规硶鎵�彇寰楃殑鏃堕棿鍗虫槸UTC鏍囧噯鏃堕棿銆�
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 鑾峰彇UTC褰撳墠鏃ユ湡鍙婃椂闂�java.sql.Timestamp
	 *
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getTimestampUTC() {
		return date2Timestamp(getDateUTC());

	}

	/**
	 * 涓婁笅宸嚑涓湀鐨勬棩鏈熴�
	 *
	 * @param dd
	 * @param month
	 * @return
	 */
	public final static java.util.Date addMonth(java.util.Date dd, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dd);
		calendar.add(Calendar.MONTH, month);

		return calendar.getTime();
	}

	/**
	 * 鑾峰緱褰撳墠鏃堕棿,浠诲姟浣跨敤
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		return getDateUTC();
	}

	public static Timestamp getCurrentTime() {
		return new Timestamp((getCurrentDate()).getTime());
	}

	/**
	 *
	 * @return
	 *
	 */
	@SuppressWarnings({ "deprecation" })
	public static Date getLastDate() {
		return new Date(2099 - 1900, 1, 1);
	}

	public static Date getEarlyDate() {
		return new Date(1);
	}

	// =================================================杞崲?
	public static Timestamp date2Timestamp(Date endTime) {

		return new Timestamp(endTime.getTime());
	}

	public static String getNowTime() // ~yaofang
										// 杩斿洖"20131231092427046"褰㈠紡鐨勫瓧绗︿覆
	{
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sf.format(date); // yyyy骞淬�MM鏈堛�dd澶┿�hh鏃躲�mm鍒嗐�ss绉掋�SSS姣
	}

	public static String getSpecifiedDayBefore(String specifiedDay) {// 鍙互鐢╪ew
																		// Date().toLocalString()浼犻�鍙傛暟
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 往后加n天
	 * 
	 * @param dd
	 * @param month
	 * @return
	 */
	public final static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 往后加n年
	 * 
	 * @param dd
	 * @param month
	 * @return
	 */
	public final static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	// 时间操作方法新加

	// 时间转换
	public static Timestamp ConvertDateToTimeStamp(Date date) throws Exception {
		if (null == date) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	// 获取当前年月字符串
	public static String getCurrentYearMonth() throws ParseException {
		return getCurrentDate("yyyy-MM");
	}

	// 获取当前日期字符串精确到秒
	public static String getCurrentDateHHmmss() throws ParseException {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}

	public static String timestampToStr(Timestamp time, String format) throws Exception {
		// if(!isNULL(time)){
		Date date = new Date(time.getTime());
		return getDateStr(date, format);
		// }
		// return null;
	}

	public static String getCurrentDate(String format) throws ParseException {
		return getDateStr(new Date(), format);
	}

	public static String getDateStr(Date date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date getDate(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	public static Date getDate(Date date) throws ParseException {
		return getDate(date, "yyyy-MM-dd");
	}

	public static Date getDate(Date date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(sdf.format(date));
	}

	public static String getCurrentYear() {
		Date date = new Date();
		return getYear(date);
	}

	public static String getCurrentMonth() {
		Date date = new Date();
		return getMonth(date);
	}

	public static String getCurrentDay() {
		Date date = new Date();
		return getDay(date);
	}

	public static String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}

	public static String getMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(date);
	}

	public static String getDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(date);
	}

	/**
	 * 解析各种时间格式
	 * 
	 * @param orderDatetimeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String orderDatetimeStr) throws ParseException {
		return DateUtils.parseDate(orderDatetimeStr,
				new String[] { "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss",
						"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH",
						"yyyy-MM-dd HH:mm:ss.SSS" });
	}

	/**
	 * @Description 判断日期过期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean judgeDateExpire(Date startDate, Date endDate) {
		if (startDate.getTime() > endDate.getTime()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 2015年9月25日 TODO时区转换
	 * 
	 * @param date
	 *            转换日期
	 * @param sourceTimeZone
	 *            date时区
	 * @param targetTimeZone
	 *            需要转成时区
	 * @return
	 */
	public static Date getDateByTimeZone(Date date, TimeZone sourceTimeZone, TimeZone targetTimeZone) {
		Long targetTime = date.getTime() - sourceTimeZone.getRawOffset() + targetTimeZone.getRawOffset();
		return new Date(targetTime);
	}
	
	public final static int deff(Date sdate, Date edate) {
		long diff = edate.getTime() - sdate.getTime();
		return (int)diff/(60*60*1000*24);
	}
}
