package com.muksihs.vaadin.steem;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@HtmlImport("frontend://styles/steemvaadin-theme.html")
@Theme(value=Lumo.class, variant="dark")
@JavaScript("//cdn.steemjs.com/lib/latest/steem.min.js")
public class SteemVaadinUI extends UI {
}
