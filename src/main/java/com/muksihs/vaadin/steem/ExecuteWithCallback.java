package com.muksihs.vaadin.steem;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.Style;

import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

@SuppressWarnings("serial")
@Tag("script")
public class ExecuteWithCallback extends Component {
	private static int counter=0;
	private final int me;
	private final String id;
	private final ExecuteWithCallback.JsonvalueCallback callback;
	public ExecuteWithCallback(ExecuteWithCallback.JsonvalueCallback callback) {
		this.callback = callback;
		me = ++counter;
		id=this.getClass().getName()+"-"+(me);
		Style style = this.getElement().getStyle();
		style.set("display", "none");
		this.setId(id);
	}
	
	public static interface JsonvalueCallback {
		void onCallback(JsonValue... jsonValues);
	}
	
	public void call() {
		JsonObject query = Json.createObject();
		query.put("tag", "leatherdog-games");
		query.put("limit", 2);
		UI.getCurrent().getPage().executeJavaScript("steem.api.getDiscussionsByBlog($0,document.getElementById(\""+id+"\").$server.callback)", query);
	}
	@ClientCallable
	private void callback(JsonValue... jsonValues) {
		System.out.println("=== CALLBACK ... "+(jsonValues==null?"NULL":jsonValues.length));
		if (callback!=null) {
			callback.onCallback(jsonValues);
		}
		getElement().removeFromParent();
	}
}