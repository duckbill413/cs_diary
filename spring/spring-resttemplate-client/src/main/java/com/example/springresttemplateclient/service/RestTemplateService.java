package com.example.springresttemplateclient.service;

import com.example.springresttemplateclient.dto.Req;
import com.example.springresttemplateclient.dto.UserRequest;
import com.example.springresttemplateclient.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

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

    public UserResponse exchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}/exchange")
                .encode()
                .build()
                .expand("100", "duckbill")
                .toUri();

        log.info("Uri : {}", uri);
        UserRequest userRequest = new UserRequest("duckbill", 30);

        RequestEntity<UserRequest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth", "abcd1234") // Header 추가 여러개 추가 가능
                .body(userRequest);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.exchange(requestEntity, UserResponse.class);

        return result.getBody();
    }

    public Req<UserResponse> genericExchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}/exchange/generic")
                .encode(Charset.forName("UTF-8"))
                .build()
                .expand("100", "duckbill")
                .toUri();

        log.info("Uri : {}", uri);

        Req<UserRequest> req = new Req();
        req.setHeader(new Req.Header());
        UserRequest userRequest = new UserRequest("duckbill", 30);
        req.setResBody(userRequest);

        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth", "abcd1234") // Header 추가 여러개 추가 가능
                .body(req);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Req<UserResponse>> result = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {
        }); // 생략 가능

        return result.getBody();
    }
}
