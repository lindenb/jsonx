package com.github.lindenb.jsonx;

public interface JsonArray extends JsonElement,Iterable<JsonElement>
	{
	public int size();
	public JsonElement get(int i);
	public JsonElement remove(int i);
	public JsonElement remove(JsonElement C);
	public void add(int index,JsonElement E);
	public void add(JsonElement E);
	}
