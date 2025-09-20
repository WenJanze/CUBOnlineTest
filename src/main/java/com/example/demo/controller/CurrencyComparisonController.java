package com.example.demo.controller;

import com.example.demo.controller.request.CurrencyComparisonUpdateRequest;
import com.example.demo.controller.response.CurrencyComparisonResponse;
import com.example.demo.controller.response.CurrencyPriceApiResponse;
import com.example.demo.service.CurrencyComparisonService;
import com.example.demo.service.CurrencyPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/currency")
public class CurrencyComparisonController {

    @Autowired
    private CurrencyComparisonService currencyComparisonService;

    @Autowired
    private CurrencyPriceService currencyPriceService;

    @GetMapping("/comparison")
    public CommonResponse<CurrencyPriceApiResponse> currencyComparison() {
        CurrencyPriceApiResponse response = currencyPriceService.getCurrencyPrice();
        return CommonResponse.valueOf(true, response, ResponseEnum.SUCCESS);
    }

    @GetMapping("/getComparisons")
    public CommonResponse<List<CurrencyComparisonResponse>> getCurrencyComparison() {
        List<CurrencyComparisonResponse> response = currencyComparisonService.getCurrencyComparisons();
        return CommonResponse.valueOf(true, response, ResponseEnum.SUCCESS);
    }

    @PostMapping("/addComparison")
    public CommonResponse<CurrencyComparisonResponse> addCurrencyComparison(@RequestBody CurrencyComparisonUpdateRequest request) {
        CurrencyComparisonResponse response = currencyComparisonService.addCurrencyComparison(request);
        return CommonResponse.valueOf(true, response, ResponseEnum.SUCCESS);
    }

    @PutMapping("/updateComparison")
    public CommonResponse<CurrencyComparisonResponse> updateCurrencyComparison(@RequestBody CurrencyComparisonUpdateRequest request) {
        CurrencyComparisonResponse response = currencyComparisonService.updateCurrencyComparison(request);
        return CommonResponse.valueOf(true, response, ResponseEnum.SUCCESS);
    }

    @DeleteMapping("/delComparison")
    public CommonResponse<Void> deleteCurrencyComparison(@RequestParam Long id) {
        currencyComparisonService.delCurrencyComparison(id);
        return CommonResponse.valueOf(true, null, ResponseEnum.SUCCESS);
    }


}



