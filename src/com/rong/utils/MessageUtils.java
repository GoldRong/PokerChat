package com.rong.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.rong.model.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 消息处理工具
 * 
 * @author xurong
 *
 */
public class MessageUtils {
	/**
	 * xml转化成Map
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Map<String, String> xml2Map(InputStream is) throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		is.close();
		return map;
	}
	/**
	 * 把文本消息转化成xml
	 * @return
	 */
	public static String textMessage2Xml(TextMessage textMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", textMessage.getClass());
		System.out.println("-------------"+textMessage.toString()+"---------------");
		return xStream.toXML(textMessage);
	}
}
