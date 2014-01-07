package com.github.lindenb.jsonx.impl;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonNull;
import com.github.lindenb.jsonx.JsonObject;
import com.github.lindenb.jsonx.JsonPrimitive;

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
	}
