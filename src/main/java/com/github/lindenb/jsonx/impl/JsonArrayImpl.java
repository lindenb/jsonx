package com.github.lindenb.jsonx.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonElement;

public class JsonArrayImpl extends AbstractJsonElement implements JsonArray
	{
	private static final long serialVersionUID = 1L;
	private List<JsonElement> array=new ArrayList<JsonElement>();
	
	@Override
	public final boolean isJsonArray() {
		return true;
		}
	
	@Override
	public Iterator<JsonElement> iterator() {
		return new MyIterator();
		}
	
	@Override
	public JsonElement get(int i) {
		return array.get(i);
		}
	
	@Override
	public int size() {
		return array.size();
		}
	@Override
	public JsonElement remove(JsonElement e)
		{
		int i=this.array.indexOf(e);
		if(i==-1) return null;
		return this.remove(i);
		}
	
	@Override
	public JsonElement remove(int i)
		{
		JsonElement E=this.array.remove(i);
		AbstractJsonElement.class.cast(E).parent=null;
		return E;
		}
	
	@Override
	public void add(JsonElement E)
		{
		if(E==this) throw new IllegalStateException();
		E.unlink();
		this.array.add(E);
		AbstractJsonElement.class.cast(E).parent=this;
		}
	
	@Override
	public void add(int index, JsonElement E)
		{
		if(E==this) throw new IllegalStateException();
		E.unlink();
		this.array.add(index, E);
		AbstractJsonElement.class.cast(E).parent=this;
		}
	
	public JsonArray cloneElement()
		{
		JsonArrayImpl cp=new JsonArrayImpl();
		for(JsonElement E:this)
			{
			cp.add(E.cloneElement());
			}
		return cp;
		}
	
	private class MyIterator implements Iterator<JsonElement>
		{
		private int index=-1;
		private JsonElement toRemove=null;
		@Override
		public boolean hasNext()
			{
			return index+1<size();
			}
		@Override
		public JsonElement next()
			{
			if(index+1>=size()) throw new IllegalStateException();
			++index;
			toRemove= array.get(index);
			return toRemove;
			}
		@Override
		
		public void remove()
			{
			if(toRemove==null) throw new IllegalStateException();
			JsonArrayImpl.this.remove(index);
			toRemove=null;
			index--;
			}
		
		}
	
	
	
	}
