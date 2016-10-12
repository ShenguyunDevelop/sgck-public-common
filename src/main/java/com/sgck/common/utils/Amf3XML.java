package com.sgck.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXWriter;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.DOMReader;
import flex.messaging.io.amf.ASObject;

public class Amf3XML
{

	public boolean m_bWriteBinary = true;

	/**
	 * Convert byte array to HEX String
	 *
	 * @param inData header address of byte array.
	 *               return output Hex String, the length = InData.length() * 2)
	 */
	protected static String BytesToHex(byte[] inData)
	{
		String outHexStr = new String();
		int nInLen = inData.length;
		for (int i = 0; i < nInLen; i++)
		{
			byte ch = inData[i];
			int lo = (byte) (ch & 0xF);
			int hi = (byte) (ch >> 4) & 0xF;

			outHexStr += (char) (hi > 9 ? ('A' + hi - 10) : ('0' + hi));
			outHexStr += (char) (lo > 9 ? ('A' + lo - 10) : ('0' + lo));
		}
		return outHexStr;
	}

	/**
	 * Convert HEX String to byte array
	 *
	 * @param inHexStr the Hex String .
	 * @reutrn the byte[].
	 */
	protected static byte[] HexToBytes(String inHexStr)
	{
		int outlen = inHexStr.length() / 2;
		byte[] outBytes = new byte[outlen];
		byte c = 0, c1 = 0;

		for (int i = 0; i < outlen; i++)
		{
			char p1 = inHexStr.charAt(i * 2);
			char p2 = inHexStr.charAt(i * 2 + 1);
			switch (p1)
			{
				case '0':
					c = 0;
					break;
				case '1':
					c = 1;
					break;
				case '2':
					c = 2;
					break;
				case '3':
					c = 3;
					break;
				case '4':
					c = 4;
					break;
				case '5':
					c = 5;
					break;
				case '6':
					c = 6;
					break;
				case '7':
					c = 7;
					break;
				case '8':
					c = 8;
					break;
				case '9':
					c = 9;
					break;
				case 'a':
				case 'A':
					c = 10;
					break;
				case 'b':
				case 'B':
					c = 11;
					break;
				case 'c':
				case 'C':
					c = 12;
					break;
				case 'd':
				case 'D':
					c = 13;
					break;
				case 'e':
				case 'E':
					c = 14;
					break;
				case 'f':
				case 'F':
					c = 15;
					break;
				default:
					return null;
			}

			c1 = c;

			switch (p2)
			{
				case '0':
					c = 0;
					break;
				case '1':
					c = 1;
					break;
				case '2':
					c = 2;
					break;
				case '3':
					c = 3;
					break;
				case '4':
					c = 4;
					break;
				case '5':
					c = 5;
					break;
				case '6':
					c = 6;
					break;
				case '7':
					c = 7;
					break;
				case '8':
					c = 8;
					break;
				case '9':
					c = 9;
					break;
				case 'a':
				case 'A':
					c = 10;
					break;
				case 'b':
				case 'B':
					c = 11;
					break;
				case 'c':
				case 'C':
					c = 12;
					break;
				case 'd':
				case 'D':
					c = 13;
					break;
				case 'e':
				case 'E':
					c = 14;
					break;
				case 'f':
				case 'F':
					c = 15;
					break;
				default:
					return null;
			}

			outBytes[i] = (byte) ((c1 << 4) + c);
		}

		// String s = BytesToHex(outBytes);
		return outBytes;
	}

	public Object readObject(String strFileName) throws IOException, DocumentException
	{
		InputStream in = new FileInputStream(new File(strFileName));
		return readObject(in);
	}

	public Object readObjectFromString(String input) throws DocumentException
	{
		Reader rr = new StringReader(input);
		return readObject(rr);
	}

	public Object readObject(InputStream in) throws DocumentException
	{
		SAXReader saxReader = new SAXReader();

		Document doc = null;
		doc = saxReader.read(in);
//		Document doc = saxReader.read(new File("D:/Grusen/TEST/amf_xml/AmfExample.xml"));
		Element root = doc.getRootElement();
		QName qname = root.getQName();
		if (qname.getNamespace().getPrefix() == "AM" && qname.getName() == "ROOT")
		{
			Element next = (Element) root.elementIterator().next();
			return readObjectImpl(next);
		}
		else
		{
			return readObjectImpl2(root);
		}
	}

	public Object readObject(Reader in) throws DocumentException
	{
		SAXReader saxReader = new SAXReader();

		Document doc = null;
		doc = saxReader.read(in);
//		Document doc = saxReader.read(new File("D:/Grusen/TEST/amf_xml/AmfExample.xml"));
		Element root = doc.getRootElement();
		QName qname = root.getQName();
		if (qname.getNamespace().getPrefix() == "AM" && qname.getName() == "ROOT")
		{
			Element next = (Element) root.elementIterator().next();
			return readObjectImpl(next);
		}
		else
		{
			return readObjectImpl2(root);
		}
	}

	void writeObject(String strFileName, Object obj) throws IOException
	{
		writeObject(strFileName, obj, XML_MODE.MODE_FAST);
	}

	void writeObject(Writer out, Object obj) throws IOException
	{
		writeObject(out, obj, XML_MODE.MODE_FAST);
	}

	void writeObject(String strFileName, Object obj, XML_MODE mode) throws IOException
	{
		FileWriter writer = new FileWriter(strFileName);
		writeObject(writer, obj, mode);
	}

	public void writeObject(Writer out, Object obj, XML_MODE mode) throws IOException
	{
		OutputFormat format = new OutputFormat("    ", true);
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(out, format);

		Document doc = DocumentHelper.createDocument();
		//doc.addProcessingInstruction("xml-stylesheet", "type='text/xsl href='students.xsl'");

		if (mode == XML_MODE.MODE_FAST)
		{
			writeObjectImpl2(doc, obj, "ROOT");
			Element r = doc.getRootElement();
			if (null != r)
			{
				doc.getRootElement().addAttribute("AM_VER", "2");
			}
		}
		else if (mode == XML_MODE.MODE_PROP)
		{
			writeObjectImplProp(doc, obj, "ROOT", true);
			Element r = doc.getRootElement();
			if (null != r)
			{
				doc.getRootElement().addAttribute("AM_VER", "2");
			}
		}
		else
		{
			throw new IOException("invalid XML_MODE");
		}

		xmlWriter.write(doc);
		xmlWriter.close();
	}

	protected Object readObjectImpl(Element parent)
	{
		if (parent.getNamespacePrefix() != "AM")
		{
			return null;
		}

		String name = parent.getName().toString();
		int len = name.length();
		if (len != 1)
		{
			return null;
		}

		char type = name.charAt(0);
		switch (type)
		{
			case 'N':	// Number
			{
				double val = Double.parseDouble(parent.getText());
				int v_i = (int) val;
				if (v_i == val)
				{// auto convert to int32
					return v_i;
				}
				else
				{// convert to double
					return val;
				}
				//break;
			}
			case 'S':	// String
			{
				return parent.getText();
			}
			//break;
			case 'A':	// Array
			{
				ArrayList<Object> arr = new ArrayList<Object>();
				for (Iterator i = parent.elementIterator(); i.hasNext();)
				{
					Element next_elm = (Element) i.next();
					Object obj = readObjectImpl(next_elm);
					if (obj != null)
					{
						arr.add(obj);
					}
				}

				return arr;
			}
			//break;
			case 'B':	// Boolean
			{
				String txt = parent.getTextTrim();
				if (txt.compareToIgnoreCase("true") == 0 || txt == "1")
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			//break;
			case 'O':	// Object
			{
				//Hashtable<String, Object> O = new Hashtable<String, Object>();
				ASObject O = new ASObject();
				for (Iterator i = parent.elementIterator(); i.hasNext();)
				{
					Element next_elm = (Element) i.next();
					String key = next_elm.attribute("name").getText();
					Object obj = readObjectImpl(next_elm);
					if (obj != null)
					{
						O.put(key, obj);
					}
				}

				return O;
			}
			// break;
			case 'T':	// Datetime
			{//
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				try
				{
					Date t = fmt.parse(parent.getText());
					// String xx = fmt.format(t);
					return t;
				}
				catch (ParseException e)
				{
					// 
					e.printStackTrace();
				}
				return null;
			}
			//break;
			case 'X':	// XML
				break;
			case 'C':	// ByteArray
			{
				return HexToBytes(parent.getTextTrim());
			}
			//break;
		}

		return null;
	}

	// 鍒ゆ柇瀛楃涓蹭腑鏄惁鍖呭惈鏈塜ML鑺傜偣淇℃伅
	private static boolean is_text_has_entity(String str)
	{
		// 
		if (null == str)
		{
			return false;
		}

		Pattern p = Pattern.compile("[<>]");
		Matcher m = p.matcher(str);
		boolean has = m.find();
		if (has)
		{
			return true;	// 濡傛灉鏈変互涓婄殑瀛楃锛屽垯琛ㄧず鍖呭惈鑺傜偣
		}

		int len = str.length();
		// 濡傛灉鍖呭惈 "\\" 鎴栬� "\""琛ㄧず鏈夎浆涔夌
		int pos = str.indexOf('\\');
		if (len != -1)
		{
			pos++;
			if (pos < len)
			{
				char ch = str.charAt(pos);
				if (ch == '\'' || ch == '"')
				{
					return true;
				}
			}
		}
		return false;
	}

	////////////////////////////////////////////////////////////////////////////////////
	// version 2
	////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings ({"unchecked"})
	protected Object readObjectImpl2(Element parent)
	{
		String typeName = parent.attributeValue("T");
		char type = 'S'; // 榛樿涓哄瓧绗︿覆绫诲瀷
		if (null == typeName || typeName.isEmpty())
		{
			if (parent.elementIterator().hasNext()
					|| parent.attributeCount() > 0)
			{
				type = 'O'; // 鏈夊瓙鑺傜偣锛屾寜瀵硅薄鐪嬪緟
			}
		}
		else
		{
			type = typeName.charAt(0);
		}

		switch (type)
		{
			case 'N': // Number
			{
				try
				{
					double val = Double.parseDouble(parent.getText());
					int v_i = (int) val;
					if (v_i == val)
					{// auto convert to int32
						return v_i;
					}
					else
					{// convert to double
						return val;
					}
				}
				catch (Exception e)
				{
					return 0;
				}
			}
			case 'S': // String
			{
				return parent.getText();
			}
			case 'A': // Array
			{
				ArrayList<Object> arr = new ArrayList<Object>();
				for (Iterator i = parent.elementIterator(); i.hasNext();)
				{
					Element next_elm = (Element) i.next();
					Object obj = readObjectImpl2(next_elm);
					if (obj != null)
					{
						arr.add(obj);
					}
				}

				return arr;
			}
			case 'B': // Boolean
			{
				String txt = parent.getTextTrim();
				if (txt.compareToIgnoreCase("true") == 0 || txt == "1")
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			case 'O': // Object
			{
				// 濡傛灉璁や负鏄疧bject锛屽垯瀵瑰瓙鍏冪礌鍒嗙被鍒楀嚭
				// 鎵�湁灞炴�鍒椾负瀵硅薄鐨勫睘鎬�
				ASObject O = new ASObject();
				for (Iterator i = parent.attributeIterator(); i.hasNext();)
				{
					Attribute attr = (Attribute) i.next();
					String key = attr.getName();
					if (key == "T" || key == "N" || key == "AM_VER")
					{
						continue;
					}
					O.put(key, attr.getText());
				}

				// 鎵�湁瀛愬厓绱犲垯鎸夌収鍚嶇О鍒嗙粍锛屾瘡缁勪负涓�釜鏁扮粍
				for (Iterator i = parent.elementIterator(); i.hasNext();)
				{
					Element next_elm = (Element) i.next();
					String key = next_elm.getName(); // attribute("name").getText();
					Object obj = readObjectImpl2(next_elm);

					// 濡傛灉鏈夊悓鍚嶇殑瀵硅薄锛屽垯杞负涓�釜鏁扮粍锛屽惁鍒欏氨鏄竴涓嫭绔嬪璞�
					Object item = O.get(key);
					if (null == item)
					{
						O.put(key, obj); // 绗竴娆℃彃鍏�
					}
					else
					{
						ArrayList arr;
						if (item instanceof ArrayList)
						{// 绗琋娆℃彃鍏ユ椂锛岀洿鎺ヨ幏鍙栨暟缁勫璞″嵆鍙�
							arr = (ArrayList) item;
						}
						else
						{// 绗簩娆℃彃鍏ワ紝鍒涘缓涓�釜鏁扮粍锛屽苟灏嗗師瀵硅薄鎻掑叆鍒版暟缁勪腑鍘�
							arr = new ArrayList();
							arr.add(item);
							O.put(key, arr);
						}
						arr.add(obj);
					}
				}

				return O;
			}
			case 'T': // Datetime
			{//
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				try
				{
					Date t = fmt.parse(parent.getText());
					// String xx = fmt.format(t);
					return t;
				}
				catch (ParseException e)
				{
					//
					e.printStackTrace();
				}
				return null;
			}
			case 'X': // XML
				break;
			case 'C': // ByteArray
			{
				return HexToBytes(parent.getTextTrim());
			}
		}

		return null;
	}

	protected void writeObjectImpl2(Branch parent, Object o, String strName) throws IOException
	{
		if (o == null)
		{
			return;
		}

		if (null == strName)
		{
			strName = "Unknow";
		}

		if (o instanceof String || o instanceof Character)		// String
		{
			String s = o.toString();
			Amf3XML.insertString(parent, strName, s, 'S', true);
		}
		else if (o instanceof Number)										// Number
		{
			Amf3XML.insertString(parent, strName, o.toString(), 'N', true);
		}
		else if (o instanceof Boolean)										// Boolean
		{
			Amf3XML.insertString(parent, strName, o.toString(), 'B', true);
		}
		// We have a complex type...
		else if (o instanceof Date)											// Date
		{
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			String s = fmt.format((Date) o);
			Amf3XML.insertString(parent, strName, s, 'T', true);
		}
		else if (o instanceof Calendar)
		{
			o = ((Calendar) o).getTime();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			String s = fmt.format((Date) o);
			Amf3XML.insertString(parent, strName, s, 'T', true);
		}
//        else if (o instanceof Document)
//        {
//            out.write(kXMLType); // Legacy flash.xml.XMLDocument Type
//            String xml = documentToString(o);
//            writeAMFUTF(xml);
//        } 
		else
		{// We have an Object or Array type...
			Element elm = parent.addElement(strName);
			Class cls = o.getClass();

			if (o instanceof Map)													// Map, ASObject
			{
				Map map = (Map) o;
				Set keySet = map.keySet();
				elm.addAttribute("T", "O");
				elm.addAttribute("N", String.valueOf(keySet.size()));
				Iterator it = keySet.iterator();
				while (it.hasNext())
				{
					Object key = it.next();
					if (key != null)
					{
						String propName = key.toString();
						writeObjectImpl2(elm, map.get(key), propName);
					}
				}
			}
			else if (o instanceof Collection)									// Array
			{
				writeObjectArray(elm, ((Collection) o).toArray());
			}
			else if (cls.isArray())
			{
				writeArray2XML(elm, o, cls.getComponentType());
			}
			else
			{
				//writeCustomObject(o);
				throw new IOException("object type not support!");
			}
		}
	}

	/**
	 * @throws IOException
	 * @exclude
	 */
	protected void writeArray2XML(Element elm, Object o, Class componentType) throws IOException
	{
		if (componentType.isPrimitive())
		{
			writePrimitiveArray(elm, o);
		}
		else if (componentType.equals(Byte.class))		// Byte[] ==> Array
		{
			Byte[] BB = (Byte[]) o;
			byte[] bb = new byte[BB.length];
			for (int i = 0; i < BB.length; i++)
			{
				Byte b = BB[i];
				if (b == null)
				{
					bb[i] = (0);
				}
				else
				{
					bb[i] = (b.byteValue());
				}
			}

			writeByteArray(elm, bb);
		}
		else if (componentType.equals(Character.class))	// Character[] ==> String
		{
			Character[] ca = (Character[]) o;
			int length = ca.length;
			char[] chars = new char[length];
			for (int i = 0; i < length; i++)
			{
				Character c = ca[i];
				if (c == null)
				{
					chars[i] = 0;
				}
				else
				{
					chars[i] = ca[i].charValue();
				}
			}

			// 姣旇緝鐗规畩锛屽瓧绗︿覆鍐欏叆鏃讹紝鍙兘鎸夊睘鎬э紝涔熷彲鑳戒娇鐢ㄥ瓙鑺傜偣鐨勬柟寮�
			// 鍥犱负鍙兘涓嶉渶瑕佸缓绔嬪瓙Element锛屾墍浠ヨ灏嗗缓濂界殑Element鍒犻櫎鎺�
			// 鐢盇mf3XML.insertString(...)鏉ュ喅瀹氭槸鍚﹀垱寤哄瓙鑺傜偣
			String name = elm.getName();
			Element parent = elm.getParent();
			parent.remove(elm);
			Amf3XML.insertString(parent, name, chars.toString(), 'S', true);
		}
		else
		{
			writeObjectArray(elm, (Object[]) o);
		}
	}

	protected void writeObjectArray(Element elm, Object[] values) throws IOException
	{
		elm.addAttribute("T", "A");
		elm.addAttribute("N", String.valueOf(values.length));
		for (int i = 0; i < values.length; ++i)
		{
			Object item = values[i];
			writeObjectImpl2(elm, item, "V");
		}
	}

	protected void writeByteArray(Element elm, byte[] ba)
	{
		elm.addAttribute("T", "C");
		elm.addAttribute("N", String.valueOf(ba.length));

		if (m_bWriteBinary)
		{
			String s = BytesToHex(ba);
			elm.setText(s);
		}
		else
		{
			elm.addComment("Byte Array");
		}
	}

	protected void writePrimitiveArray(Element elm, Object obj) throws IOException
	{
		Class aType = obj.getClass().getComponentType();

		if (aType.equals(Character.TYPE)) // String
		{
			// Treat char[] as a String
			char[] c = (char[]) obj;
			// 姣旇緝鐗规畩锛屽瓧绗︿覆鍐欏叆鏃讹紝鍙兘鎸夊睘鎬э紝涔熷彲鑳戒娇鐢ㄥ瓙鑺傜偣鐨勬柟寮�
			// 鍥犱负鍙兘涓嶉渶瑕佸缓绔嬪瓙Element锛屾墍浠ヨ灏嗗缓濂界殑Element鍒犻櫎鎺�
			// 鐢盇mf3XML.insertString(...)鏉ュ喅瀹氭槸鍚﹀垱寤哄瓙鑺傜偣
			String name = elm.getName();
			Element parent = elm.getParent();
			parent.remove(elm);
			Amf3XML.insertString(parent, name, c.toString(), 'S', true);
		}
		else if (aType.equals(Byte.TYPE)) // ByteArray
		{
			writeByteArray(elm, (byte[]) obj);
		}
		else
		{
			if (aType.equals(Boolean.TYPE)) // boolean[] ==> Array
			{
				boolean[] b = (boolean[]) obj;
				elm.addAttribute("T", "A");
				for (int i = 0; i < b.length; ++i)
				{
					Object item = b[i];
					writeObjectImpl2(elm, item, "V");
				}
			}
			else if (aType.equals(Integer.TYPE) || aType.equals(Short.TYPE))
			{
				// We have a primitive number, either an int or short
				// We write all of these as Integers...
				int length = Array.getLength(obj);
				elm.addAttribute("T", "A");
				for (int i = 0; i < length; ++i)
				{
					int v = Array.getInt(obj, i);
					writeObjectImpl2(elm, v, "V");
				}
			}
			else
			{
				int length = Array.getLength(obj);
				elm.addAttribute("T", "A");
				for (int i = 0; i < length; ++i)
				{
					double v = Array.getDouble(obj, i);
					writeObjectImpl2(elm, v, "V");
				}
			}
		}
	}

	/**
	 * @throws IOException
	 * @exclude
	 */
	protected void writeArray2XMLProp(Element elm, Object o, Class componentType, boolean asElement) throws IOException
	{
		if (componentType.isPrimitive())
		{
			writePrimitiveArrayProp(elm, o, asElement);
		}
		else if (componentType.equals(Byte.class))		// Byte[] ==> ByteArray
		{
			Byte[] BB = (Byte[]) o;
			byte[] bb = new byte[BB.length];
			for (int i = 0; i < BB.length; i++)
			{
				Byte b = BB[i];
				if (b == null)
				{
					bb[i] = (0);
				}
				else
				{
					bb[i] = (b.byteValue());
				}
			}

			writeByteArray(elm, bb);
		}
		else if (componentType.equals(Character.class))	// Character[] ==> String
		{
			Character[] ca = (Character[]) o;
			int length = ca.length;
			char[] chars = new char[length];
			for (int i = 0; i < length; i++)
			{
				Character c = ca[i];
				if (c == null)
				{
					chars[i] = 0;
				}
				else
				{
					chars[i] = ca[i].charValue();
				}
			}
			// 姣旇緝鐗规畩锛屽瓧绗︿覆鍐欏叆鏃讹紝鍙兘鎸夊睘鎬э紝涔熷彲鑳戒娇鐢ㄥ瓙鑺傜偣鐨勬柟寮�
			// 鍥犱负鍙兘涓嶉渶瑕佸缓绔嬪瓙Element锛屾墍浠ヨ灏嗗缓濂界殑Element鍒犻櫎鎺�
			// 鐢盇mf3XML.insertString(...)鏉ュ喅瀹氭槸鍚﹀垱寤哄瓙鑺傜偣
			String name = elm.getName();
			Element parent = elm.getParent();
			parent.remove(elm);
			Amf3XML.insertString(parent, name, chars.toString(), 'S', asElement);
		}
		else
		{
			writeObjectArrayProp(elm, (Object[]) o);
		}
	}

	protected void writeObjectArrayProp(Element elm, Object[] values) throws IOException
	{
		elm.addAttribute("T", "A");
		elm.addAttribute("N", String.valueOf(values.length));
		for (int i = 0; i < values.length; ++i)
		{
			Object item = values[i];
			writeObjectImplProp(elm, item, "V", true);
		}
	}

	protected void writePrimitiveArrayProp(Element elm, Object obj, boolean asElement) throws IOException
	{
		Class aType = obj.getClass().getComponentType();

		if (aType.equals(Character.TYPE)) // String
		{
			// Treat char[] as a String
			char[] c = (char[]) obj;
			// 姣旇緝鐗规畩锛屽瓧绗︿覆鍐欏叆鏃讹紝鍙兘鎸夊睘鎬э紝涔熷彲鑳戒娇鐢ㄥ瓙鑺傜偣鐨勬柟寮�
			// 鍥犱负鍙兘涓嶉渶瑕佸缓绔嬪瓙Element锛屾墍浠ヨ灏嗗缓濂界殑Element鍒犻櫎鎺�
			// 鐢盇mf3XML.insertString(...)鏉ュ喅瀹氭槸鍚﹀垱寤哄瓙鑺傜偣
			String name = elm.getName();
			Element parent = elm.getParent();
			parent.remove(elm);
			Amf3XML.insertString(parent, name, c.toString(), 'S', asElement);
		}
		else if (aType.equals(Byte.TYPE)) // ByteArray
		{
			writeByteArray(elm, (byte[]) obj);
		}
		else
		{
			if (aType.equals(Boolean.TYPE)) // boolean[] ==> Array
			{
				boolean[] b = (boolean[]) obj;
				elm.addAttribute("T", "A");
				for (int i = 0; i < b.length; ++i)
				{
					Object item = b[i];
					writeObjectImplProp(elm, item, "V", true);
				}
			}
			else if (aType.equals(Integer.TYPE) || aType.equals(Short.TYPE))
			{
				// We have a primitive number, either an int or short
				// We write all of these as Integers...
				int length = Array.getLength(obj);
				elm.addAttribute("T", "A");
				for (int i = 0; i < length; ++i)
				{
					int v = Array.getInt(obj, i);
					writeObjectImplProp(elm, v, "V", true);
				}
			}
			else
			{
				int length = Array.getLength(obj);
				elm.addAttribute("T", "A");
				for (int i = 0; i < length; ++i)
				{
					double v = Array.getDouble(obj, i);
					writeObjectImplProp(elm, v, "V", true);
				}
			}
		}
	}

	private static void insertString(Branch elm, String name, String str, char type, boolean asElement)
	{
		if (is_text_has_entity(str))
		{
			Element sub = elm.addElement(name);
			sub.addCDATA(str);
			sub.addAttribute("T", String.valueOf(type));
		}
		else if (asElement || !(elm instanceof Element))
		{
			Element sub = elm.addElement(name);
			sub.setText(str);
			sub.addAttribute("T", String.valueOf(type));
		}
		else
		{
			((Element) elm).addAttribute(name, str);
		}
	}

	protected void writeObjectImplProp(Branch parent, Object o, String strName, boolean asElement) throws IOException
	{
		if (o == null)
		{
			return;
		}

		if (null == strName)
		{
			strName = "Unknow";
		}

//    	Element elm = parent.addElement(strName);
		if (o instanceof String || o instanceof Character)		// String
		{
			String s = o.toString();
			Amf3XML.insertString(parent, strName, s, 'S', asElement);
		}
		else if (o instanceof Number)										// Number
		{
			Amf3XML.insertString(parent, strName, o.toString(), 'N', asElement);
		}
		else if (o instanceof Boolean)										// Boolean
		{
			Amf3XML.insertString(parent, strName, o.toString(), 'B', asElement);
		}
		// We have a complex type...
		else if (o instanceof Date)											// Date
		{
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			String s = fmt.format((Date) o);
			Amf3XML.insertString(parent, strName, s, 'T', asElement);
		}
		else if (o instanceof Calendar)
		{
			o = ((Calendar) o).getTime();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			String s = fmt.format((Date) o);
			Amf3XML.insertString(parent, strName, s, 'T', asElement);
		}
//        else if (o instanceof Document)
//        {
//            out.write(kXMLType); // Legacy flash.xml.XMLDocument Type
//            String xml = documentToString(o);
//            writeAMFUTF(xml);
//        } 
		else
		{// We have an Object or Array type...
			Element elm = parent.addElement(strName);
			Class cls = o.getClass();

			if (o instanceof Map)													// Map, ASObject
			{
				boolean is_root = (parent.getDocument() == parent);
				Map map = (Map) o;
				Set keySet = map.keySet();

				elm.addAttribute("T", "O");
				elm.addAttribute("N", String.valueOf(keySet.size()));

				Iterator it = keySet.iterator();
				while (it.hasNext())
				{
					Object key = it.next();
					if (key != null)
					{
						String propName = key.toString();
						writeObjectImplProp(elm, map.get(key), propName, is_root);
					}
				}
			}
			else if (o instanceof Collection)									// Array
			{
				writeObjectArrayProp(elm, ((Collection) o).toArray());
			}
			else if (cls.isArray())
			{
				writeArray2XMLProp(elm, o, cls.getComponentType(), asElement);
			}
			else
			{
				//writeCustomObject(o);
				throw new IOException("object type not support!");
			}
		}
	}
}
