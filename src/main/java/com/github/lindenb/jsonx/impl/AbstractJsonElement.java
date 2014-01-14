package com.github.lindenb.jsonx.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonNull;
import com.github.lindenb.jsonx.JsonObject;
import com.github.lindenb.jsonx.JsonPrimitive;
import com.github.lindenb.jsonx.io.JsonPrettyWriter;

public abstract class AbstractJsonElement implements JsonElement
	{
	private static final long serialVersionUID = 1L;
	protected JsonElement parent=null;
	protected AbstractJsonElement()
		{
		}
	
	@Override
	public JsonElement getParent() {
		return this.parent;
		}
	
	@Override
	public boolean isJsonArray() {
		return false;
		}
	@Override
	public boolean isJsonNull() {
		return false;
		}
	@Override
	public boolean isJsonObject() {
		return false;
		}
	@Override
	public boolean isJsonPrimitive() {
		return false;
		}
	@Override
	public boolean isString() { return false;}
	@Override
	public boolean isNumber() { return false;}
	@Override
	public boolean isBoolean() { return false;}
	@Override
	public boolean isInteger() { return false;}
	@Override
	public boolean isDecimal() { return false;}

	
	
	public  JsonArray 	getAsJsonArray()
		{
		if(!isJsonArray()) throw new ClassCastException("not a json-array");
		return JsonArray.class.cast(this);
		}
	public	JsonNull 	getAsJsonNull()
		{
		if(!isJsonNull()) throw new ClassCastException("not a json-null");
		return JsonNull.class.cast(this);
		}
	public	JsonObject 	getAsJsonObject()
		{
		if(!isJsonObject()) throw new ClassCastException("not a json-object");
		return JsonObject.class.cast(this);
		}
	public	JsonPrimitive 	getAsJsonPrimitive()
		{
		if(!isJsonPrimitive()) throw new ClassCastException("not a json-primitive");
		return JsonPrimitive.class.cast(this);
		}

	@Override
	public void unlink() {
		JsonElement p=this.parent;
		if(p==null) return;
		if(p.isJsonArray())
			{
			p.getAsJsonArray().remove(this);
			}
		else if(p.isJsonObject())
			{
			p.getAsJsonObject().remove(this);
			}
		this.parent=null;
		}
	
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);
	
	
	@Override
	public String toString()
		{
		JsonPrettyWriter jpw=new JsonPrettyWriter();
		return jpw.toString(this);
		}
	static String typeOf(JsonElement E)
		{	
		if(E==null) return null;
		if(E.isBoolean()) return "Primitive(Boolean)";
		if(E.isString())  return "Primitive(String)";
		if(E.isDecimal())  return "Primitive(Decimal)";
		if(E.isInteger())  return "Primitive(Integer)";
		if(E.isJsonArray()) return "Array";
		if(E.isJsonObject()) return "Object";
		if(E.isJsonNull()) return "Nil";
		return "undefined";
		}
	
	public String getAsString()
		{	
		return null;
		}
	public boolean getAsBoolean() { return false;}
	public BigInteger getAsBigInteger() { return null;}
	public BigDecimal getAsBigDecimal() { return null;}
	public Number getAsNumber() { return null;}

	
	
	}
