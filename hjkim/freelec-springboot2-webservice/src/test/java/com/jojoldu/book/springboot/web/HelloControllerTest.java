package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
/*
    JUnit에 내장된 실행자 외에 다른 실행자를 실행하며 SpringRunner라는 스프링 실행자를 사용
    스프링 부트 테스트와 JUnit 사이에 연결자 역할 수행
 */
@WebMvcTest()
/*
    Web에 집중할 수 있는 어노테이션
    선언할 경우, @Controller, @ControllerAdvice 등을 사용할 수 있음
    단, @Service, @Component, @Repository 등은 사용할 수 없음
 */
public class HelloControllerTest {

    @Autowired  // 스프링이 관리하는 빈을 주입받는다.
    private MockMvc mvc;    // 웹 MVC를 테스트할 때 사용하며 스프링 MVC 테스트의 시작점

    @Test
    public void testHello() throws Exception  {
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void testDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //JSON 응답값을 필드별로 검증
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}
