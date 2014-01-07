package com.github.lindenb.jsonx;

import java.io.Serializable;


public interface JsonElement extends Serializable
	{
	public boolean isJsonArray();
	public boolean isJsonNull();
	public boolean isJsonObject();
	public boolean isJsonPrimitive();
	
	public  JsonArray 	getAsJsonArray();
	public	JsonNull 	getAsJsonNull();
	public	JsonObject 	getAsJsonObject();
	public	JsonPrimitive 	getAsJsonPrimitive();
	
	public JsonElement getParent();
	public void unlink();
	public JsonElement cloneElement();
	
	}
