package com.apress.messaging.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrencyConversion {

    private String base;
    private String code;
    private float amount;
    private float total;

    public CurrencyConversion(){}

    public CurrencyConversion(String base, String code, float amount, float total) {
        super();
        this.base = base;
        this.code = code;
        this.amount = amount;
        this.total = total;
    }
}
