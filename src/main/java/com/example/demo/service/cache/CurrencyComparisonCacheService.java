package com.example.demo.service.cache;


import com.example.demo.database.CurrencyComparisonEntity;
import com.example.demo.database.CurrencyComparisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyComparisonCacheService {
    @Autowired
    private CurrencyComparisonRepository currencyComparisonRepository;

    public List<CurrencyComparisonEntity> findAll() {
        //TODO預留緩存方法
        return currencyComparisonRepository.findAll();
    }

    public CurrencyComparisonEntity save(CurrencyComparisonEntity entity) {
        //TODO預留緩存方法
        return currencyComparisonRepository.save(entity);
    }

    public void delete(CurrencyComparisonEntity entity) {
        //TODO預留緩存方法
        currencyComparisonRepository.delete(entity);
    }

    public Optional<CurrencyComparisonEntity> findById(Long id) {
        //TODO預留緩存方法
        return currencyComparisonRepository.findById(id);
    }

    public Boolean existComparison(String currency) {
        //TODO預留緩存方法
        return currencyComparisonRepository.existsCurrencyComparisonEntityByCurrency(currency);
    }
}
