package com.example.demo.controller.response;

import com.example.demo.database.CurrencyComparisonEntity;
import lombok.Data;

@Data
public class CurrencyComparisonResponse {
    private Long id;
    private String currency;
    private String symbol;
    private String chinaName;

    public static CurrencyComparisonResponse valueOf(CurrencyComparisonEntity entity) {
        CurrencyComparisonResponse currencyComparisonResponse = new CurrencyComparisonResponse();
        currencyComparisonResponse.setCurrency(entity.getCurrency());
        currencyComparisonResponse.setSymbol(entity.getSymbol());
        currencyComparisonResponse.setChinaName(entity.getChinaName());
        currencyComparisonResponse.setId(entity.getId());
        return currencyComparisonResponse;
    }
}
