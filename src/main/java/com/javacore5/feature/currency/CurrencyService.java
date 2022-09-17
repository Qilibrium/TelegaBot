package com.javacore5.feature.currency;

import com.javacore5.feature.currency.dto.Currency;

public interface CurrencyService {
    double getRate(Currency currency);
}
