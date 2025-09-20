package com.example.demo.service;

import com.example.demo.controller.request.CurrencyComparisonUpdateRequest;
import com.example.demo.controller.response.CurrencyComparisonResponse;
import com.example.demo.database.CurrencyComparisonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class CurrencyComparisonServiceTest {


    @Autowired
    private CurrencyComparisonService currencyComparisonService;
    @Autowired
    private CurrencyComparisonRepository currencyComparisonRepository;


    private CurrencyComparisonUpdateRequest createCurrencyComparisonUpdateRequest(String currency, String chName, String symbol) {
        CurrencyComparisonUpdateRequest response = new CurrencyComparisonUpdateRequest();
        response.setCurrency(currency);
        response.setChinaName(chName);
        response.setSymbol(symbol);
        return response;
    }

    @Test
    public void addCurrencyComparison() {
        CurrencyComparisonUpdateRequest addEntity = createCurrencyComparisonUpdateRequest("USD", "美金", "$");
        CurrencyComparisonResponse result = currencyComparisonService.addCurrencyComparison(addEntity);
        assert (result.getCurrency().equals("USD"));
    }


    @Test
    public void updateCurrencyComparison() {
        CurrencyComparisonUpdateRequest addRequest = createCurrencyComparisonUpdateRequest("USD", "美", "$");
        CurrencyComparisonResponse currencyComparisonResponse = currencyComparisonService.addCurrencyComparison(addRequest);
        CurrencyComparisonUpdateRequest request = new CurrencyComparisonUpdateRequest();
        request.setId(currencyComparisonResponse.getId());
        request.setChinaName("英鎊");
        CurrencyComparisonResponse result = currencyComparisonService.updateCurrencyComparison(request);
        assert (result.getChinaName().equals("英鎊"));
    }

    @Test
    public void deleteCurrencyComparison() {
        CurrencyComparisonUpdateRequest addRequest = createCurrencyComparisonUpdateRequest("VND", "越南盾", "d");
        CurrencyComparisonResponse currencyComparisonResponse = currencyComparisonService.addCurrencyComparison(addRequest);
        currencyComparisonService.delCurrencyComparison(currencyComparisonResponse.getId());
        assert (currencyComparisonRepository.findCurrencyComparisonEntityByCurrency("VND") == null);
    }

    @Test
    public void getCurrencyComparisonsTest() {
        CurrencyComparisonUpdateRequest addRequest = createCurrencyComparisonUpdateRequest("USD", "美金", "$");
        CurrencyComparisonResponse result1 = currencyComparisonService.addCurrencyComparison(addRequest);
        CurrencyComparisonUpdateRequest entity = createCurrencyComparisonUpdateRequest("GBP", "英鎊", "$");
        CurrencyComparisonResponse result2 = currencyComparisonService.addCurrencyComparison(entity);
        List<CurrencyComparisonResponse> responses = Arrays.asList(result1, result2);

        List<CurrencyComparisonResponse> result = currencyComparisonService.getCurrencyComparisons();
        log.info("getCurrencyComparisons result: {}", result);
        assert (result.equals(responses));
    }
}
