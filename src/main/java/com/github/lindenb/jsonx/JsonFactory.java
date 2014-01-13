package com.github.lindenb.jsonx;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.lindenb.jsonx.impl.JsonArrayImpl;
import com.github.lindenb.jsonx.impl.JsonNullImpl;
import com.github.lindenb.jsonx.impl.JsonObjectImpl;
import com.github.lindenb.jsonx.impl.JsonPrimitiveImpl;

public class JsonFactory
	{
	public JsonFactory()
		{
		}
	
	public JsonArray newArray()
		{
		return new JsonArrayImpl();
		}
	public JsonObject newObject()
		{
		return new JsonObjectImpl();
		}
	public JsonNull newNull()
		{
		return new JsonNullImpl();
		}
	public JsonPrimitive newString(String s)
		{
		return new JsonPrimitiveImpl(s);
		}
	
	public JsonPrimitive newNumber(int v)
		{
		return newNumber(new BigDecimal(v));
		}
	public JsonPrimitive newNumber(long v)
		{
		return newNumber(new BigDecimal(v));
		}
	
	
	public JsonPrimitive newNumber(BigInteger v)
		{
		return new JsonPrimitiveImpl(v);
		}
	public JsonPrimitive newNumber(BigDecimal v)
		{
		return new JsonPrimitiveImpl(v);
		}
	public JsonPrimitive newBoolean(boolean v)
		{
		return new JsonPrimitiveImpl(v);
		}
	public JsonPrimitive newTrue()
		{
		return newBoolean(true);
		}
	public JsonPrimitive newFalse()
		{
		return newBoolean(false);
		}
	}
