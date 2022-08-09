package com.example.utils;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;
import org.springframework.util.StringUtils;

import java.util.Locale;

public class AmountTranslate {

    static String COUNTRY_CODE = "IN";
    static String LANG = "en";
    static String FRACTION_UNIT_NAME = "Rupees";

    public static String translate(String amount) {
        StringBuilder result = new StringBuilder();

        Locale locale = new Locale(LANG, COUNTRY_CODE);
        Currency crncy = Currency.getInstance(locale);

        String[] inputArr = StringUtils.split(new BigDecimal(amount).abs().toString(), ".");
        RuleBasedNumberFormat rule = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);

        int i = 0;
        if (inputArr == null) {
            return "ERROR";
        }
        for (String input : inputArr) {
            CurrencyAmount crncyAmt = new CurrencyAmount(new BigDecimal(input), crncy);
            if (i++ == 0) {
                result.append(rule.format(crncyAmt)).append(" ").append(crncy.getDisplayName()).append(" and ");
            } else {
                result.append(rule.format(crncyAmt)).append(" ").append(FRACTION_UNIT_NAME).append(" ");
            }
        }
        return result.toString();
    }
}
