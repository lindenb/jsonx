package com.github.lindenb.jsonx;

public interface JsonPrimitive extends JsonElement
	{
	@Override
	public JsonPrimitive cloneElement();
	public boolean isString();
	public boolean isBoolean();
	public boolean isNumber();
	public Object getValue();
	}
