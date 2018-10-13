package com.muksihs.vaadin.javascript;

import elemental.json.JsonValue;

public interface JsBiErrorResultCallback extends JsArrayCallback {
	@Override
	default void result(JsonValue... jsonValues) {
		if (jsonValues == null || jsonValues.length==0) {
			error((JsonValue)null);
			return;
		}
		if (jsonValues[0]!=null) {
			error(jsonValues[0]);
		}
		if (jsonValues.length>1) {
			result(jsonValues[1]);
		}
	}
	default void error(JsonValue error) {}
	void result(JsonValue result);
}