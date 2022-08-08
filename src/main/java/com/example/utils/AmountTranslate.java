package com.example.utils;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;

public class AmountTranslate {

	static String COUNTRY_CODE = "IN";
	static String LANG = "en";
	static String FRACTION_UNIT_NAME = "Rupees";

	public static String translate(String amount) {
		StringBuffer result = new StringBuffer();

		Locale locale = new Locale(LANG, COUNTRY_CODE);
		Currency crncy = Currency.getInstance(locale);

		String inputArr[] = StringUtils.split(new BigDecimal(amount).abs().toString(), ".");
		RuleBasedNumberFormat rule = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);

		int i = 0;
		for (String input : inputArr) {
			CurrencyAmount crncyAmt = new CurrencyAmount(new BigDecimal(input), crncy);
			if (i++ == 0) {
				result.append(rule.format(crncyAmt)).append(" " + crncy.getDisplayName() + " and ");
			} else {
				result.append(rule.format(crncyAmt)).append(" " + FRACTION_UNIT_NAME + " ");
			}
		}
		return result.toString();
	}
}
