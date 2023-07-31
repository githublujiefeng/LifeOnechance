package com.learn.java2.TimeUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    //无格式时间戳 分为 14位 12位 10位 8位...等
    public static final String TIMESTAMP_TIMEREGULAR="yyyyMMddHHmmss";
    //标准日期格式 2017-01-01 19:30:29
    public static final String GENERAL_TIMEREGULAR="yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) {
        Calendar c1 = Calendar.getInstance();
        //public final void set(int year,int month,int date)
        //c1.set(2009, 6 - 1, 12);public void set(int field,int value)
        c1.set(Calendar.DATE,10);
// 获得年份
        int year = c1.get(Calendar.YEAR);
// 获得月份
        int month = c1.get(Calendar.MONTH) + 1;
// 获得日期
        int date = c1.get(Calendar.DATE);
// 获得小时
        int hour = c1.get(Calendar.HOUR_OF_DAY);
// 获得分钟
        int minute = c1.get(Calendar.MINUTE);
// 获得秒
        int second = c1.get(Calendar.SECOND);
// 获得星期几（注意（这个与Date类是不同的）：1代表星期日、2代表星期1、3代表星期二，以此类推）
        int day = c1.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 各种日期类型转换
     */
    public static void timeType(){
        System.out.println("Java Date日期格式____:"+new Date());
        System.out.println("java 标准日期格式______:"+new SimpleDateFormat(GENERAL_TIMEREGULAR).format(new Date()));
        //java的 new Date().getTime() 获取的是毫秒 而unix获取的是秒
        System.out.println("java unix日期格式____:"+new Date().getTime()/1000L);
        System.out.println("java timestamp日期格式:"+new Timestamp(System.currentTimeMillis()));
        System.out.println("timestamp转date"+timestampToDate());
        System.out.println("date转string___:"+dateToString(new Date()));
        try {
            System.out.println("string转date____:" + strToDate("2017-01-01 19:23:12"));
        }catch (Exception e){
            return;
        }
        System.out.println("date转long___:"+dateToLong(new Date()));
        System.out.println("long转date ____:"+longToDate(dateToLong(new Date())));
        System.out.println("calendar转string ____:"+calendarToStr());
        System.out.println("Calendar转date______:"+calendarToDate());
        System.out.println("date转canledar____:"+dateToCalendar(new Date()));
    }

    //date类型转string类型的格式
    public static String dateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat(GENERAL_TIMEREGULAR);
        return sdf.format(date);
    }
    //string 类型date转 java Date
    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat(GENERAL_TIMEREGULAR);
        return sdf.parse(str);
    }
    //java date转long时间转换
    public static long dateToLong(Date date){
        return date.getTime();
    }
    //java long转date
    public static Date longToDate(long longtime){
        return new Date(longtime);
    }
    //calendar转string
    public static String calendarToStr() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(GENERAL_TIMEREGULAR);
        return sdf.format(calendar.getTime());
    }
    //calendar转date
    public static Date calendarToDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
    //date转calendar
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    //timestamp转date类型
    public static Date timestampToDate(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = new Date();
        date =ts ;
        return date;
    }

}
