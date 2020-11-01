package com.kamen.NeighboursTour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NeighboursTourManager {

    @Autowired
    private Currencies currencies;

    @Autowired
    private NeighboursFactoryInterface neighboursFactory;

    @Autowired
    private CurrencyConverterInterface currencyConverter;

    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    public int calculateCountiesMaximumVisit(NeighboursTour neighboursTour) {
        if (neighboursTour.getTourTotalBudget() > 0 && neighboursTour.getTourCostPerCountry() > 0) {
            List<String> neighbours = neighboursFactory.getNeighbours(neighboursTour.getTourStartingCountry());
            LOG.log(Level.INFO, "Neighbours: {0}", neighbours.size());

            if (!neighbours.isEmpty()) {
                return (int) (neighboursTour.getTourTotalBudget() / (neighbours.size() * neighboursTour.getTourCostPerCountry()));
            }
        }

        return 0;
    }

    public LocalBudget calculateLocalBudget(NeighboursTour neighboursTour) {
        LocalBudget localBudget = new LocalBudget();

        List<String> neighbours = neighboursFactory.getNeighbours(neighboursTour.getTourStartingCountry());
        LOG.log(Level.INFO, "Neighbours: {0}", neighbours.size());

        for (String neighbour: neighbours) {
            String localCurrency = currencies.getCurrency(neighbour);
            LOG.log(Level.INFO, "Local currency: {0}", localCurrency);

            try {
                double localMoney = currencyConverter.convertCurrency(neighboursTour.getTourCostPerCountry(), neighboursTour.getTourOriginalCurrency(), localCurrency);
                LOG.log(Level.INFO, "Local money: {0}", localMoney);

                localBudget.put(neighbour, localMoney);
            } catch (MoneyException e) {
                LOG.log(Level.SEVERE, "There is problem with currency conversion!", e);

                localBudget.put(neighbour, neighboursTour.getTourCostPerCountry());
            }
        }

        return localBudget;
    }

    public double calculateNeighboursTourLeftover(NeighboursTour neighboursTour) {
        int countriesMaximumVisit = calculateCountiesMaximumVisit(neighboursTour);
        LOG.log(Level.INFO, "Countries maximum visit: {0}", countriesMaximumVisit);

        if (countriesMaximumVisit > 0) {
            if (neighboursTour.getTourCostPerCountry() > 0) {
                List<String> neighbours = neighboursFactory.getNeighbours(neighboursTour.getTourStartingCountry());
                LOG.log(Level.INFO, "Neighbours: {0}", neighbours.size());

                if (!neighbours.isEmpty()) {
                    return neighboursTour.getTourTotalBudget() - (neighbours.size() * neighboursTour.getTourCostPerCountry() * countriesMaximumVisit);
                }
            }
        }

        return neighboursTour.getTourTotalBudget() > 0 ? neighboursTour.getTourTotalBudget() : 0;
    }
}
