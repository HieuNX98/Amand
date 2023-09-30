package com.amand.Utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Component
public class GetUtils {

    public static String formatMoney(Double input) {
        if (input == null) return "";

        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        return numberFormat.format(input);
    }
}
