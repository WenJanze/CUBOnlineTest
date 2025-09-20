package com.example.demo.database;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "currency_comparison", indexes = {
        @Index(name = "idx_currency", columnList = "currency")
})
public class CurrencyComparisonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;
    private String symbol;
    private String chinaName;

    public CurrencyComparisonEntity(String currency, String chinaName) {
        this.currency = currency;
        this.chinaName = chinaName;
    }

    public CurrencyComparisonEntity() {
    }

}
