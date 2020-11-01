package com.kamen.NeighboursTour.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Dashboard")
public class DashboardScreen extends VerticalLayout {

    public DashboardScreen() {
        Anchor countriesMaximumVisitUrl = new Anchor("/api/v1/tours/calculate_counties_maximum_visit", "Calculate countries maximum visit...");
        Anchor localBudgetUrl = new Anchor("/api/v1/tours/calculate_local_budget", "Calculate local budget...");
        Anchor logout = new Anchor("/logout", "Logout");

        add(countriesMaximumVisitUrl, localBudgetUrl, logout);
    }
}
