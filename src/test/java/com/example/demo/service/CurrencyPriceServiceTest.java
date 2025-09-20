package com.example.demo.service;


import com.example.demo.controller.response.CurrencyPriceApiResponse;
import com.example.demo.controller.response.CurrencyPriceResponse;
import com.example.demo.database.CurrencyComparisonEntity;
import com.example.demo.service.cache.CurrencyComparisonCacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CurrencyPriceServiceTest {
    @Mock
    private CurrencyComparisonCacheService currencyComparisonCacheService;

    @Spy
    @InjectMocks
    private CurrencyPriceService currencyPriceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String MOCK_COIN_DESK_JSON = DemoMockingData.data;

    private final List<CurrencyComparisonEntity> MOCK_CURRENCY_CACHE_LIST = List.of(
            new CurrencyComparisonEntity("USD", "美金"),
            new CurrencyComparisonEntity("GBP", "英鎊"),
            new CurrencyComparisonEntity("EUR", "歐元")
    );
    @Test
    public void getCurrencyPriceTest() throws JsonProcessingException {
        doReturn(objectMapper.readTree(MOCK_COIN_DESK_JSON)).when(currencyPriceService).getCoinDesk();

        doReturn(MOCK_CURRENCY_CACHE_LIST).when(currencyComparisonCacheService).findAll();
        CurrencyPriceApiResponse response = currencyPriceService.getCurrencyPrice();

        // 3. 驗證 (Assert)
        assertNotNull(response);

        // 驗證時間是否被正確格式化
        assertEquals("2022/08/03 20:25:00", response.getTime());

        // 驗證幣別清單大小
        assertEquals(3, response.getBpis().size());

        // 驗證第一個項目（USD）
        CurrencyPriceResponse usdPrice = response.getBpis().get(0);
        assertEquals("USD", usdPrice.getCode());
        assertEquals("美金", usdPrice.getDescription());
    }

}
