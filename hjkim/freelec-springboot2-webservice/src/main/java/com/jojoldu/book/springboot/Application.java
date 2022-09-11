package com.jojoldu.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication // 스프링 부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정 (이 위치부터 설정을 읽음. 즉, 프로젝트의 최상단에 위치해 있어야함)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행 -> 서버에 톰캣 설치 필요X, 스프링 부트로 만들어진 Jar 파일로 실행
    }
}
