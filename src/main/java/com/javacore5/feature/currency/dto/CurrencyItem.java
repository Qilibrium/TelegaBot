package com.javacore5.feature.currency.dto;

import lombok.Data;

@Data
public class CurrencyItem {
    private Currency ccy;
    private  Currency base_ccy;
    private float buy;
    private float sale;

}
