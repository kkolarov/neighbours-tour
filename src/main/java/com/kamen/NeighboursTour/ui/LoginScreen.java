package com.kamen.NeighboursTour.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginScreen extends FlexLayout {

    private static final String URL = "/oauth2/authorization/google";

    public LoginScreen() {
        Anchor googleLoginButton = new Anchor(URL, "Login with Google");
        add(googleLoginButton);
        setSizeFull();
    }
}