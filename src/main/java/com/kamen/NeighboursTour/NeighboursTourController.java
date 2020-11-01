package com.kamen.NeighboursTour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class NeighboursTourController {

    @Autowired
    private NeighboursTourManager neighboursTourManager;

    @GetMapping("/tours/calculate_counties_maximum_visit")
    public @ResponseBody
    Map<String, Integer> calculateCountiesMaximumVisit(@Valid NeighboursTour neighboursTour) {
        int countriesMaximumVisit = neighboursTourManager.calculateCountiesMaximumVisit(neighboursTour);

        return new HashMap<String, Integer>() {{ put("result", countriesMaximumVisit); }};
    }

    @GetMapping("/tours/calculate_local_budget")
    public @ResponseBody
    Map<String, Double> calculateLocalBudget(@Valid NeighboursTour neighboursTour) throws MoneyException {
        LocalBudget localBudgetPerCountry = neighboursTourManager.calculateLocalBudget(neighboursTour);

        double leftover = neighboursTourManager.calculateNeighboursTourLeftover(neighboursTour);

        return new HashMap<String, Double>(localBudgetPerCountry.getValues()) {{ put("leftover", leftover); }};
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String, String> handleValidationExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
