package com.github.lindenb.jsonx.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonConstants;
import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonFactory;
import com.github.lindenb.jsonx.JsonNull;
import com.github.lindenb.jsonx.JsonObject;
import com.github.lindenb.jsonx.JsonPrimitive;
public class JsonXmlReader
	extends JsonConstants
	{
	private static final QName ATT_NAME=new QName("name");
	private JsonFactory jsonFactory=new JsonFactory();
	
	public JsonElement parse(File f) throws IOException,XMLStreamException
		{
		XMLInputFactory xif=XMLInputFactory.newFactory();
		FileReader fr=new FileReader(f);
		XMLEventReader r=xif.createXMLEventReader(fr);
		JsonElement E= parse(r);
		r.close();
		fr.close();
		return E;
		}
	public JsonElement parse(Reader fr) throws IOException,XMLStreamException
		{
		XMLInputFactory xif=XMLInputFactory.newFactory();
		XMLEventReader r=xif.createXMLEventReader(fr);
		JsonElement E=parse(r);
		r.close();
		return E;
		}
	public JsonElement parse(XMLEventReader r) throws XMLStreamException
		{
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isStartDocument()) continue;
			if(evt.isEndDocument()) continue;
			if(evt.isStartElement())
				{
				JsonElement E=any(r,evt.asStartElement());
				return E;
				}
			else if(evt.isCharacters())
				{
				if(!isEmpty(evt.asCharacters().getData()))
						throw new XMLStreamException("illegal content in xml", evt.getLocation());
				}
			}
		return null;
		}
	
	public JsonElement any(XMLEventReader r,StartElement E)throws XMLStreamException
		{
		String name=E.getName().getLocalPart();
		if(!JsonConstants.XMLNS.equals(E.getName().getNamespaceURI()))
			{
			throw new XMLStreamException("illegal namespace in xml:", E.getLocation());
			}
		if(name.equals("string"))
			{
			return parseString(r);
			}
		else if(name.equals("number"))
			{
			return parseNumber(r);
			}
		else if(name.equals("boolean"))
			{
			return parseBoolean(r);
			}
		else if(name.equals("null"))
			{
			return parseNull(r);
			}
		else if(name.equals("array"))
			{
			return _parseArray(r);
			}
		else if(name.equals("object"))
			{
			return _parseObject(r);
			}
		throw new XMLStreamException("not a json element "+name);
		}
	
	
	
	public JsonPrimitive parseString(XMLEventReader r) throws XMLStreamException
		{
		return jsonFactory.newString(parseData(r));
		}
	public JsonPrimitive parseBoolean(XMLEventReader r) throws XMLStreamException
		{
		String s=parseData(r);
		if(s.equals("true")) return jsonFactory.newBoolean(Boolean.TRUE);
		if(s.equals("false")) return jsonFactory.newBoolean(Boolean.FALSE);
		throw new XMLStreamException("not a boolean "+s);
		}
	
	public JsonPrimitive parseNumber(XMLEventReader r) throws XMLStreamException
		{
		String s=parseData(r);
		try
			{
			BigInteger bi=new BigInteger(s);
			return jsonFactory.newNumber(bi);
			}
		catch(NumberFormatException err)
			{
			try
				{
				BigDecimal bd=new BigDecimal(s);
				return jsonFactory.newNumber(bd);
				}
			catch(NumberFormatException err2)
				{
				throw new XMLStreamException("not a number ",err2);
				}
			}
		}
	
	private JsonNull parseNull(XMLEventReader r) throws XMLStreamException
		{
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isEndElement())
				{
				return this.jsonFactory.newNull();
				}
			else
				{
				throw new XMLStreamException("illegal content in array", evt.getLocation());
				}
			}
		throw new XMLStreamException("illegal state for null");
		}

	private JsonArray _parseArray(XMLEventReader r) throws XMLStreamException
		{
		JsonArray array=jsonFactory.newArray();
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isEndElement())
				{
				break;
				}
			else if(evt.isCharacters())
				{
				if(!isEmpty(evt.asCharacters().getData()))
						throw new XMLStreamException("illegal content in array", evt.getLocation());
				}
			else if(evt.isStartElement())
				{
				JsonElement E=any(r,evt.asStartElement());
				array.add(E);
				}
			}
		return array;
		}

	public JsonObject _parseObject(XMLEventReader r) throws XMLStreamException
		{
		JsonObject map=jsonFactory.newObject();
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isEndElement())
				{
				break;
				}
			else if(evt.isCharacters())
				{
				if(!isEmpty(evt.asCharacters().getData()))
						throw new XMLStreamException("illegal content in array", evt.getLocation());
				}
			else if(evt.isStartElement())
				{
				StartElement S=evt.asStartElement();
				Attribute att=S.getAttributeByName(ATT_NAME);
				if(att==null) throw new XMLStreamException("@name missing in object", evt.getLocation());
				if(map.containsKey(att.getValue()))
					{
					throw new XMLStreamException("duplicate name \""+att.getValue()+"\" in object", evt.getLocation());
					}
				JsonElement E=any(r,evt.asStartElement());
				map.put(att.getValue(),E);
				}
			}
		return map;
		}
	
	private String parseData(XMLEventReader r) throws XMLStreamException
		{
		StringBuilder b=null;
		while(r.hasNext())
			{
			XMLEvent evt=r.nextEvent();
			if(evt.isEndElement())
				{
				break;
				}
			else if(evt.isCharacters())
				{
				if(b==null) b=new StringBuilder();
				b.append(evt.asCharacters().getData());
				}
			else if(	evt.isStartElement())
				{
				throw new XMLStreamException("tag in string", evt.getLocation());
				}
			}
		if(b==null) throw new XMLStreamException("no content");
		return b.toString();
		}
	private static boolean isEmpty(String s)
		{
		return s.trim().isEmpty();
		}
	}
