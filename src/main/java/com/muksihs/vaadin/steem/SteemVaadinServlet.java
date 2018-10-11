package com.muksihs.vaadin.steem;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;

import javax.servlet.annotation.WebServlet;


@WebServlet(urlPatterns = "/*", name = "SteemVaadinServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = SteemVaadinUI.class, productionMode = false)
public class SteemVaadinServlet extends VaadinServlet { }