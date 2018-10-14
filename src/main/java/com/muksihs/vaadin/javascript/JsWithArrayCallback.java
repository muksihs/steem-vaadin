package com.muksihs.vaadin.javascript;

import java.io.Serializable;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;

import elemental.json.JsonValue;

@SuppressWarnings("serial")
@Tag("script")
public class JsWithArrayCallback extends Component {

	private static long counter = System.currentTimeMillis();

	private static synchronized long nextCounter() {
		return ++counter;
	}

	private final String id;
	private final JsArrayCallback onCallback;
	private String javascriptFunctionCall;

	protected JsWithArrayCallback(JsArrayCallback onCallback) {
		this.onCallback = onCallback;
		id = this.getClass().getName() + "-" + nextCounter();
		this.setId(id);
	}

	public static void call(String function, JsonValue... args) {
		call(function, null, args);
	}

	public static void call(String function, JsArrayCallback onCallback, JsonValue... args) {
		UI.getCurrent().getSession().access(() -> {
			HasComponents ctx = UI.getCurrent();
			JsWithArrayCallback e = new JsWithArrayCallback(onCallback);
			e.addAttachListener((event) -> {
				UI.getCurrent().access(() -> {
					e._call(function, args);
					System.out.println("Javascript-Then: " + e.getJavascriptFunctionCall());
				});
			});
			ctx.add(e);
		});
	}

	protected void _call(String function, JsonValue... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(function);
		sb.append("(");
		if (args != null && args.length > 0) {
			for (int ix = 0; ix < args.length; ix++) {
				sb.append("$");
				sb.append(ix);
				if (ix + 1 < args.length) {
					sb.append(",");
				}
			}
		}
		if (onCallback != null) {
			sb.append(",document.getElementById(\"");
			sb.append(id);
			sb.append("\").$server.onCallback");
		}
		sb.append(")");
		javascriptFunctionCall = sb.toString();
		if (args != null && args.length > 0) {
			UI.getCurrent().getPage().executeJavaScript(javascriptFunctionCall, (Serializable[]) args);
		} else {
			UI.getCurrent().getPage().executeJavaScript(javascriptFunctionCall);
		}
	}

	@ClientCallable
	protected void onCallback(JsonValue... jsonValues) {
		if (onCallback != null) {
			onCallback.result(jsonValues);
		}
		UI.getCurrent().getSession().access(() -> {
			HasComponents ctx = UI.getCurrent();
			ctx.remove(this);
		});
	}

	public String getJavascriptFunctionCall() {
		return javascriptFunctionCall;
	}
}