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
public class JsExecuteThen extends Component {
	private static long counter = System.currentTimeMillis();

	private static synchronized long nextCounter() {
		return ++counter;
	}

	private final String id;
	private final JsCallback onThen;
	private final JsCallback onCatch;
	private String javascriptFunctionCall;

	protected JsExecuteThen(JsCallback onThen, JsCallback onCatch) {
		this.onThen = onThen;
		this.onCatch = onCatch;
		id = this.getClass().getName() + "-" + nextCounter();
		this.setId(id);
	}

	protected JsExecuteThen(JsCallback onThen) {
		this(onThen, null);
	}

	public static void call(String function, JsonValue... args) {
		call(function, null, null, args);
	}

	public static void call(String function, JsCallback then, JsonValue... args) {
		call(function, then, null, args);
	}

	public static void call(String function, JsCallback then, JsCallback onCatch,
			JsonValue... args) {
		UI.getCurrent().getSession().access(()->{
			HasComponents ctx = UI.getCurrent();
			JsExecuteThen e = new JsExecuteThen(then, onCatch);
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
		sb.append(")");
		if (onThen != null) {
			sb.append(".then(document.getElementById(\"");
			sb.append(id);
			sb.append("\").$server.onThen)");
		}
		if (onCatch != null) {
			sb.append(".catch(document.getElementById(\"");
			sb.append(id);
			sb.append("\").$server.onCatch)");
		}
		javascriptFunctionCall = sb.toString();
		if (args != null && args.length > 0) {
			UI.getCurrent().getPage().executeJavaScript(javascriptFunctionCall, (Serializable[]) args);
		} else {
			UI.getCurrent().getPage().executeJavaScript(javascriptFunctionCall);
		}
	}

	@ClientCallable
	protected void onThen(JsonValue result) {
		if (onThen != null) {
			onThen.result(result);
		}
		getElement().removeFromParent();
	}

	@ClientCallable
	protected void onCatch(JsonValue result) {
		if (onCatch != null) {
			onCatch.result(result);
		}
//		getElement().removeFromParent();
	}

	public String getJavascriptFunctionCall() {
		return javascriptFunctionCall;
	}
}