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
	
	@Override
	public final boolean isString()
		{
		return value.getClass()==String.class;
		}
	@Override
	public final boolean isBoolean()
		{
		return value.getClass()==Boolean.class;
		}
	@Override
	public final boolean isNumber()
		{
		return isInteger() || isDecimal();
		}

	@Override
	public final boolean isInteger() {
		return value.getClass()==BigInteger.class;
		}
	@Override
	public final boolean isDecimal() {
		return value.getClass()==BigDecimal.class;
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
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
		}
	@Override
	public boolean equals(Object obj) {
		if(obj==this) return true;
		if(obj==null || !(obj instanceof JsonPrimitive)) return false;
		JsonPrimitive other=JsonPrimitive.class.cast(obj);
		return this.value.equals(other.getValue());
		}

	@Override
	public String toString() {
		return value.toString();
		}	

	
	public String getAsString()
		{
		return String.class.cast(value);
		}
	public boolean getAsBoolean()
		{
		return Boolean.class.cast(value);
		}
	public BigInteger getAsBigInteger()
		{
		return BigInteger.class.cast(value);
		}
	public BigDecimal getAsBigDecimal()
		{
		return BigDecimal.class.cast(value);
		}
	public Number getAsNumber()
		{
		return Number.class.cast(value);
		}

	
	}
