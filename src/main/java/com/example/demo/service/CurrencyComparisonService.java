package com.example.demo.service;

import com.example.demo.controller.ResponseEnum;
import com.example.demo.controller.request.CurrencyComparisonUpdateRequest;
import com.example.demo.controller.response.CurrencyComparisonResponse;
import com.example.demo.controller.response.CurrencyPriceApiResponse;
import com.example.demo.controller.response.CurrencyPriceResponse;
import com.example.demo.database.CurrencyComparisonEntity;
import com.example.demo.service.cache.CurrencyComparisonCacheService;
import com.example.demo.service.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CurrencyComparisonService {

    @Autowired
    private CurrencyComparisonCacheService currencyComparisonCacheService;



    public List<CurrencyComparisonResponse> getCurrencyComparisons() {
        List<CurrencyComparisonEntity> responses = currencyComparisonCacheService.findAll();
        return responses.stream().map(CurrencyComparisonResponse::valueOf).collect(Collectors.toList());
    }

    public CurrencyComparisonResponse addCurrencyComparison(CurrencyComparisonUpdateRequest request) {
        if (request.getId() != null) {
            throw new BusinessException(ResponseEnum.INSERT_NOT_HAVE_ID);
        }
        if (currencyComparisonCacheService.existComparison(request.getCurrency())) {
            throw new BusinessException(ResponseEnum.EXIST_CURRENCY);
        }
        CurrencyComparisonEntity entity = new CurrencyComparisonEntity();
        setComparison(entity, request);
        CurrencyComparisonResponse response = CurrencyComparisonResponse.valueOf(currencyComparisonCacheService.save(entity));
        log.debug("response:{}", response);
        return response;
    }

    public CurrencyComparisonResponse updateCurrencyComparison(CurrencyComparisonUpdateRequest request) {
        CurrencyComparisonEntity entity = findCurrencyComparisonById(request.getId());
        setComparison(entity, request);
        return CurrencyComparisonResponse.valueOf(currencyComparisonCacheService.save(entity));
    }

    private void setComparison(CurrencyComparisonEntity entity, CurrencyComparisonUpdateRequest request) {
        if (request.getCurrency() != null) entity.setCurrency(request.getCurrency());
        if (request.getChinaName() != null) entity.setChinaName(request.getChinaName());
        if (request.getSymbol() != null) entity.setSymbol(request.getSymbol());
    }



    private CurrencyComparisonEntity findCurrencyComparisonById(Long id) {
        if (id == null) {
            throw new BusinessException(ResponseEnum.HAVE_ID);
        }
        CurrencyComparisonEntity entity = currencyComparisonCacheService.findById(id).orElse(null);
        if (entity == null) {
            throw new BusinessException(ResponseEnum.NOT_FIND);
        }
        return entity;
    }

    public void delCurrencyComparison(Long id) {
        CurrencyComparisonEntity entity = findCurrencyComparisonById(id);
        currencyComparisonCacheService.delete(entity);
    }
}
