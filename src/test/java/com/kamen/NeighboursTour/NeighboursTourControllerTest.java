package com.kamen.NeighboursTour;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {NeighboursTourController.class, NeighboursTourManager.class, NeighboursFactory.class, Currencies.class, CurrencyConverter.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NeighboursTourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConverterInterface currencyConverter;

    private final String CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT = "/api/v1/tours/calculate_counties_maximum_visit";
    private final String CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT = "/api/v1/tours/calculate_local_budget";

    @Before
    public void beforeEachMethod() throws MoneyException {
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "TRY")).thenReturn(1023.75);
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "EUR")).thenReturn(105.00);
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "MKD")).thenReturn(6452.81);
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "SRD")).thenReturn(1730.52);
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "RON")).thenReturn(510.51);
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWhenTotalBudgetIsEmpty() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "0.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWhenTotalBudgetIsInDeficit() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "-50.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourTotalBudget").exists());
    }

    @Test
    public void testAngelVisitsNeighbourCountiesWhenTotalBudgetIsNotEnough() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "99.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelVisitsNeighbourCountiesWhenTotalBudgetIsEnough() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(2)));
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWithLargeBudget() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "10000000.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourTotalBudget").exists());
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWhenNeighboursNotExist() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "COUNTRY_DOES_NOT_EXIST")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelVisitsNeighbourCountiesWhenTripExpensesAreNegative() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "-100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourCostPerCountry").exists());
    }

    @Test
    public void testAngelVisitsNeighbourCountiesWhenTotalBudgetIsUnknown() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelVisitsNeighbourCountiesWhenCostPerCountryIsUnknown() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWithLargeCostPerCountry() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "100000000.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourCostPerCountry").exists());
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWhenStartingCountryIsUnknown() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourStartingCountry").exists());
    }

    @Test
    public void testAngelVisitsNeighbourCountriesWhenStartingCountryNotExists() throws Exception {
        mockMvc.perform(get(CALCULATE_COUNTRIES_MAXIMUM_VISIT_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourStartingCountry", "BG_TEST")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(0)));
    }

    @Test
    public void testAngelCalculatesLocalBudgetPerCountry() throws Exception {
        mockMvc.perform(get(CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "105.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TR", is(1023.75)))
                .andExpect(jsonPath("$.GR", is(105.00)))
                .andExpect(jsonPath("$.MK", is(6452.81)))
                .andExpect(jsonPath("$.SR", is(1730.52)))
                .andExpect(jsonPath("$.RO", is(510.51)));
    }

    @Test
    public void testAngelCalculatesNeighboursTourLeftover() throws Exception {
        mockMvc.perform(get(CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "100.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leftover", is(200.00)));
    }

    @Test
    public void testAngelCalculatesLocalBudgetWhenThereIsProblemWithTurkishLira() throws Exception {
        Mockito.when(currencyConverter.convertCurrency(105, "EUR", "TRY")).thenThrow(new MoneyException("No available exchange rate for turkish lira."));

        mockMvc.perform(get(CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "105.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TR", is(105.00)))
                .andExpect(jsonPath("$.GR", is(105.00)))
                .andExpect(jsonPath("$.MK", is(6452.81)))
                .andExpect(jsonPath("$.SR", is(1730.52)))
                .andExpect(jsonPath("$.RO", is(510.51)));
    }

    @Test
    public void testAngelCalculatesLocalBudgetWhenOriginalCurrencyIsUnknown() throws Exception {
        mockMvc.perform(get(CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "105.00")
                .param("tourStartingCountry", "BG")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tourOriginalCurrency").exists());
    }

    @Test
    public void testAngelCalculatesLocalBudgetWhenOriginalCurrencyNotExists() throws Exception {
        mockMvc.perform(get(CALCULATE_LOCAL_BUDGET_PER_COUNTRY_ENDPOINT)
                .param("tourTotalBudget", "1200.00")
                .param("tourCostPerCountry", "105.00")
                .param("tourStartingCountry", "BG")
                .param("tourOriginalCurrency", "EUR_TEST")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TR", is(0.00)))
                .andExpect(jsonPath("$.GR", is(0.00)))
                .andExpect(jsonPath("$.MK", is(0.00)))
                .andExpect(jsonPath("$.SR", is(0.00)))
                .andExpect(jsonPath("$.RO", is(0.00)));
    }
}
