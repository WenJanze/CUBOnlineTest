package com.example.demo.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyComparisonUpdateRequest {

    private Long id;
    private String currency;
    private String symbol;
    private String chinaName;
}
