package com.slow.college.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类
 * 
 * @author glcword
 *
 */
public class DateUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String YYYY_MM_DD_ = "yyyy/MM/dd";
	public static final String YYYY_MM_DDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String YYYY_MM_DDHHMM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYY_MM_DDHHMMSSSSS_ = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String dd_MM_YY = "dd-MM-yy";
	public static final String MM_DD = "MM月dd日";
	public static final String YYYY_MM_DD_2="yyyy年MM月dd日";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static String defaultDatePattern = "yyyy-MM-dd";

	/**
	 * 获得默认的 date pattern
	 * 
	 * @return
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 计算两日期的差 可返回相差多少年，多少月，多少天
	 * 
	 * @param startDay
	 *            需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
	 * @param endDay
	 *            被比较的时间 为空(null)则为当前时间
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return 举例：
	 *         <p>
	 *         compareDate("2009-09-12", null, 0);//比较天
	 *         <p>
	 *         compareDate("2009-09-12", null, 1);//比较月
	 *         <p>
	 *         compareDate("2009-09-12", null, 2);//比较年
	 */
	public static int compareDate(String startDay, String endDay, int stype) {
		int n = 0;
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		endDay = endDay == null ? DATE_FORMAT.format(getNowDate()) : endDay;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(startDay));
			c2.setTime(df.parse(endDay));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		return n;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static final Date getNowDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 获取当前时间格式化成字符串
	 * 
	 * @return
	 */
	public static final String getNowDateToString(String format) {
		return parseString(Calendar.getInstance().getTime(), format);
	}

	/**
	 * 获取当前时间格式化成字符串 默认格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static final String getNowDateDefaultString() {
		return parseString(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param format要显示的格式
	 * @return
	 */
	public static final String parseString(Date date, String format) {
		SimpleDateFormat sf = null;
		if (format != null && !"".equals(format)) {
			sf = new SimpleDateFormat(format);
		} else {
			sf = new SimpleDateFormat("yyyy-MM-dd");
		}
		if (date != null) {
			return sf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static final String parseString(String date, String format) {
		return parseString(parseDate(date), format);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static final Date parseDate(String date) {
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sf = null;
			if (date.length() == 10) {
				sf = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			try {
				return sf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static final Date parseDate(String date, String format) {
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sf = null;
			if (StringUtils.isNotBlank(format)) {
				sf = new SimpleDateFormat(format);
			} else if (date.length() == 10) {
				sf = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			try {
				return sf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 现有时间加上秒
	 * 
	 * @param nowDate
	 * @param second
	 * @return
	 */
	public static Date addDateSecond(Date nowDate, int second) {
		try {
			Date afterDate = new Date(nowDate.getTime() + second * 1000);
			return afterDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取时间的毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getTime(Date date) {
		if (date != null) {
			return date.getTime();
		}
		return 0;
	}

	/**
	 * 计算2个日期相差几天
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		if (smdate == null || bdate == null)
			return 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 返回给定天的num天后的字符串
	 * 
	 * @param curDay
	 * @param dateFormat
	 * @param num
	 * @return
	 */
	public static String getNextDay(String curDay, String dateFormat, int num) {
		SimpleDateFormat sdf = null;
		Calendar cal = null;
		Date date = null;
		String dateStr = null;

		sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			date = sdf.parse(curDay);
			cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, num);
			date = cal.getTime();
			dateStr = sdf.format(date);
		} catch (ParseException e) {
			return curDay;
		}

		return dateStr;
	}

	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		return (strDate == null || strDate.equals("")) ? null : new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 在日期上增加小时
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            小时数
	 * @return
	 */
	public static Date addHour(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数个整日
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数个整日
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            天数
	 * @return
	 */
	public static String addDay(String date, int n) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = ymd.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DAY_OF_MONTH, n);
			return ymd.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 是否为次日凌晨房
	 * 
	 * @param hour
	 *            HH:mm:ss 最晚到店时间
	 */
	public static boolean isNextDayNight(String hour) {
		if (hour.contains(":")) {
			hour = hour.substring(0, hour.indexOf(":"));
			if (hour.startsWith("0")) {
				hour = hour.replaceAll("0", "");
			}
		}
		int h = Integer.parseInt(hour);
		if (h <= 6) {
			return true;
		}
		return false;
	}

	public static String date2str(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	// 获取当前时间后的10分钟时间
	public static String getCurrNextM(String fmt, int m, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date currDate = new Date();
		int time = m * 60 * 1000;
		Date nextDate = new Date(currDate.getTime() + time);
		return df.format(nextDate);
	}

	public static String getTimeNextM(String timeStr, int m, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date currDate = null;
		try {
			currDate = df.parse(timeStr);
		} catch (ParseException e) {
		}
		int time = m * 60 * 1000;
		Date nextDate = new Date(currDate.getTime() + time);
		return df.format(nextDate);
	}

	// 获取当前时间后的10分钟时间
	public static String getCurrNextH(String fmt, int m) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date currDate = null;
		try {
			currDate = df.parse(fmt);
			// currDate = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int time = m * 60 * 1000 * 60;
		Date nextDate = new Date(currDate.getTime() + time);
		return df.format(nextDate);
	}

	/**
	 * 返回预设Format的当前日期字符串
	 * 
	 * @return
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 返回指定Format的当前日期字符串
	 * 
	 * @param ft
	 * @return
	 */
	public static String getToday(String ft) {
		Date today = new Date();
		return format(today, ft);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, getDatePattern());
	}

	public static String toYmdDate(String time) {
		SimpleDateFormat ymd = new SimpleDateFormat(defaultDatePattern);
		try {
			return ymd.format(ymd.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 查看两个日期相差多少天
	 * 
	 * @param preDate
	 * @param lastDate
	 * @return
	 */
	public static int dayDiff(String preDate, String lastDate) {
		SimpleDateFormat sd = new SimpleDateFormat(YYYY_MM_DD);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long diff = 0;
		try {
			diff = sd.parse(lastDate).getTime() - sd.parse(preDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = (int) (diff / nd);// 计算差多少天
		return day;
	}

	/*
	 * 
	 * 获取当前时间之前或之后几小时 hour
	 * 
	 */

	public static String getTimeByHour(int hour) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/*-5:前五分钟
	 * 5：后五分钟
	 * 
	 * 获取当前时间之前或之后几分钟 minute
	 * 
	 */

	public static String getTimeByMinute(int minute) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, minute);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	public static void main(String[] args) {
		String date1 = getNowDateToString("HH:mm");
		System.out.println(DateUtil.parseDate("15:00", "HH:mm").before(DateUtil.parseDate(date1, "HH:mm")));
	}
}
