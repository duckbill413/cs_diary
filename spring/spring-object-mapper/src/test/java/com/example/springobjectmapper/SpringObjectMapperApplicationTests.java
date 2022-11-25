package com.example.springobjectmapper;

import com.example.springobjectmapper.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringObjectMapperApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
        System.out.println("hello-----------");

        /* INFO: Json -> Object
        *  Object -> Json

        controller req json(text) -> object
        response object -> json(text)
         */

        var objectMapper = new ObjectMapper();

        // MEMO: object -> text objectMapper가 get method 활용
        // TIP: objectMapper가 get method를 참고하므로 다른 method이름 앞에 get을 붙이면 안됨
        var user = new User("duckbill", 27, "010-1234-1234");
        var text = objectMapper.writeValueAsString(user);
        System.out.println(text);

        // MEMO: text -> object 기본생성자로 초기화 필요
        var objectUser = objectMapper.readValue(text, User.class);
        System.out.println(objectUser);
    }

}
