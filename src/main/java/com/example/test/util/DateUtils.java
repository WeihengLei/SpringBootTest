package com.example.test.util;


import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date getDateStart(Date d){
        Calendar cd = Calendar.getInstance(); 
        cd.setTime(d);
        cd.set(Calendar.HOUR_OF_DAY, 0);  
        cd.set(Calendar.MINUTE, 0);  
        cd.set(Calendar.SECOND, 0);  
        cd.set(Calendar.MILLISECOND, 0); 
        return cd.getTime();
	}
	/**
	 * 获取日期的结束时间
	 * 2015.6.10
	 * @author LK
	 * @param
	 * @return Date
	 * */
	public static Date getDateEnd(Date d){
        Calendar cd = Calendar.getInstance(); 
        cd.setTime(d);
        cd.set(Calendar.HOUR_OF_DAY, 23);  
        cd.set(Calendar.MINUTE, 59);  
        cd.set(Calendar.SECOND, 59);  
        cd.set(Calendar.MILLISECOND, 999);
        return cd.getTime();
	}
	

	
	/**
	 * 获取日期的后n天的时间
	 * 2015.7.9
	 * @author LK
	 * @param sourceDay 要计算的日期
	 * @param days 后n天
	 * @return Date
	 * */
	public static Date getDateAfterDays(Date sourceDay,int days){
		//return new Date(sourceDay.getTime()+days*24 * 60 * 60 * 1000);
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.DAY_OF_MONTH, days);	
		return c.getTime();

		//return getDateBeforeDays(sourceDay,-days);
	}
	
	/**
	 * 获取日期的前n天的时间
	 * 2015.6.10
	 * @author LK
	 * @param sourceDay 要计算的日期
	 * @param days 前n天
	 * @return Date
	 * */
	public static Date getDateBeforeDays(Date sourceDay,int days){
		//return new Date(sourceDay.getTime()-days*24 * 60 * 60 * 1000);
		/*Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.DAY_OF_MONTH, -days);
		return c.getTime();
		*/
		return getDateAfterDays(sourceDay,-days);
	}
	
	
	public static Date getDateAfterYears(Date sourceDay,int years){
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.YEAR, years);	
		return c.getTime();
	}
	
	public static Date getDateBeforeYears(Date sourceDay,int years){
		return getDateAfterYears(sourceDay,-years);
	}
	
	public static Date getDateAfterMonths(Date sourceDay,int month){
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.MONTH, month);	
		return c.getTime();
	}
	
	public static Date getDateBeforeMonths(Date sourceDay,int month){
		return getDateAfterMonths(sourceDay,-month);
	}	
	
	public static Date getDateAfterHours(Date sourceDay,int hours){
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.HOUR_OF_DAY, hours);	
		return c.getTime();
	}
	
	public static Date getDateBeforeHours(Date sourceDay,int hours){
		return getDateAfterHours(sourceDay,-hours);
	}
	
	public static Date getDateAfterMinutes(Date sourceDay,int ms){
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.MINUTE, ms);	
		return c.getTime();
	}
	
	public static Date getDateBeforeMinutes(Date sourceDay,int ms){
		return getDateAfterMinutes(sourceDay,-ms);
	}
	
	public static Date getDateAfterSeconds(Date sourceDay,int sec){
		Calendar c = Calendar.getInstance();
		c.setTime(sourceDay);
		c.add(Calendar.SECOND, sec);	
		return c.getTime();
	}
	
	public static Date getDateBeforeSeconds(Date sourceDay,int sec){
		return getDateAfterSeconds(sourceDay,-sec);
	}
	

	/**
	 * 获取两个日期相差的天数
	 * 2015.6.10
	 * @author LK
	 * @param dateAfter  后日期
	 * @param dateBefore  前日期
	 * @return Date
	 * */
	public static int getDaysBetween(Date dateAfter,Date dateBefore){
		dateAfter = getDateStart(dateAfter);
		dateBefore = getDateStart(dateBefore);
		return (int)((dateAfter.getTime()/1000-dateBefore.getTime()/1000))/3600/24;
	}	

	public static int getAgeAtGivenDateByDob(Date inputDob, Date givenDate){
		  Calendar dob = Calendar.getInstance();  
		  dob.setTime(inputDob);  
		  Calendar today = Calendar.getInstance(); 
		  today.setTime(givenDate);   
		  int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		  if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		    age--;  
		  } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		      && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		    age--;  
		  }		  
		  return age;
	}

}
