package com.example.jparepository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String city; // 시
    private String district; // 구
    @Column(name = "address_detail")
    private String detail; // 상세주소
    private String zipCode; // 우편번호
}
