package com.njwb.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	//默认格式，年-月-日 时:分:秒
	private static String PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取指定时间的指定格式的字符串
	 *格式：2020-08-29 16:24:23
	 * ---> yyyy-MM-dd HH:mm:ss
	 * 
	 * 如果参数date为null，则返回当前时间的字符串格式
	 * @return
	 */
	public static String dateToString(String pattern,Date date)
	{
		//如果没有给格式，使用默认格式
		if(StringUtil.isEmpty(pattern))
		{
			pattern = DateUtil.PATTERN;
		}
		//只负责日期的格式，不管日期对象怎么来的
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		//将日期转String
		
		//如果传入的date为null,则返回当前时间
		if(date == null)
		{
			date = new Date();
		}
		
		String timeStr = sdf.format(date);
		return timeStr;
	}
	
	
	/**
	 * 将日期格式的字符串转换成Date对象
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern)
	{
		//如果没有给格式，使用默认格式
		if(StringUtil.isEmpty(pattern))
		{
			pattern = DateUtil.PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			//将String转日期对象
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("转换异常，格式不对");
			date = null;
		}
		return date;
		
	}
	
	/**
	 * Date-->Timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimeStamp(Date date){
		//如果date为null，改以当前时间
		if(date == null){
			date = new Date();
		}
		Timestamp ts=new Timestamp(date.getTime());
		return ts;
		
	}
	
	/**
	 * 获取时间间隔
	 * （精确到分钟）
	 * @param dateX
	 * @param dateY
	 * @return
	 */
	public static long getDateInterval(Date dateX,Date dateY) {
		return (dateX.getTime()-dateY.getTime())/60000;
	}
	

}