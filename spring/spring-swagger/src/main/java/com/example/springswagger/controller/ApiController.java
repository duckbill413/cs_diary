package com.example.springswagger.controller;

import com.example.springswagger.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "컨트롤러 이름")
@RestController
@RequestMapping("/api")
public class ApiController {
    @Operation(summary = "요약", description = "설명")
    @ApiResponse(code = 200, message = "ok")
    @GetMapping("/swagger")
    public ResponseDto swagger(){
        return new ResponseDto("duckbill", 20);
    }
}
