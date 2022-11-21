package com.example.springioc;

import com.example.springioc.ioc.ApplicationContextProvider;
import com.example.springioc.ioc.Base64Encoder;
import com.example.springioc.ioc.Encoder;
import com.example.springioc.ioc.UrlEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;

@SpringBootApplication
public class SpringIocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIocApplication.class, args);

        ApplicationContext context = ApplicationContextProvider.getContext();

//        Encoder 주입 방식
//        Base64Encoder base64Encoder = context.getBean(Base64Encoder.class);
//        UrlEncoder urlEncoder = context.getBean(UrlEncoder.class);
//        Encoder encoder = new Encoder(base64Encoder);
//        encoder.encode(msg);

        Encoder encoder = context.getBean("base64Encode", Encoder.class);
        String url = "www.naver.com/books/it?page=1&size=123&name=duckbillLvr";
        String result = encoder.encode(url);
        System.out.println(result);
    }
}

// 여러가지 Bean 생성 => Configuration
@Configuration
class AppConfig{
    @Bean("base64Encode")
    public Encoder encoder(Base64Encoder base64Encoder){
        return new Encoder(base64Encoder);
    }
    @Bean("urlEncode")
    public Encoder encoder(UrlEncoder urlEncoder){
        return new Encoder(urlEncoder);
    }
}
