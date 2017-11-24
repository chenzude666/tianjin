package com.stq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * java时间类
 * 
 * 
 */
public class JTime {
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat esFormat =   new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
	public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取两个日期之间所有的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static ArrayList<String> days(String date1, String date2) {
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		ArrayList<String> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
		    totalDates.add(start.toString());
		    start = start.plusDays(1);
		}
		return totalDates;
	}
	
	/**
	 * 获取两个日期之间所有的日期（包含date1、不包含date2）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static ArrayList<String> daysNotdate2(String date1, String date2) {
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		end = end.plusDays(-1);
		ArrayList<String> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
		    totalDates.add(start.toString());
		    start = start.plusDays(1);
		}
		return totalDates;
	}
	
	/**
	 * 获取两个日期之间所有的日期，n的值是多少，日期就间隔几天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public synchronized static ArrayList<String> days(String date1, String date2, int n) {
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		ArrayList<String> totalDates = new ArrayList<>();
		
		if(n == 0){
			totalDates.add(date1);
			totalDates.add(date2);
			return totalDates;
		}
		
		while (!start.isAfter(end)) {
		    totalDates.add(start.toString());
		    start = start.plusDays(n);
		}
		return totalDates;
	}
	
	/**
	 * N日后
	 * date1 的N日后，但是不超过 date2
	 * @param date
	 * @return
	 */
	public synchronized static String nextNDay(String date1, String date2, int n){
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		if(n == 0){
			return end.plusDays(1).toString();
		}else{
			if(start.plusDays(n).isAfter(end)){
				return end.plusDays(1).toString();
			}else{
				return start.plusDays(n).toString();
			}
		}
	}
	
	/**
	 * N日后
	 * date1 的N日后，但是不超过 date2 Coefficient
	 * @param date
	 * @return
	 */
	public synchronized static String nextNDayCoefficient(String date1, String date2, int n){
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		if(start.plusDays(n).isAfter(end)){
			return end.plusDays(0).toString();
		}else{
			return start.plusDays(n).toString();
		}
	}
	
	/**
	 * ES 查询使用的日期格式 2017-08-10 00:00:00   yyyy-MM-dd HH:mm:ss
	 * 2017-08-10 => 2017-08-10 00:00:00
	 * @param date
	 * @return
	 */
	public synchronized static String esDay(String date){
		//return esFormat.format(str2Date(date));
		StringBuilder s = new StringBuilder();
		s.append(date);
		s.append(" 00:00:00");
		return s.toString();
	}
	
	/**
	 * ES 查询使用的日期格式 2017-08-10 00:00:00   yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public synchronized static String esNextDay(String date){
		StringBuilder s = new StringBuilder();
		s.append(LocalDate.parse(date).plusDays(1));
		s.append(" 00:00:00");
		return s.toString();
	}
	
	/**
	 * excel 导出使用的日期格式 2017-08-10  yyyy-MM-dd 
	 * 2017-08-10 00:00:00 =>  2017-08-10
	 * @param date
	 * @return
	 */
	public synchronized static String excelDay(String date){
		if (date == null)
			return null;
/*		try {
			return format.format(esFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;*/
		return date.split(" ")[0];
	}
 
	/**
	 * 2017-08-10 => 日期
	 * @param str
	 * @return
	 */
	public synchronized static Date str2Date(String str) {
		return LocalDate.parse(str).toDate();
	}
 
	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	public synchronized static boolean isLeapYear(int year) {
		return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
	}
 
	/**
	 * 一个月有几天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public synchronized static int dayInMonth(int year, int month) {
		boolean yearleap = isLeapYear(year);
		int day;
		if (yearleap && month == 2) {
			day = 29;
		} else if (!yearleap && month == 2) {
			day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		} else {
			day = 31;
		}
		return day;
	}
	
	/**
	* @param dt 2017-09-11
	* @return 当前日期是星期几
	*/
	public synchronized static int getWeekOfDate(String dt) {
		return 	LocalDate.parse(dt).getDayOfWeek();
	}

    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public synchronized static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期  yyyy-MM-dd  2015-01-02=>1420128000000
     * @return 
     */  
    public synchronized static Long date2TimeStamp(String date){  
        return LocalDate.parse(date).toDateTimeAtStartOfDay().getMillis();  
    } 
    
    /** 
     * utc时间转为日期
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @return 2017-07-31T15:55:10.000Z  => 2017-07-31
     */  
    public synchronized static String utc2Data(String date_str){  
    	Calendar date = Calendar.getInstance();  
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT"));  
    	Date dateTime;
		try {
			dateTime = sdf.parse(date_str);
			date.setTime(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return format.format(date.getTime());  
    } 
    
    /** 
     * utc时间转为日期
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @return 2017-07-31T15:55:10.000Z  => 2017-07-31
     */  
    public synchronized static String utc2Data2(String date_str){  
    	Calendar date = Calendar.getInstance();  
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT"));  
    	Date dateTime;
		try {
			dateTime = sdf.parse(date_str);
			date.setTime(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return format2.format(date.getTime());  
    } 
    
	/** 
	   * 得到几天前的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	public synchronized static String getDateBefore(Date d,int day){  
		Calendar now =Calendar.getInstance();  
		now.setTime(d);  
		now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
		return esFormat.format(now.getTime());  
	}  
	
	/** 
	   * 得到几天前的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	public synchronized static String getDateBefore2(Date d,int day){  
		Calendar now =Calendar.getInstance();  
		now.setTime(d);  
		now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
		return format2.format(now.getTime());  
	}  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(JTime.days("2012-02-02", "2012-02-10"));
		System.out.println(JTime.daysNotdate2("2012-02-02", "2012-02-10"));
		System.out.println(JTime.nextNDay("2012-02-28","2012-02-29",1));
		System.out.println(JTime.days("2012-02-02", "2012-03-23",0));
		System.out.println(JTime.days("2012-02-02", "2012-03-23",1));
		System.out.println(JTime.days("2012-02-02", "2012-03-23",2));
		System.out.println(JTime.days("2012-02-02", "2012-03-23",4));
		
		System.out.println(JTime.days("2017-06-01", "2017-07-31",3000));
		
		System.out.println("111 "+JTime.utc2Data2("2017-07-31T15:55:10.000Z"));
		
		System.out.println(JTime.days(JTime.excelDay("2017-06-01 00:00:00"), JTime.excelDay("2017-08-01 00:00:00")));
		
		System.out.println(JTime.getDateBefore2(new Date(),30));
		
		System.out.println(Double.valueOf("8000000")/Double.valueOf("503895"));
		System.out.println(Double.valueOf("8000000")/( Double.valueOf("490000")*( Double.valueOf("24")/Double.valueOf("40") ) ));
		System.out.println(Double.valueOf("8000000")/Double.valueOf("503895"));
		System.out.println(Double.valueOf("8000000")/( Double.valueOf("490000")*( Double.valueOf("24")/Double.valueOf("40") ) ));
		System.out.println(Double.valueOf("8000000")/( Double.valueOf("490000")*( Double.valueOf("24")/Double.valueOf("40") )*Double.valueOf("0.8") ));
		
		
		System.out.println(JTime.days("2012-02-02", "2012-02-10"));
		System.out.println(JTime.daysNotdate2("2012-02-02", "2012-02-10"));
		
		
		System.out.println(JTime.days("2012-02-02","2012-02-10",10));
		
		String date1 = "2012-02-02";
		String date2 = "2012-02-10";
		LocalDate start = LocalDate.parse(date1);
		LocalDate end = LocalDate.parse(date2);
		List<String> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
		    totalDates.add(start.toString());
		    start = start.plusDays(10);
		}
		System.out.println(totalDates);
		
		System.out.println("--------");
		System.out.println(JTime.nextNDay("2012-02-28","2012-02-29",0));
		System.out.println(JTime.nextNDay("2012-02-28","2012-02-29",1));
		System.out.println(JTime.nextNDay("2012-02-28","2012-02-29",10));
		

		System.out.println(JTime.esNextDay("2012-02-29"));
		System.out.println(LocalDate.parse("2012-02-29").plusDays(1));
		
		System.out.println("1111  "   +  LocalDate.parse( "2015-01-02" ).toDate());
		
		System.out.println("1111  "   +  LocalDate.parse( "2015-01-02" ).toDateTimeAtStartOfDay().getMillis());
		
		
		System.out.println(LocalDate.now());
		
		
		List<String> dates = JTime.days(JTime.excelDay("2014-06-01"), JTime.excelDay("2017-10-01"),10);
		for (String string : dates) {
			System.out.println("111  "  + string);
		}
		
		System.out.println(JTime.nextNDay("2017-10-12",LocalDate.now().toString(),10));
		System.out.println(JTime.nextNDayCoefficient("2017-10-12",LocalDate.now().toString(),10));
		
		System.out.println(LocalDate.now());
		System.out.println(LocalDate.now().plusDays(-3));
		
		String a = LocalDate.now().toString() + "T20:50:35.177";
		String b = LocalDate.now().toString() + "T20:50:35.177";
		System.out.println(LocalDateTime.now().isAfter(LocalDateTime.parse(a)));
		System.out.println(LocalDateTime.now().isBefore(LocalDateTime.parse(b)));
	}
}
