package com.example.first_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
@SpringBootApplication

@SpringBootConfiguration
-> @Configuration + Spring Boot 특성 활용 가능
-> 보통 main 클래스에서만 사용
@EnableAutoConfiguration
-> Spring 자동 설정(but 수동 설정이 우선 적용)
@ComponentScan
-> 클래스들을 자동으로 Spring Bean(Spring 컨테이너가 관리하는 객체)로 등록
-> @Component, @Service, @Repository, @Controller, @RestController가 붙은 클래스 자동 감지
*/


@SpringBootApplication
public class FirstStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstStudyApplication.class, args);
        /*
        1. SpringApplication 객체 생성
        2. Application 유형 결정(웹/비웹)
        3. Context(Bean 관리) 생성
        4. ApplicationEvent, ApplicationListener 처리
        5. 환경 초기화
        6. 패키지 스캔(@ComponentScan 사용), Bean 클래스를 찾아서 Context에 등록
        7. 내장된 웹 서버(Tomcat) 시작
        8. 명령어 라인의 인자 파싱(데이터를 원하는 형태로 가공) -> 실행에 반영
        9. CommandLineRunner, ApplicationRunner 실행 << 더 공부!!!!!!!!!!!!!!!!!!
        */
	}

}
// Spring Framework 에 대해서 더 공부하기...
