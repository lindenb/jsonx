package com.github.lindenb.jsonx;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

public interface JsonObject extends JsonElement,Map<String, JsonElement>
	{
	public JsonElement put(String key,JsonElement E);

	public JsonElement get(String key);
	public boolean containsKey(String key);
	public int size();
	public JsonElement remove(String key);
	public JsonElement remove(JsonElement value);
	public Set<String> keySet();
	@Override
	public JsonObject cloneElement();

	
	//shortcuts
	public JsonElement put(String key,String E);
	public JsonElement put(String key,BigInteger E);
	public JsonElement put(String key,BigDecimal E);
	public JsonElement put(String key,boolean b);
	public JsonElement put(String key,int v);
	public JsonElement put(String key,long v);
	public JsonElement put(String key,float v);
	public JsonElement put(String key,double v);
	
	
	public String getString(String key);
	public boolean getBoolean(String key);
	public BigInteger getBigInteger(String key);
	public BigDecimal getBigDecimal(String key);
	public int getInt(String key);
	public long getLong(String key);
	public float getFloat(String key);
	public double getDouble(String key);
	
	}
