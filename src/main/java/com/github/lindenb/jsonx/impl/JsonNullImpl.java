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
	
	@Override
	public int hashCode() {
		return -1;
		}
	@Override
	public boolean equals(Object obj) {
		if(obj==this) return true;
		if(obj==null || !(obj instanceof JsonNull)) return false;
		return true;
		}

	
	}
