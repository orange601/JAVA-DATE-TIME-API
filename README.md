# JAVA-DATE-TIME-API
JAVA의 날짜와 시간 API

## 사용하기 불편해서 악평이 자자하다는 날짜, 시간 API ##
1. java.util.Date 
2. java.util.Calendar

### 문제점 ###
1. 불변 객체가 아니다.
    - Calendar 클래스에 set 메서드를 호출해서 날짜를 지정하고, 다시 같은 객체에 set(int,int) 메서드를 호출해서 수행한 날짜 연산 결과는 같은 인스턴스에 저장되었다. 
    - Date 클래스에도 값을 바꿀 수 있는 set 메서드가 존재한다. 이 때문에 Calendar 객체나 Date 객체가 여러 객체에서 공유되면 한 곳에서 바꾼 값이 다른 곳에 영향을 미치는 부작용이 생길 수 있다. 

2. 상수 필드 남용
    ````java
    calendar.add(Calendar.SECOND, 2);
    `````
    - 첫 번째 파라미터에 Calendar.JUNE과 같이, 전혀 엉뚱한 상수가 들어가도 이를 컴파일 시점에서 확인할 방법이 없다. 이 뿐만 아니라 Calendar 클래스에는 많은 int 상수가 쓰였는데, 이어서 설명할 월, 요일 지정 등에서도 많은 혼란을 유발한다.

3. 헷갈리는 월 지정
    - 1582년 10월 4일을 지정하는 코드는 다음과 같았다.
    ````java
    calendar.set(1582, Calendar.OCTOBER , 4);  
    ````
    - 월에 해당하는 Calendar.OCTOBER 값은 실제로는 '9'이다. JDK 1.0에서 Date 클래스는 1월을 0으로 표현했고, JDK 1.1부터 포함된 Calendar 클래스도 이러한 관례를 답습했다. 그래서 1582년 10월 4일을 표현하는 코드를 다음과 같이 쓰는 실수를 많은 개발자들이 반복하고 있다.

    #### 실수로 쓰기 쉬운 10월 지정 코드 ####
    ````java
    calendar.set(1582, 10 , 4);  // 0이 1월이기때문에 10은 11월을 의미한다.
    ````
    
4. 일관성 없는 요일 상수

-> 어디서는 일요일이 0, 어디서는 일요일이 1

5. Date와 Calendar 객체의 역할 분담

-> 다소 치명적인데 년/월/일 계산은 Date 클래스만으로는 부족해서 왔다갔다 하는 문제가 있다. 또한 Calendar객체를 생성하고 Date 객체를 생성하는 프로세스를 거치기 때문에 번거롭고 생성비용이 비싸다.

6. 기타 java.util.Date 하위 클래스의 문제



출처: https://jeong-pro.tistory.com/163 [기본기를 쌓는 정아마추어 코딩블로그]

## Java의 개선된 날짜, 시간 API ##
1. Joda-Time ( 오픈소스 라이브러리 )
2. JSR-310

## Joda-Time ##
1. 오픈소스 라이브러리
2. Spring 프레임워크에서도 Joda-Time을 기본으로 지원
    -  Spring-web-mvc 프레임워크는 사용자가 입력한 문자열을 원하는 객체로 변환할 때 Converter라는 인터페이스를 활용하는데, 클래스 패스에 Joda-Time이 포함되어 있으면 이 라이브러리의 객체를 변화하는 Converter 구현체를 자동으로 등록한다.
3. Hibernate 프레임워크에서도 Joda-Time을 쓸 수 있다.
    - Joda-time-hibernate 모듈(http://www.joda.org/joda-time-hibernate) 을 이용하면 데이터베이스에 저장된 TIMESTAMPE 같은 타입을 Date 클래스와 같은 JDK의 기본 클래스대신 Joda-Time의 클래스로 매핑할 수 있다.

## JSR-310 ##
- 2014년에 최종 배포되는 JDK 8에는 JSR-310이라는 표준 명세로 날짜와 시간에 대한 새로운 API가 추가되었다.
- Joda-Time에 가장 많은 영향을 받았고, 그 밖에 Time and Money 라이브러리나 ICU 등 여러 오픈소스 라이브러리를 참고했다고 한다.
- Spring 프레임워크 4.0에서는 JSR-310을 기본으로 지원
- 나노초까지 다룰 수 있다

##### 참고 #####
- https://d2.naver.com/helloworld/645609


## 사용방법
1. 회원가입
    - 회원가입을 한다.
2. 완료
    - 완료를 한다.
      + 더하고 싶으면 계속한다.
 <!-- 가로 640px 세로 450px 사이즈가 괜찮다 -->
![완료에관련이미지](https://user-images.githubusercontent.com/24876345/142789990-bdb42e5f-67e8-4971-844a-f0111af0b135.png)
