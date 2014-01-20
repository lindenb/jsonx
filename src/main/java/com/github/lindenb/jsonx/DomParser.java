package com.github.lindenb.jsonx;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.w3c.dom.*;




public class DomParser extends JsonConstants
	{
	private JsonFactory jsonFactory=new JsonFactory();
	public JsonElement parse(Node n)
		{
		if(n==null) throw new  NullPointerException("argument is null");
		switch(n.getNodeType())
			{
			case Node.DOCUMENT_NODE: return parse(Document.class.cast(n).getDocumentElement()); 
			case Node.ELEMENT_NODE: return _parse(Element.class.cast(n));
			default: throw new IllegalArgumentException("Bad node type");
			}
		}
	private JsonElement _parse(Element n)
		{
		if(!XMLNS.equals(n.getNamespaceURI()))throw new IllegalArgumentException("Bad xmlns:"+n.getNamespaceURI());
		String localName=n.getLocalName();
		if(localName.equals("string"))
			{
			return jsonFactory.newString(_content(n));
			}
		else if(localName.equals("null"))
			{
			return jsonFactory.newNull();
			}
		else if(localName.equals("boolean"))
			{
			return jsonFactory.newBoolean(Boolean.parseBoolean(_content(n)));
			}
		else if(localName.equals("number"))
			{
			String s=_content(n);
			try
				{
				BigInteger bi=new BigInteger(s);
				return jsonFactory.newNumber(bi);
				}
			catch(Exception err)
				{
				BigDecimal bd=new BigDecimal(s);
				return jsonFactory.newNumber(bd);
				}
			}
		else if(localName.endsWith("array"))
			{
			JsonArray array=jsonFactory.newArray();
			for(Node c=n.getFirstChild();c!=null;c=c.getNextSibling())
				{		
				switch(c.getNodeType())
					{
					case Node.COMMENT_NODE: break;
					case Node.TEXT_NODE:verifyEmpty(Text.class.cast(c));break;
					case Node.ELEMENT_NODE:
						{
						array.add(_parse(Element.class.cast(c)));
						break;
						}
					default: throw new IllegalArgumentException("Bad node type");
					}
				}
			return array;
			}
		else if(localName.endsWith("object"))
			{
			JsonObject object=jsonFactory.newObject();
			for(Node c=n.getFirstChild();c!=null;c=c.getNextSibling())
				{
				switch(c.getNodeType())
					{
					case Node.COMMENT_NODE: break;
					case Node.TEXT_NODE:verifyEmpty(Text.class.cast(c));break;
					case Node.ELEMENT_NODE:
						{
						Element E=Element.class.cast(c);
						Attr att=E.getAttributeNode("name");
						if(att==null) throw new IllegalArgumentException("@name missing in object"); 
						object.put(att.getValue(),_parse(E));
						break;
						}
					default: throw new IllegalArgumentException("Bad node type while parsing obejct:"+c.getNodeType());
					}
				}
			return object;
			}
		else
			{
			throw new IllegalArgumentException("Bad node "+localName);
			}
		}	
	private String _content(Node root)
		{
		StringBuilder b=new StringBuilder();
		for(Node c=root.getFirstChild();c!=null;c=c.getNextSibling())
			{	
			switch(c.getNodeType())
				{
				case Node.COMMENT_NODE: break;
				case Node.TEXT_NODE: b.append(Text.class.cast(c).getData());break;
				default: throw new IllegalArgumentException("Bad node type in content "+root.getNodeName());
				}
			}
		return b.toString();
		}
	
	private void verifyEmpty(Text txt)
		{
		if(!txt.getData().trim().isEmpty())
			{
			throw new IllegalArgumentException("non-empty string in xml data.");
			}
		}
	}
