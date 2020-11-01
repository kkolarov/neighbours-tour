package com.kamen.NeighboursTour;

import java.util.HashMap;
import java.util.Map;

public class LocalBudget {

    private Map<String, Double> values = new HashMap<>();

    public void put(String country, double localMoney) {
        values.put(country, localMoney);
    }

    public Map<String, Double> getValues() {
        return values;
    }
}
