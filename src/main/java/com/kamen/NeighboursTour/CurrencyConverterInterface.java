package com.kamen.NeighboursTour;

public interface CurrencyConverterInterface {

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) throws MoneyException;
}
