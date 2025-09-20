package com.example.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyComparisonRepository extends JpaRepository<CurrencyComparisonEntity,Long>{

    Boolean existsCurrencyComparisonEntityByCurrency(String currency);

    CurrencyComparisonEntity findCurrencyComparisonEntityByCurrency(String currency);
}
