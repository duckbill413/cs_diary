package com.example.springvalidation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Car {
    @NotBlank
    private String name;
    @NotBlank
    private String carNumber;
    @NotBlank
    @JsonProperty(value = "TYPE")
    private String type;
}
