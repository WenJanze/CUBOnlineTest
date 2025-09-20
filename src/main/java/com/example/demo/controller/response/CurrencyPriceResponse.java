package com.example.demo.controller.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyPriceResponse {
    private String code;
    private String symbol;
    private BigDecimal rate;
    private String description;

    public static CurrencyPriceResponse valueOf(JsonNode node) {
        CurrencyPriceResponse currencyPriceResponse = new CurrencyPriceResponse();
        currencyPriceResponse.setCode(node.path("code").asText(null));
        currencyPriceResponse.setSymbol(node.path("symbol").asText(null));
        BigDecimal rate = new BigDecimal(node.path("rate_float").asText(null));
        currencyPriceResponse.setRate(rate);
        currencyPriceResponse.setDescription(node.path("description").asText(null));
        return currencyPriceResponse;
    }
}
