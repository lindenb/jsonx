package com.github.lindenb.jsonx.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonConstants;
import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonObject;
import com.github.lindenb.jsonx.JsonPrimitive;

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
		return xef.createStartElement(PREFIX, XMLNS , name, attributes.iterator(), namespaces.iterator());
		}
	
	private XMLEvent createEndElement(String s)
		{
		List<Namespace> namespaces=Collections.emptyList(); 
		return xef.createEndElement(PREFIX, XMLNS ,s,namespaces.iterator());
		}
	
	public void write(File f,JsonElement E) throws XMLStreamException,IOException
		{
		FileWriter fout=new FileWriter(f);
		XMLOutputFactory xof=XMLOutputFactory.newFactory();
		xof.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
		XMLStreamWriter w=xof.createXMLStreamWriter(fout);
		w.writeStartDocument(fout.getEncoding(), "1.0");
		write(w,E);
		w.writeEndDocument();
		w.close();
		fout.flush();
		fout.close();
		}
	
	

	
	public void write(XMLStreamWriter w,JsonElement E) throws XMLStreamException
		{
		write(w,E,null);
		}
	
	public void write(XMLEventWriter w,JsonElement E) throws XMLStreamException
		{
		write(w,E,null);
		}
	
	public void write(XMLEventWriter w,JsonElement E,String attName)
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
	
	private void writeName(XMLStreamWriter w,String name)throws XMLStreamException
		{
		if(name!=null) w.writeAttribute("name", name);
		}
	
	public void write(XMLStreamWriter w,JsonElement E,String attName)
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
				w.writeStartElement(PREFIX, localName, XMLNS);
				writeName(w,attName);
				w.writeCharacters(p.getValue().toString());
				w.writeEndElement();
				}
			else if(E.isJsonNull())
				{
				w.writeEmptyElement(PREFIX, "null", XMLNS);
				writeName(w,attName);
				}
			else if(E.isJsonArray())
				{
				JsonArray array=E.getAsJsonArray();
				w.writeStartElement(PREFIX, "array", XMLNS);
				writeName(w,attName);
				for(int i=0;i< array.size();++i)
					{
					write(w,array.get(i),null);
					}
				w.writeEndElement();
				}
			else if(E.isJsonObject())
				{
				JsonObject object=E.getAsJsonObject();
				w.writeStartElement(PREFIX, "object", XMLNS);
				writeName(w,attName);
				for(String k: object.keySet())
					{
					write(w,object.get(k),k);
					}
				w.writeEndElement();
				}
			else
				{
				throw new IllegalStateException();
				}
			}
	
	public String toString(JsonElement E,String encoding) throws XMLStreamException
		{
		StringWriter sw=new StringWriter();
		XMLOutputFactory xof=XMLOutputFactory.newFactory();
		xof.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);
		XMLStreamWriter w=xof.createXMLStreamWriter(sw);
		w.writeStartDocument(encoding, "1.0");
		write(w,E);
		w.writeEndDocument();
		w.close();
		return sw.toString();
		}
	public String toString(JsonElement E) throws XMLStreamException
		{
		return toString(E,"UTF-8");
		}
	
	}
