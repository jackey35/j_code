package com.jack.fo.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class WXRequestUtil {
	//xml解析    
    public static SortedMap<String, String> doXMLParseWithSorted(String strxml) throws Exception {    
          strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");    
          if(null == strxml || "".equals(strxml)) {    
              return null;    
          }    
              
          SortedMap<String,String> m = new TreeMap<String,String>();     
          InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));    
          SAXBuilder builder = new SAXBuilder();    
          Document doc = builder.build(in);    
          Element root = doc.getRootElement();    
          List list = root.getChildren();    
          Iterator it = list.iterator();    
          while(it.hasNext()) {    
              Element e = (Element) it.next();    
              String k = e.getName();    
              String v = "";    
              List children = e.getChildren();    
              if(children.isEmpty()) {    
                  v = e.getTextNormalize();    
              } else {    
                  v = getChildrenText(children);    
              }    
                  
              m.put(k, v);    
          }    
              
          //关闭流    
          in.close();     
          return m;    
    }   
    
    public static String getChildrenText(List children) {    
        StringBuffer sb = new StringBuffer();    
        if(!children.isEmpty()) {    
            Iterator it = children.iterator();    
            while(it.hasNext()) {    
                Element e = (Element) it.next();    
                String name = e.getName();    
                String value = e.getTextNormalize();    
                List list = e.getChildren();    
                sb.append("<" + name + ">");    
                if(!list.isEmpty()) {    
                    sb.append(getChildrenText(list));    
                }    
                sb.append(value);    
                sb.append("</" + name + ">");    
            }    
        }     
        return sb.toString();    
  }  
}
