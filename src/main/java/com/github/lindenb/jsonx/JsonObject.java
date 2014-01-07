package com.github.lindenb.jsonx;

import java.util.Set;

public interface JsonObject extends JsonElement
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

	}
