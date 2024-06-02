package wh.duckbill.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // Feign Client 사용을 위한 어노테이션
@SpringBootApplication
public class SpringOpenfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOpenfeignApplication.class, args);
    }

}
