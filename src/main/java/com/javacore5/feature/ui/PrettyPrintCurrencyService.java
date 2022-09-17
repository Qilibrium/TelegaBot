package com.javacore5.feature.ui;

import com.javacore5.feature.currency.dto.Currency;

public class PrettyPrintCurrencyService {
    public String convert(double rate, Currency currency) {
        String template = "Course ${currency} => UAH = ${rate}";

        //Округления вывода числа
        //float roundedRate = Math.round(rate*100)/100.f;

        return template
                .replace("${currency}", currency.name())
                .replace("${rate}", Double.toString(rate));
        //.replace("${rate}", roundRate + "");
    }
}
