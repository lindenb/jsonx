package com.github.lindenb.jsonx.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonObject;

public class JsonObjectImpl extends AbstractJsonElement implements JsonObject
	{
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, JsonElement> map=new LinkedHashMap<String,JsonElement>();
	
	
	
	
	
	
	@Override
	public JsonElement findById(String id)
		{
		if(hasId(id)) return this;
		for(String key:this.keySet())
			{
			JsonElement E2=get(key).findById(id);
			if(E2!=null) return E2;
			}
		return null;
		}

	
	
	
	
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
	
	
	@Override
	public int hashCode() {
		return this.map.hashCode();
		}
	@Override
	public boolean equals(Object obj) {
		if(obj==this) return true;
		if(obj==null || !(obj instanceof JsonObject)) return false;
		JsonObject other=JsonObject.class.cast(obj);
		if(!this.keySet().equals(other.keySet())) return false;
		for(String key:this.keySet())
			{
			if(!this.get(key).equals(other.get(key))) return false;
			}
		return true;
		}

	@Override
	public void clear()
		{
		for(String jey:this.keySet())
			{
			JsonElement o=this.get(jey);
			o.unlink();
			}
		this.map.clear();
		}
	
	@Override
	public boolean containsKey(Object k) {
		String key=castKey(k);
		if(key==null) return false;
		return this.containsKey(key);
		}
	
	@Override
	public boolean containsValue(Object v) {
		
		if(v==null || !(v instanceof JsonElement)) return false;
		return this.map.containsValue(v);
		}
	
	
	@Override
	public Set<java.util.Map.Entry<String, JsonElement>> entrySet() {
		Set<java.util.Map.Entry<String, JsonElement>> set=new LinkedHashSet<java.util.Map.Entry<String, JsonElement>>();
		for(String key:this.keySet())
			{
			java.util.AbstractMap.SimpleImmutableEntry<String, JsonElement> p=new
					java.util.AbstractMap.SimpleImmutableEntry<String, JsonElement>(key,this.get(key));
			set.add(p);
			}
		return set;
		}
	
	private String castKey(Object key)
		{
		if(key==null) return null;
		if(key.getClass()==String.class) return String.class.cast(key);
		if(key instanceof CharSequence) return key.toString();
		if(key instanceof JsonElement )
			{
			JsonElement E=JsonElement.class.cast(key);
			if(!E.isJsonPrimitive()) return null;
			return E.getAsJsonPrimitive().getValue().toString();
			}
		return null;
		}	
	
	@Override
	public JsonElement get(Object k) {
		String key=castKey(k);
		return key==null?null:this.get(key);
		}
	
	@Override
	public boolean isEmpty()
		{
		return this.map.isEmpty();
		}
	
	@Override
	public void putAll(Map<? extends String, ? extends JsonElement> M) {
		for(String K:M.keySet())
			{
			this.map.put(K, M.get(K).cloneElement());
			}
		}
	@Override
	public JsonElement remove(Object k) {
		String key=castKey(k);
		if(key==null) return null;
		return  this.remove(key);
	}
	@Override
	public Collection<JsonElement> values() {
		return this.map.values();
	}
	
	
	@Override
	public String getString(String key)
		{
		JsonElement E=get(key);
		if(E==null) throw new java.util.NoSuchElementException("no element "+key);
		return E.getAsString();
		}
	public boolean getBoolean(String key)
		{
		JsonElement E=get(key);
		if(E==null) throw new java.util.NoSuchElementException("no element "+key);
		return E.getAsBoolean();
		}
	public BigInteger getBigInteger(String key)
		{
		JsonElement E=get(key);
		if(E==null) throw new java.util.NoSuchElementException("no element "+key);
		return E.getAsBigInteger();
		}
	public BigDecimal getBigDecimal(String key)
		{
		JsonElement E=get(key);
		if(E==null) throw new java.util.NoSuchElementException("no element "+key);
		return E.getAsBigDecimal();
		}
	public int getInt(String key)
		{
		return getBigInteger(key).intValue();
		}
	public long getLong(String key)
		{
		return getBigInteger(key).longValue();
		}
	public float getFloat(String key)
		{
		return getBigDecimal(key).floatValue();
		}
	public double getDouble(String key)
		{
		return getBigDecimal(key).doubleValue();
		}

	
	
	}
