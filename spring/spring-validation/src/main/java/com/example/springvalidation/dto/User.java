package com.example.springvalidation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotBlank
    private String name;

    @Min(value = 0)
    @Max(value = 90)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
    message = "핸드폰 번호의 양식과 맞지 않습니다. xxx-xxx(x)-xxxx")
    private String phoneNumber;
}
