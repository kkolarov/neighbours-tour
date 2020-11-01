package com.kamen.NeighboursTour;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Currencies {

    private Map<String, String> codes = new HashMap<>();

    public String getCurrency(String country) {
        return codes.get(country);
    }

    @Bean
    public Currencies loadCurrencies() throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getCsv())))) {
            CsvToBean<CurrencyCountry> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CurrencyCountry.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CurrencyCountry> currenciesCountries = csvToBean.parse();
            currenciesCountries.size();

            codes = new HashMap<>();

            for (CurrencyCountry currencyCountry: currenciesCountries) {
                codes.put(currencyCountry.getCountryCode(), currencyCountry.getCode());
            }
        }

        return new Currencies();
    }

    public File getCsv() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:countries-currencies.csv");
    }
}
