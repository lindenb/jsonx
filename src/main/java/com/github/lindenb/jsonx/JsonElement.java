package com.github.lindenb.jsonx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


public interface JsonElement extends Serializable
	{
	public boolean isJsonArray();
	public boolean isJsonNull();
	public boolean isJsonObject();
	public boolean isJsonPrimitive();
	public boolean isString();
	public boolean isNumber();
	public boolean isBoolean();
	public boolean isInteger();
	public boolean isDecimal();
	
	
	public  JsonArray 	getAsJsonArray();
	public	JsonNull 	getAsJsonNull();
	public	JsonObject 	getAsJsonObject();
	public	JsonPrimitive 	getAsJsonPrimitive();
	
	public String getAsString();
	public boolean getAsBoolean();
	public BigInteger getAsBigInteger();
	public BigDecimal getAsBigDecimal();
	public Number getAsNumber();
	
	public JsonElement getParent();
	public void unlink();
	public JsonElement cloneElement();
	
	}
