package com.example.batch.part3.ItemTemplate;

import lombok.Getter;

/**
 * author        : duckbill413
 * date          : 2023-01-24
 * description   :
 **/
@Getter
public class Person {
    private int id;
    private String name;
    private String age;
    private String address;

    public Person(int id, String name, String age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
