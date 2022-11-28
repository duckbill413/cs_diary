package com.example.springvalidation.dto;

import com.example.springvalidation.annotation.YearMonth;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @YearMonth // MEMO: Custom Annotation
    private String reqYearMonth;

    @Valid // MEMO: Validation 적용하기 위해서는 하위 클래스에 @Valid 적용이 필요
    private List<Car> cars;

    /* FIXME
    @AssertTrue(message = "yyyyMM 형식에 맞지 않습니다.") // MEMO: Boolean Method 는 앞에 is가 붙어야 한다.
    public Boolean isReqYearMonthValidation() {
        try {
            LocalDate localDate = LocalDate.parse(this.reqYearMonth+"01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
     */
}
