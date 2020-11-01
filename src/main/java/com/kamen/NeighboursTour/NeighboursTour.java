package com.kamen.NeighboursTour;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class NeighboursTour {

    @Min(value = 0L)
    @Max(value = 1000000L)
    private double tourTotalBudget;

    @Min(value = 0L)
    @Max(value = 1000000L)
    private double tourCostPerCountry;

    @NotBlank
    private String tourStartingCountry;

    @NotBlank
    private String tourOriginalCurrency;

    public NeighboursTour() {
    }

    public double getTourTotalBudget() {
        return tourTotalBudget;
    }

    public void setTourTotalBudget(double tourTotalBudget) {
        this.tourTotalBudget = tourTotalBudget;
    }

    public double getTourCostPerCountry() {
        return tourCostPerCountry;
    }

    public void setTourCostPerCountry(double tourCostPerCountry) {
        this.tourCostPerCountry = tourCostPerCountry;
    }

    public String getTourStartingCountry() {
        return tourStartingCountry;
    }

    public void setTourStartingCountry(String tourStartingCountry) {
        this.tourStartingCountry = tourStartingCountry;
    }

    public String getTourOriginalCurrency() {
        return tourOriginalCurrency;
    }

    public void setTourOriginalCurrency(String tourOriginalCurrency) {
        this.tourOriginalCurrency = tourOriginalCurrency;
    }
}
