package com.date.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Jsr310 {
	public static void main(String[] args) {
		ZoneDate();
	}
	
	/**
	 * LocalDate.now의 문제는 서버 시스템의 날짜(시간)를 LocalDate의 정보로 가진다는 점이다.
	 * 한국에 서버를 두고 있는 서비스를 미국의 유저가 사용한다면 날짜(시간)가 맞지 않게 표시될 수 있는 문제점을 가지고 있다.
	 * */
	public static void CurrentDate() {
		LocalDate now = LocalDate.now();
        System.out.println(now);
	}
	
	/**
	 * 특정 날짜를 리턴한다.
	 * */
	public static void SpecificDate() {
		LocalDate specificDate = LocalDate.of(1992, Month.NOVEMBER, 30);
		System.out.println(specificDate);
	}
	
	/**
	 * 시간대(Time Zone) 개념이 추가된 클래스
	 * */
	public static void ZoneDate() {
	    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
	    System.out.printf("%s년%s월%s일", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
	 
	    System.out.println();
	    
	    ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("UTC"));
	    System.out.println(now2); // 2019-10-11T06:23:08.605Z[UTC]
	}
	
	/**
	 * 시간대(Zone Offset/Zone Region)에 대한 정보가 전혀 없는 API이다.
	 * 한국에서 1993-05-30였으면 미국으로 들고가도 1993-05-30이다.
	 * */
	public static void BirthDateTime () {
		LocalDate birthDate = LocalDate.of(1993, 5, 30);
		System.out.println(birthDate);
	}

}
