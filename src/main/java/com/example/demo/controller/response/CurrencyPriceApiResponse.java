package com.example.demo.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyPriceApiResponse {
    private String time;
    private List<CurrencyPriceResponse> bpis;
}
