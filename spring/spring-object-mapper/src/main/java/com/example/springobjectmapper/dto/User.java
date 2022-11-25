package com.example.springobjectmapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private int age;
    @JsonProperty("phone_number")
    private String phoneNumber;
    public User(){
        this.name = null;
        this.age = 0;
        this.phoneNumber = null;
    }
    public User defaultUser(){
        return new User("default", 0, "000-0000-0000");
    }
    public User(String name, int age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
}
