package com.muksihs.vaadin.steem;

import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;


@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/*", name = "SteemVaadinServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = SteemVaadinUI.class, productionMode = false)
public class SteemVaadinServlet extends VaadinServlet { }