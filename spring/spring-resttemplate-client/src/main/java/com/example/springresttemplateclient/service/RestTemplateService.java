package com.example.springresttemplateclient.service;

import com.example.springresttemplateclient.dto.UserRequest;
import com.example.springresttemplateclient.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Slf4j
@Service
public class RestTemplateService {
    // http://localhost/api/server/hello
    // response
    public String helloObject(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .encode()
                .build()
                .toUri();
        log.info("Uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public String helloEntity(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .encode()
                .build()
                .toUri();
        log.info("Uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        log.info("status code: {}", result.getStatusCode());
        log.info("body: {}", result.getBody());


        return result.getBody();
    }

    public UserResponse helloUser(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello/user")
                .encode()
                .build()
                .toUri();
        log.info("Uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        log.info("status code: {}", result.getStatusCode());
        log.info("body: {}", result.getBody());


        return result.getBody();
    }
    public UserResponse helloUserParams(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello/user/param")
                .queryParam("name", "duckbill")
                .queryParam("age", 9999)
                .encode()
                .build()
                .toUri();
        log.info("Uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        log.info("status code: {}", result.getStatusCode());
        log.info("body: {}", result.getBody());


        return result.getBody();
    }

    public UserResponse userPost(){
        // http://localhost:9090/api/server/user/{userId}/name/{userName}
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand("100", "duckbill")
                .toUri();

        log.info("Uri : {}", uri);
        UserRequest userRequest = new UserRequest("duckbill", 30);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.postForEntity(uri, userRequest, UserResponse.class);

        log.info("status code: {}", result.getStatusCode());
        log.info("headers: {}", result.getHeaders());
        log.info("body: {}", result.getBody());

        return result.getBody();
    }
}
