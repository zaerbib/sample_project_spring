package com.tuto.rate.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyExchange {

    public static final String BASE_CODE = "USD";
    private String base;
    private String date;
    private Rate[] rates;

    public CurrencyExchange(String base, String date, Rate[] rates){
        super();
        this.base = base;
        this.date = date;
        this.rates = rates;
    }
}
