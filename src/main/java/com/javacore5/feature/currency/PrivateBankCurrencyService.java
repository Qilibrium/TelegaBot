package com.javacore5.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javacore5.feature.currency.dto.Currency;
import com.javacore5.feature.currency.dto.CurrencyItem;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivateBankCurrencyService implements CurrencyService{
    @Override
    public double getRate(Currency currency) {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        //Get JSON
        String json;
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can not to Privat API");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItem.class)
                .getType();

        Gson gson = new Gson();
        List<CurrencyItem> currencyItems = new Gson()
                .fromJson(json, typeToken);



        //Find UAH/USD
           Float uahUsd = currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(CurrencyItem::getBuy)
                .findFirst()
                .orElseThrow();

        return uahUsd;


    }
}
