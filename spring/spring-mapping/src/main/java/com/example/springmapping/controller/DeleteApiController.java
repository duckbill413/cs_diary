package com.example.springmapping.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
public class DeleteApiController {

    @DeleteMapping("/delete1/{id}")
    public void delete(@PathVariable(name="id") String userId, @RequestParam String account){
        // MEMO: delete : 200 OK를 RETURN
        System.out.println(userId);
        System.out.println(account);
    }
}
