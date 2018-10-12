package com.muksihs.vaadin.steem;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import elemental.json.JsonValue;

/**
 * The main view of the application
 */
@SuppressWarnings("serial")
@Route("")
@BodySize(height = "100%", width = "100%")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(value = Lumo.class, variant = "dark")
public class SteemVaadinView extends VerticalLayout {

	public SteemVaadinView() {
		setClassName("app-view");

		ExecuteWithCallback withCallback = new ExecuteWithCallback(jsonValues -> {
			int ix = 0;
			for (JsonValue value : jsonValues) {
				TextArea textArea = new TextArea("JSON RECEIVED [" + (++ix) + "]");
				if (value == null) {
					textArea.setValue("[NULL]");
				} else {
					textArea.setValue(value.toJson());
					textArea.setWidth("98%");
				}
				SteemVaadinView.this.add(textArea);
			}
		});
		add(withCallback);
		withCallback.call();
	}
}