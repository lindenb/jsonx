package com.github.lindenb.jsonx.io;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.lindenb.jsonx.JsonArray;
import com.github.lindenb.jsonx.JsonElement;
import com.github.lindenb.jsonx.JsonObject;
import com.github.lindenb.jsonx.JsonPrimitive;

public class JsonPrettyWriter
	{
	int depthshift=4;
	private void ws(PrintWriter out,int n)
		{
		for(int i=0;i< n;++i) out.print(' ');
		}
	/* start of line */
	private void sol(PrintWriter pw)
		{
	
		}
	/* end of line */
	private void eol(PrintWriter pw)
		{
	
		}
	/* comma, then end of line */
	private void comma_eol(PrintWriter pw)
		{
		
		}
	
	public void print(OutputStream out,JsonElement E)
		{
		PrintWriter p=new PrintWriter(out);
		print(p,E);
		p.flush();
		}
	
	
	public String toString(JsonElement E)
		{
		StringWriter sw=new StringWriter();
		PrintWriter p=new PrintWriter(sw);
		print(p,E);
		p.flush();
		return sw.toString();
		}
	
	public void print(PrintWriter out,JsonElement E)
		{
		if(E.isJsonArray())
			{
			JsonArray array=E.getAsJsonArray();
			
			out.write("[");
			for(int i=0;i< array.size();++i)
				{
				if(i>0) out.write(",");
				print(out,array.get(i));
				}
			out.write("]");
			}
		else if(E.isJsonObject())
			{
			JsonObject map=E.getAsJsonObject();
			boolean first=true;
			out.write("{");
			for(String key:map.keySet())
				{
				if(!first) out.write(",");
				first=false;
				quote(out,key);
				out.write(":");
				print(out,map.get(key));
				}
			out.write("}");
			}
		else if(E.isJsonNull())
			{
			out.write("null");
			}
		else if(E.isJsonPrimitive())
			{
			JsonPrimitive p=E.getAsJsonPrimitive();
			if(p.isString())
				{
				quote(out,p.getValue().toString());
				}
			else
				{
				out.write(p.getValue().toString());
				}
			}
		else
			{
			throw new IllegalStateException();
			}
		}
	private void quote(PrintWriter out,String s)
		{
		out.write("\"");
		for(int i=0;i< s.length();++i)
			{
			switch(s.charAt(i))
				{
				case '\b': out.write("\\b");break;
				case '\f': out.write("\\f");break;
				case '\r': out.write("\\r");break;
				case  '\'':out.write("\\\'");break;
				case '\\': out.write("\\\\");break;
				case '\t':out.write("\\t");break;
				case '\n':out.write("\\n");break;
				case '\"':out.write("\\\"");break;
				default:out.write(s.charAt(i));break;
				}
			}
		out.write("\"");
		}
	}
