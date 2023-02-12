package com.example.batch.part5;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * author        : duckbill413
 * date          : 2023-02-12
 * description   :
 **/
@Getter
@Builder
public class OrderStatistics {
    private String amount;
    private LocalDate date;
}
