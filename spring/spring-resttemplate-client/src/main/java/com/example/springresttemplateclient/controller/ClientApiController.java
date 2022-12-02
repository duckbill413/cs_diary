package com.example.springresttemplateclient.controller;

import com.example.springresttemplateclient.dto.Req;
import com.example.springresttemplateclient.dto.UserResponse;
import com.example.springresttemplateclient.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientApiController {
    private final RestTemplateService restTemplateService;
    @GetMapping("/hello/object")
    public String getHelloObject(){
        return restTemplateService.helloObject();
    }

    @GetMapping("/hello/entity")
    public String getHelloEntity(){
        return restTemplateService.helloEntity();
    }

    @GetMapping("/hello/entity/user")
    public UserResponse getUser(){
        return restTemplateService.helloUser();
    }

    @GetMapping("/hello/entity/user/params")
    public UserResponse getUserParams(){
        return restTemplateService.helloUserParams();
    }

    @GetMapping("/hello/user/post")
    public UserResponse getUserPost(){
        return restTemplateService.userPost();
    }

    @GetMapping("/exchange")
    public UserResponse getUserExchange(){
        return restTemplateService.exchange();
    }

    @GetMapping("/exchange/generic")
    public Req<UserResponse> getGenericExchange(){
        return restTemplateService.genericExchange();
    }
}
