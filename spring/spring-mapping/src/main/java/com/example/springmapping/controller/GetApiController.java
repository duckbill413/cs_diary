package com.example.springmapping.controller;

import com.example.springmapping.dto.GetRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/get")
public class GetApiController {
    @GetMapping(path = "/hello")
    public String getHello(){
        return "get Hello";
    }

    @RequestMapping(path = "/hi", method = RequestMethod.GET)
    public String getHi(){
        return "get Hi";
    }
    @GetMapping("/path-variable/{name}")
    public String pathVariable(@PathVariable(name = "name") String pathName){
        return pathName;
    }

    @GetMapping(path = "/query-param")
    public String queryParams(@RequestParam Map<String, String> queryParam){
        StringBuilder sb = new StringBuilder();

        queryParam.entrySet().forEach(entry->{
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();

            sb.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        });

        return sb.toString();
    }
    
    @GetMapping(path = "query-param02")
    public String queryParam02(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int age
    ){
        System.out.println(name);
        return name + "\t" + email + "\t" + age;
    }

    @GetMapping(path = "query-param03")
    public String queryParam02(GetRequest getRequest){
        System.out.println(getRequest.getName());
        System.out.println(getRequest.getEmail());
        System.out.println(getRequest.getAge());
        return getRequest.toString();
    }

}
