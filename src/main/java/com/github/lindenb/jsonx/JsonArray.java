package com.github.lindenb.jsonx;

import java.util.List;

public interface JsonArray extends JsonElement,Iterable<JsonElement>,List<JsonElement>
	{
	public int size();
	public JsonElement get(int i);
	public JsonElement remove(int i);
	public JsonElement remove(JsonElement C);
	public void add(int index,JsonElement E);
	public boolean add(JsonElement E);
	}
