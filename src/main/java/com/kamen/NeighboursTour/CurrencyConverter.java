package com.kamen.NeighboursTour;

import com.ritaja.xchangerate.api.CurrencyConverterBuilder;
import com.ritaja.xchangerate.api.CurrencyNotSupportedException;
import com.ritaja.xchangerate.endpoint.EndpointException;
import com.ritaja.xchangerate.service.ServiceException;
import com.ritaja.xchangerate.storage.StorageException;
import com.ritaja.xchangerate.util.Currency;
import com.ritaja.xchangerate.util.Strategy;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConverter implements CurrencyConverterInterface {

    private final String LAYER_FILE_STORE_ACCESS_TOKEN = "2ac42034b6896e69a44816d0dd160d6a";

    private com.ritaja.xchangerate.api.CurrencyConverter converter = new CurrencyConverterBuilder()
				.strategy(Strategy.CURRENCY_LAYER_FILESTORE)
				.accessKey(LAYER_FILE_STORE_ACCESS_TOKEN)
				.buildConverter();

    public CurrencyConverter() {
        converter.setRefreshRateSeconds(86400);
    }

    @Override
    public double convertCurrency(double money, String fromCurrency, String toCurrency) throws MoneyException {
        try {
            BigDecimal moneyAmount = converter.convertCurrency(new BigDecimal(money), Currency.get(fromCurrency), Currency.get(toCurrency));

            return moneyAmount.doubleValue();
        } catch (CurrencyNotSupportedException | JSONException | StorageException | EndpointException | ServiceException e) {
            throw new MoneyException(e);
        }
    }
}
