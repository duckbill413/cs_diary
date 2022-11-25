package com.example.springmapping.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetRequest {
    private String name;
    private String email;
    private int age;
}
