package com.example.springmapping.controller;

import com.example.springmapping.dto.PutRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/put")
public class PutApiController {
    @PutMapping("/put1")
    public PutRequest put(@RequestBody PutRequest putRequest){
        System.out.println(putRequest.toString());
        return putRequest;
    }

    @PutMapping("/put2/{id}")
    public PutRequest put2(@RequestBody PutRequest putRequest,
                           @PathVariable(name = "id") Long userId){
        System.out.println(userId);
        System.out.println(putRequest.toString());
        return putRequest;
    }
}
