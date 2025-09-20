package com.example.demo.service;

import com.example.demo.controller.response.CurrencyPriceApiResponse;
import com.example.demo.controller.response.CurrencyPriceResponse;
import com.example.demo.database.CurrencyComparisonEntity;
import com.example.demo.service.cache.CurrencyComparisonCacheService;
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
public class CurrencyPriceService {
    @Autowired
    private CurrencyComparisonCacheService currencyComparisonCacheService;

    private final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrencyPriceApiResponse getCurrencyPrice() {
        try {
            CurrencyPriceApiResponse response = new CurrencyPriceApiResponse();
            DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            DateTimeFormatter updateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);

            JsonNode jsonNode = getCoinDesk();
            String updatedTimeStr = jsonNode.get("time").path("updated").asText(null);
            if (updatedTimeStr != null) {
                ZonedDateTime updatedTime = ZonedDateTime.parse(updatedTimeStr, updateFormatter);
                response.setTime(updatedTime.format(dataTimeFormatter));
            }
            Map<String, CurrencyComparisonEntity> currencyComparisonMap = currencyComparisonCacheService.findAll()
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    CurrencyComparisonEntity::getCurrency,
                                    Function.identity())
                    );

            JsonNode bpiNode = jsonNode.path("bpi");
            List<CurrencyPriceResponse> currencyPriceResponses = new ArrayList<>();
            for (JsonNode node : bpiNode) {
                String code = node.path("code").asText(null);
                CurrencyPriceResponse cpr = CurrencyPriceResponse.valueOf(node);
                if (code != null) {
                    CurrencyComparisonEntity currencyComparisonEntity = currencyComparisonMap.get(code);
                    if (currencyComparisonEntity != null) {
                        cpr.setDescription(currencyComparisonEntity.getChinaName());
                    }
                }
                currencyPriceResponses.add(cpr);
            }
            response.setBpis(currencyPriceResponses);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public JsonNode getCoinDesk() throws JsonProcessingException {
        HttpGet request = new HttpGet("https://api.coindesk.com/v1/bpi/currentprice.json");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return objectMapper.readTree(responseBody);
            }
        } catch (IOException e) {
            return objectMapper.readTree(DemoMockingData.data);
        }
        throw new RuntimeException("Status code not 200");
    }

}
