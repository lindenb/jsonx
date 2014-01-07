package com.github.lindenb.jsonx;

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;

public class JsonXmlWriter
	extends JsonConstants
	{
	private static final QName ATT_NAME=new QName("name");
	private XMLEventFactory xef=XMLEventFactory.newFactory();
	private XMLEvent createStartElement(String s,String name)
		{
		List<Attribute> attributes= Collections.emptyList();
		if(name!=null) attributes=Collections.singletonList(xef.createAttribute(ATT_NAME, name)); 
		List<Namespace> namespaces=Collections.emptyList(); 
		return xef.createStartElement("json", XMLNS , name, attributes.iterator(), namespaces.iterator());
		}
	
	private XMLEvent createEndElement(String s)
		{
		List<Namespace> namespaces=Collections.emptyList(); 
		return xef.createEndElement("json", XMLNS ,s,namespaces.iterator());
		}
	
	private void write(XMLEventWriter w,JsonElement E,String attName)
		throws XMLStreamException
		{
		if(E.isJsonPrimitive())
			{
			JsonPrimitive p=E.getAsJsonPrimitive();
			String localName=null;
			if(p.isString()) localName="string";
			else if(p.isNumber()) localName="string";
			else if(p.isBoolean()) localName="boolean";
			else throw new IllegalStateException();
			w.add(createStartElement(localName,attName));
			w.add(xef.createCharacters(p.getValue().toString()));
			w.add(createEndElement(localName));
			}
		else if(E.isJsonNull())
			{
			w.add(createStartElement("null",attName));
			w.add(createEndElement("null"));
			}
		else if(E.isJsonArray())
			{
			JsonArray array=E.getAsJsonArray();
			w.add(createStartElement("array",attName));
			for(int i=0;i< array.size();++i)
				{
				write(w,array.get(i),null);
				}
			w.add(createEndElement("array"));
			}
		else if(E.isJsonObject())
			{
			JsonObject object=E.getAsJsonObject();
			w.add(createStartElement("object",attName));
			for(String k: object.keySet())
				{
				write(w,object.get(k),k);
				}
			w.add(createEndElement("object"));
			}
		else
			{
			throw new IllegalStateException();
			}
		}
	}
