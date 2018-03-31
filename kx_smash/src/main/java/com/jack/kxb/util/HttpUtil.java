package com.jack.kxb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger("HttpUtil");
	private final static int CONNECT_TIMEOUT = 10000; // in milliseconds
	private final static String DEFAULT_ENCODING = "UTF-8";

	public static String postData(String urlStr, String data) {
		return postData(urlStr, data, null);
	}

	public static String postData(String urlStr, String data, String contentType) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(CONNECT_TIMEOUT);
			conn.setDoOutput(true);  
			conn.setDoInput(true);  
			if (contentType != null)
				conn.setRequestProperty("content-type", contentType);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
			if (data == null)
				data = "";
			writer.write(data);
			writer.flush();
			writer.close();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error("Error connecting to " + urlStr + ": " + e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
	
	/** 
	 * 向指定 URL 发送POST方法的请求 
	 *  
	 * @param url 
	 *            发送请求的 URL 
	 * @param param 
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。 
	 * @return 所代表远程资源的响应结果 
	 */  
	public static String sendPost(String url, String param) {  
	    StringBuilder sb = new StringBuilder();  
	    PrintWriter out = null;  
	    BufferedReader in = null;  
	    try {  
	        URL realUrl = new URL(url);  
	        // 打开和URL之间的连接  
	        URLConnection conn = realUrl.openConnection();  
	        // 设置通用的请求属性  
	        conn.setRequestProperty("accept", "*/*");  
	        conn.setRequestProperty("connection", "Keep-Alive");  
	        conn.setRequestProperty("user-agent",  
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
	        // 发送POST请求必须设置如下两行  
	        conn.setDoOutput(true);  
	        conn.setDoInput(true);  
	        // 获取URLConnection对象对应的输出流  
	        out = new PrintWriter(conn.getOutputStream());  
	        // 发送请求参数  
	        out.print(param);  
	        // flush输出流的缓冲  
	        out.flush();  
	        // 定义BufferedReader输入流来读取URL的响应  
	        in = new BufferedReader(  
	                new InputStreamReader(conn.getInputStream()));  
	        String line;  
	        sb = new StringBuilder();  
	        while ((line = in.readLine()) != null) {  
	            sb.append(line);  
	        }  
	    } catch (Exception e) {  
	        System.out.println("发送 POST 请求出现异常！"+e);  
	        e.printStackTrace();  
	    }  
	    //使用finally块来关闭输出流、输入流  
	    finally{  
	        try{  
	            if(out!=null){  
	                out.close();  
	            }  
	            if(in!=null){  
	                in.close();  
	            }  
	        }  
	        catch(IOException ex){  
	            ex.printStackTrace();  
	        }  
	    }  
	    return sb.toString();  
	}   
	
	public static String sendGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url);  
        logger.info("executing request{} " + httpget.getURI());  
        // 执行get请求.    
        CloseableHttpResponse response = null;
        try {  
        		response = httpclient.execute(httpget);

            // 获取响应实体    
            HttpEntity entity = response.getEntity();  
            // 打印响应状态    
            int httpCode = response.getStatusLine().getStatusCode();
            logger.info("url={},httpcode={}",url,httpCode);  
            if (entity != null&&httpCode==200) {  
                // 打印响应内容    
            		String resp = EntityUtils.toString(entity);
            		logger.info("Response content:{} " + resp);  
            		return resp;
            }  
        } catch(Exception e){
        		e.printStackTrace();
        }finally {  
        		if(response!=null) {
					try {
						response.close();
					} catch (IOException e) {
						e.printStackTrace();
					}  
        		}
        }  
        return null;
	}
}
