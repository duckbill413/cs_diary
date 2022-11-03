package com.example.springswagger.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    @ApiModelProperty(value = "필드 값", example = "예시", required = true)
    private String name;

    @ApiModelProperty(value = "필드 값", example = "예시")
    private int age;
}
