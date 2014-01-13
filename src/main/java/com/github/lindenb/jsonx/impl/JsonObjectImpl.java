package com.github.lindenb.jsonx.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonObject;

public class JsonObjectImpl extends AbstractJsonElement implements JsonObject
	{
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, JsonElement> map=new LinkedHashMap<String,JsonElement>();
	@Override
	public final boolean isJsonObject() {
		return true;
		}
	@Override
	public boolean containsKey(String key) {
		return map.containsKey(key);
		}
	@Override
	public JsonElement get(String key) {
		return map.get(key);
		}
	@Override
	public JsonElement remove(String key)
		{
		JsonElement E=map.remove(key);
		if(E!=null) AbstractJsonElement.class.cast(E).parent=null;
		return E;
		}
	@Override
	public JsonElement remove(JsonElement value)
		{
		for(String k:map.keySet())
			{
			if(map.get(k)==value)
				{
				return remove(k);
				}
			}	
		return null;
		}
	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet(map.keySet());
		}
	@Override
	public int size()
		{
		return map.size();
		}
	
	@Override
	public JsonElement put(String key, JsonElement E)
		{
		if(E==this) throw new IllegalStateException();
		E.unlink();
		JsonElement prev=this.map.get(key);
		if(prev!=null) prev.unlink();
		this.map.put(key, E);
		AbstractJsonElement.class.cast(E).parent=this;
		return prev;
		}
	
	@Override
	public JsonObject cloneElement()
		{
		JsonObjectImpl M=new JsonObjectImpl();
		for(String key:this.map.keySet())
			{
			M.put(key,this.map.get(key).cloneElement());
			}
		return M;
		}
	
	@Override
	public JsonElement put(String key, BigDecimal E)
		{
		return put(key,E==null?new JsonNullImpl():new JsonPrimitiveImpl(E));
		}
	@Override
	public JsonElement put(String key, BigInteger E)
		{
		return put(key,E==null?new JsonNullImpl():new JsonPrimitiveImpl(E));
		}
	@Override
	public JsonElement put(String key, boolean b)
		{
		return put(key,new JsonPrimitiveImpl(b));
		}
	
	@Override
	public JsonElement put(String key, String E) {
		return put(key,E==null?new JsonNullImpl():new JsonPrimitiveImpl(E));
		}
	
	
	@Override
	public JsonElement put(String key, int E) {
		return put(key,new BigInteger(String.valueOf(E)));
		}
	@Override
	public JsonElement put(String key, long E) {
		return put(key,new BigInteger(String.valueOf(E)));
		}
	@Override
	public JsonElement put(String key, float E) {
		return put(key,new BigDecimal(E));
		}

	@Override
	public JsonElement put(String key,double E) {
		return put(key,new BigDecimal(E));
		}
	}
