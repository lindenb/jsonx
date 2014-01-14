package com.github.lindenb.jsonx;

public interface JsonPrimitive extends JsonElement
	{
	@Override
	public JsonPrimitive cloneElement();
	public Object getValue();
	}
