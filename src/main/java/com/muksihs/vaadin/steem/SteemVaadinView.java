package com.muksihs.vaadin.steem;

import com.muksihs.vaadin.javascript.JsBiErrorResultCallback;
import com.muksihs.vaadin.javascript.JsCallback;
import com.muksihs.vaadin.javascript.JsExecuteThen;
import com.muksihs.vaadin.javascript.JsWithArrayCallback;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

/**
 * The main view of the application
 */
@SuppressWarnings("serial")
@Route("")
@BodySize(height = "100%", width = "100%")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(value = Lumo.class, variant = "dark")
public class SteemVaadinView extends VerticalLayout {
	private static final String GET_DISCUSSIONS_BY_BLOG = "steem.api.getDiscussionsByBlog";
	private static final String GET_DISCUSSIONS_BY_BLOG_ASYNC = "steem.api.getDiscussionsByBlogAsync";

	public SteemVaadinView() {
		UI ui1 = UI.getCurrent();
		ui1.add(new Label("Add Component while in Constructor"));
		System.out.println("UI1 hashCode: "+ui1.hashCode());
		System.out.println("HTML: "+ui1.getElement().getOuterHTML());
		ui1.access(()->{
			UI ui2 = UI.getCurrent();
			System.out.println("UI2 hashCode: "+ui2.hashCode());
			System.out.println("HTML: "+ui2.getElement().getOuterHTML());
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {

		UI.getCurrent().add(new Label("SteemVaadinView#onAttach"));

		setClassName("app-view");

		Button button = new Button("NAV1");
		button.addClickListener((e) -> UI.getCurrent().navigate(TopNavTest.class));
		this.add(button);

		JsonObject query = Json.createObject();
		query.put("tag", "leatherdog-games");
		query.put("limit", 2);

		JsWithArrayCallback.call(GET_DISCUSSIONS_BY_BLOG, (JsBiErrorResultCallback) value -> {
			Label label = new Label("With Callback");
			SteemVaadinView.this.add(label);
			int ix = 0;
			if (value == null) {
				TextArea textArea = new TextArea("JSON RECEIVED [" + (++ix) + "]");
				textArea.setValue("[NULL]");
				SteemVaadinView.this.add(textArea);
			} else {
				if (value instanceof JsonArray) {
					JsonArray array = (JsonArray) value;
					for (int iy = 0; iy < array.length(); iy++) {
						TextArea itemText = new TextArea("ARRAY ITEM [" + (iy + 1) + "]");
						String jsonEntry = array.get(iy).toJson();
						itemText.setValue(array.get(iy).getType() + ":\n\n" + jsonEntry);
						itemText.setWidth("98%");
						SteemVaadinView.this.add(itemText);
					}
				} else {
					TextArea itemText = new TextArea("OTHER ITEM");
					String jsonEntry = value.toJson();
					itemText.setValue(value.getType() + ":\n\n" + jsonEntry);
					itemText.setWidth("98%");
					SteemVaadinView.this.add(itemText);
				}
			}
		}, query);

		JsExecuteThen.call(GET_DISCUSSIONS_BY_BLOG_ASYNC, value -> {
			Label label = new Label("With Then");
			SteemVaadinView.this.add(label);
			int ix = 0;
			if (value == null) {
				TextArea textArea = new TextArea("JSON RECEIVED [" + (++ix) + "]");
				textArea.setValue("[NULL]");
				SteemVaadinView.this.add(textArea);
			} else {
				if (value instanceof JsonArray) {
					JsonArray array = (JsonArray) value;
					for (int iy = 0; iy < array.length(); iy++) {
						TextArea itemText = new TextArea("ARRAY ITEM [" + (iy + 1) + "]");
						String jsonEntry = array.get(iy).toJson();
						itemText.setValue(array.get(iy).getType() + ":\n\n" + jsonEntry);
						itemText.setWidth("98%");
						SteemVaadinView.this.add(itemText);
					}
				} else {
					TextArea itemText = new TextArea("OTHER ITEM");
					String jsonEntry = value.toJson();
					itemText.setValue(value.getType() + ":\n\n" + jsonEntry);
					itemText.setWidth("98%");
					SteemVaadinView.this.add(itemText);
				}
			}
		}, query);

		JsExecuteThen.call(GET_DISCUSSIONS_BY_BLOG_ASYNC, null, (JsCallback) error -> {
			TextArea itemText = new TextArea("ERROR");
			String jsonEntry = error.toJson();
			itemText.setValue(error.getType() + ":\n\n" + jsonEntry);
			itemText.setWidth("98%");
			SteemVaadinView.this.add(itemText);
		}, Json.createNull());
	}
}