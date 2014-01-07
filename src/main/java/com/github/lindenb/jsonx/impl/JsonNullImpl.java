package com.github.lindenb.jsonx.impl;

import com.github.lindenb.jsonx.JsonNull;

public class JsonNullImpl extends AbstractJsonElement
	implements JsonNull
	{
	private static final long serialVersionUID = 1L;
	@Override
	public final boolean isJsonNull() {
		return true;
		}
	@Override
	public JsonNull cloneElement()
		{
		return new JsonNullImpl();
		}
	}
