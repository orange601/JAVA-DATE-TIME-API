# JAVA-DATE-TIME-API
JAVA의 날짜와 시간 API

## 사용하면 안되는 날짜-시간 API ##
1. java.util.Date 
2. java.util.Calendar

### 문제점 ###
1. **불변 객체가 아니다.**
    - Calendar 클래스에 set 메서드를 호출해서 날짜를 지정하고, 다시 같은 객체에 set(int,int) 메서드를 호출해서 수행한 날짜 연산 결과는 같은 인스턴스에 저장되었다. 
    - Date 클래스에도 값을 바꿀 수 있는 set 메서드가 존재한다. 이 때문에 Calendar 객체나 Date 객체가 여러 객체에서 공유되면 한 곳에서 바꾼 값이 다른 곳에 영향을 미치는 부작용이 생길 수 있다. 

2. **상수 필드 남용**
    ````java
    calendar.add(Calendar.SECOND, 2);
    `````
    - 첫 번째 파라미터에 Calendar.JUNE과 같이, 전혀 엉뚱한 상수가 들어가도 이를 컴파일 시점에서 확인할 방법이 없다. 이 뿐만 아니라 Calendar 클래스에는 많은 int 상수가 쓰였는데, 이어서 설명할 월, 요일 지정 등에서도 많은 혼란을 유발한다.

3. **헷갈리는 월 지정**
    - 1582년 10월 4일을 지정하는 코드는 다음과 같았다.
    ````java
    calendar.set(1582, Calendar.OCTOBER , 4);  
    ````
    - 월에 해당하는 Calendar.OCTOBER 값은 실제로는 '9'이다. JDK 1.0에서 Date 클래스는 1월을 0으로 표현했고, JDK 1.1부터 포함된 Calendar 클래스도 이러한 관례를 답습했다. 그래서 1582년 10월 4일을 표현하는 코드를 다음과 같이 쓰는 실수를 많은 개발자들이 반복하고 있다.

    - 실수로 쓰기 쉬운 10월 지정 코드
    ````java
    calendar.set(1582, 10 , 4);  // 0부터 1월이기때문에 10은 11월을 의미한다.
    ````
    
4. **일관성 없는 요일 상수**
    - Calendar.get(Calendar.DAY_OF_WEEK) 함수에서 반환한 요일은 int 값으로, 일요일이 1로 표현된다. 따라서 수요일은 4이고, 보통 Calendar.WEDNESDAY 상수와 비교해서 확인한다. 
    - calendar.getTime() 메서드로 Date 객체를 얻어와서 Date.getDay() 메서드로 요일을 구하면 일요일은 0, 수요일은 3이 된다. 두 개의 클래스 사이에 요일 지정값에 일관성이 없는 것이다.

5. **Date와 Calendar 객체의 역할 분담**
    - JDK 1.0 시절에는 Date 클래스가 날짜 연산을 지원하는 유일한 클래스였다. 
    - JDK 1.1 이후부터 Calendar 클래스가 포함되면서 날짜간의 연산, 국제화 지원 등은 Calendar 클래스에서 주로 담당하고 Date 클래스의 많은 기능이 사용되하지 않게(deprecated)되었다.
    - 특정 시간대의 날짜를 생성한다거나, 년/월/일 같은 날짜 단위의 계산은 Date 클래스만으로는 수행하기 어렵기 때문에 날짜 연산을 위해서 Calendar 객체를 생성하고, 다시 Calendar 객체에서 Date 객체를 생성한다. 최종 결과에는 불필요한 중간 객체를 생성해야 하는 셈인데, 쓰기에도 번거롭고, Calendar 클래스는 생성 비용이 비싼 편이기 때문에 비효율적이기도 하다.
    - 불편함을 덜기 위해 실무에서는 Date의 연산에 Apache commons Lang 라이브러리에 있는 DateUtils 클래스의 plusDays() 메서드나 plusMonth() 메서드 같은 메서드를 주로 활용한다. 그러나 DateUtils 클래스를 쓰더라도 중간 객체로 Calendar를 생성하는 것은 마찬가지다.
    - 날짜와 시간을 모두 저장하는 클래스의 이름이 'Date'라는 점도 다소 아쉽다. Calendar.getTime() 메서드도 Date 타입을 반환하는데 메서드 이름만 봐서는 반환 타입을 예측하기가 힘들다.

6. **오류에 둔감한 시간대 ID지정**
    -  시간대의 ID를 'Asia/Seoul'대신 'Seoul/Asia'로 잘못 지정한 코드다.
    ````java
    @Test
    public void shouldSetGmtWhenWrongTimeZoneId(){  
        TimeZone zone = TimeZone.getTimeZone("Seoul/Asia");
        assertThat(zone.getID()).isEqualTo("GMT");
    }
    ````
    -  코드는 오류가 발생하지 않고, 'GMT'가 ID인 시간대가 지정된 것처럼 테스트를 통과한다. 이런 특성 때문에 찾기 어려운 버그가 생길 수도 있다.

7. **java.util.Date 하위 클래스의 문제**
    - java.sql.Date 클래스는 상위 클래스인 java.util.Date 클래스와 이름이 같다. 

## Java의 개선된 날짜-시간 API 
1. Joda-Time ( 오픈소스 라이브러리 )
2. JSR-310 ( JAVA 표준 )

## Joda-Time ##
1. 오픈소스 라이브러리
2. JDK5 부터 지원
3. Spring 프레임워크에서도 Joda-Time을 기본으로 지원
    -  Spring-web-mvc 프레임워크는 사용자가 입력한 문자열을 원하는 객체로 변환할 때 Converter라는 인터페이스를 활용하는데, 클래스 패스에 Joda-Time이 포함되어 있으면 이 라이브러리의 객체를 변화하는 Converter 구현체를 자동으로 등록한다.
4. Hibernate 프레임워크에서도 Joda-Time을 쓸 수 있다.
    - Joda-time-hibernate 모듈(http://www.joda.org/joda-time-hibernate) 을 이용하면 데이터베이스에 저장된 TIMESTAMPE 같은 타입을 Date 클래스와 같은 JDK의 기본 클래스대신 Joda-Time의 클래스로 매핑할 수 있다.

## JSR-310 (JDK 8) ##
- 2014년에 최종 배포되는 **JDK8**에는 JSR-310이라는 표준 명세로 날짜와 시간에 대한 새로운 API가 추가되었다. ( java.time.* 패키지 )
- Joda-Time의 창시자인 Joda도 이 API를 만드는데 동참했다고 한다.
- 기존 클래스(java.util.Date, java.util.Calendar)를 대체하는 것을 목표로 2007년에 처음 제안된 명세였다.
- "JSR(Java Specification Requests)의 310번 째" 라고한다.
- 기존 Date, Calander와 달리 Thread Safe하고, 날짜 연산 관련된 편의 기능이 많고, TimeOffset/TimeZone 관련된 기능들도 있어서 글로벌 서비스에서도 적합하다.
- Spring 프레임워크 4.0에서는 JSR-310을 기본으로 지원한다.
- 나노초까지 다룰 수 있다.

### LocalTime/LocalDate/LocalDateTime ###
    - 시간대(Zone Offset/Zone Region)에 대한 정보가 전혀 없는 API이다.
    - 한국에서 2018-09-07T08:00:04였으면 미국으로 들고가도 2018-09-07T08:00:04이다.
이러한 경우는 생일 같은 경우 제일 적합하다.

### ZoneOffset ###
    - UTC 기준으로 시간(Time Offset)을 나타낸 것이라고 보면 된다.
    - 우리나라는 KST를 사용하는데 KST는 UTC보다 9시간이 빠르므로 UTC +09:00으로 표기한다.
    - ZoneOffset은 ZoneId의 자식 클래스이다.
    
    
## JPA-MYSQL ERROR ##
- java.time (JSR-310)를 별다른 처리 없이 JPA를 이용해서 MySQL에 저장하면 버전에 따라서 아래와 같은 에러가 날 수도 있다.
````sql
Caused by: com.mysql.jdbc.MsqlDataTruncation: Data truncation: Incorrect dateme value: '\xAC\xED\x00\x05sr\x0Djava.time.Ser\x95]\x84\xBA\x1B"H\xB2\x0C\0\x00xpw\x07\x03\x00\x00\x07\xE0\x05\x1Fx' for column 'start_date' at row 1
````
- http://homoefficio.github.io/2016/11/19/Spring-Data-JPA-%EC%97%90%EC%84%9C-Java8-Date-Time-JSR-310-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0/


##### 참고 #####
- https://d2.naver.com/helloworld/645609
