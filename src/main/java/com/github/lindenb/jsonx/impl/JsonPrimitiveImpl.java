package com.github.lindenb.jsonx.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.lindenb.jsonx.JsonPrimitive;

public  class JsonPrimitiveImpl extends AbstractJsonElement implements JsonPrimitive
	{
	private static final long serialVersionUID = 1L;
	private Object value;

	
	private JsonPrimitiveImpl(Object value)
		{
		this.value=value;
		}
	
	public JsonPrimitiveImpl(BigInteger o)
		{
		this.value=o;
		}
	
	public JsonPrimitiveImpl(BigDecimal o)
		{
		this.value=o;
		}
	
	public JsonPrimitiveImpl(String o)
		{
		this.value=o;
		}
	
	public JsonPrimitiveImpl(boolean o)
		{
		this.value=o;
		}
	
	public boolean isString()
		{
		return value.getClass()==String.class;
		}
	public boolean isBoolean()
		{
		return value.getClass()==Boolean.class;
		}
	
	public boolean isNumber()
		{
		return value.getClass()==BigInteger.class || value.getClass()==BigDecimal.class;
		}

	@Override
	public Object getValue()
		{
		return value;
		}
	
	@Override
	public JsonPrimitive cloneElement()
		{
		JsonPrimitiveImpl E=new JsonPrimitiveImpl(this.value);
		return E;
		}
	@Override
	public final boolean isJsonPrimitive() {
		return true;
		}
	}
