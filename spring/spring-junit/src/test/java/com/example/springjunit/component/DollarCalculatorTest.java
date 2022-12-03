package com.example.springjunit.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
public class DollarCalculatorTest {

    @MockBean
    private MarketApi marketApi;

    @Autowired
    private Calculator calculator;

    @Test
    @DisplayName("Dollar test")
    public void dollarCalculatorTest(){
        Mockito.when(marketApi.connect()).thenReturn(3000);

        int sum = calculator.sum(10, 5);
        int minus = calculator.minus(10, 10);
        Assertions.assertEquals(45000, sum);
        Assertions.assertEquals(0, minus);
    }
}
