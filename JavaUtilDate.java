package com.jsr310.datatime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class JavaUtilDate {
	/**
	 * @category 1582년 10월 4일의 다음 날은?
	 * */
	public static void main(String[] args) {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(1582, Calendar.OCTOBER , 4);
        String pattern = "yyyy.MM.dd";
        String theDay = toString(calendar, pattern, utc);
        // 1582.10.04 이 출력된다.
        System.out.println(theDay);

        // 1582.10.04에서 하루를 더한다. 
        calendar.add(Calendar.DATE, 1);
        String nextDay = toString(calendar, pattern, utc);
        // 1582.10.05을 예상하지만 1582.10.15이 출력된다.
        System.out.println(nextDay);
	}
	
    public static String toString(Calendar calendar, String pattern, TimeZone zone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(zone);
        return format.format(calendar.getTime());
    }
}
