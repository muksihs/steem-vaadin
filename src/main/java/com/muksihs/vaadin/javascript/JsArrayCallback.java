package com.muksihs.vaadin.javascript;

import elemental.json.JsonValue;

public interface JsArrayCallback {
	void result(JsonValue... jsonValues);
}