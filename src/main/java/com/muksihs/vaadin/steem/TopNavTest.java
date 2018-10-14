package com.muksihs.vaadin.steem;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("nav1")
//@BodySize(height = "100%", width = "100%")
//@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
//@Theme(value = Lumo.class, variant = "dark")
public class TopNavTest extends SteemVaadinView {
	public TopNavTest() {
	}
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		UI.getCurrent().add(new Label("TopNavTest#onAttach"));
		setClassName("app-view");
		Button button = new Button("BACK");
		button.addClickListener((e)->UI.getCurrent().navigate(SteemVaadinView.class));
		this.add(button);
	}
}
