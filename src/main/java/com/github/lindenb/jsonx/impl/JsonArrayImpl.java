package com.github.lindenb.jsonx.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonElement;

public class JsonArrayImpl extends AbstractJsonElement implements JsonArray
	{
	private static final long serialVersionUID = 1L;
	private List<JsonElement> array=new ArrayList<JsonElement>();
	
	public JsonArrayImpl()
		{
		
		}
	
	
	@Override
	public JsonElement findById(String id)
		{
		if(hasId(id)) return this;
		for(JsonElement E:this.array)
			{
			JsonElement E2=E.findById(id);
			if(E2!=null) return E2;
			}
		return null;
		}

	
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
	public boolean add(JsonElement E)
		{
		if(E==this) throw new IllegalStateException();
		E.unlink();
		this.array.add(E);
		AbstractJsonElement.class.cast(E).parent=this;
		return true;
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
	
	@Override
	public int hashCode() {
		return this.array.hashCode();
		}
	@Override
	public boolean equals(Object obj) {
		if(obj==this) return true;
		if(obj==null || !(obj instanceof JsonArray)) return false;
		JsonArray other=JsonArray.class.cast(obj);
		if(this.size()!=other.size()) return false;
		for(int i=0;i< size();++i)
			{
			if(!this.get(i).equals(other.get(i))) return false;
			}
		return true;
		}
	@Override
	public boolean addAll(Collection<? extends JsonElement> c)
		{
		boolean changed=false;
		for(JsonElement E:c) if(this.add(E)) changed=true;
		return changed;
		}
	@Override
	public void clear() {
		for(JsonElement E:this.array) E.unlink();
		this.array.clear();
		}
	
	@Override
	public boolean contains(Object o) {
		if(o==null || !(o instanceof JsonElement)) return false;
		return this.array.contains(o);
		}
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.array.containsAll(c);
		}
	@Override
	public boolean isEmpty() {
		return this.array.isEmpty();
		}
	@Override
	public int indexOf(Object o) {
		if(o==null || !(o instanceof JsonElement)) return -1;
		return this.array.indexOf(o);
		}
	private void unsupported()
		{
		throw new UnsupportedOperationException();
		}
	@Override
	public boolean addAll(int index, Collection<? extends JsonElement> c) {
		unsupported();
		return false;
		}

	@Override
	public int lastIndexOf(Object o) {
		if(o==null || !(o instanceof JsonElement)) return -1;
		return this.array.lastIndexOf(o);
		}

	@Override
	public ListIterator<JsonElement> listIterator() {
		unsupported();
		return null;
	}

	@Override
	public ListIterator<JsonElement> listIterator(int index) {
		unsupported();		return null;
	}

	@Override
	public boolean remove(Object o) {
		unsupported();
		return false;
		}

	@Override
	public boolean removeAll(Collection<?> c) {
		unsupported();
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		unsupported();
		return false;
	}

	@Override
	public JsonElement set(int index, JsonElement element) {
		unsupported();
		return null;
		}

	@Override
	public List<JsonElement> subList(int fromIndex, int toIndex) {
		unsupported();
		return null;
		}

	@Override
	public Object[] toArray() {
		return array.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) 
		{
		return array.toArray(a);
		}
	
	}
