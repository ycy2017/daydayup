package trydemo.httpframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class httpclientpost {

	public static void main(String[] args) {
		
		 String url = "http://localhost:8080/prj_mas_dtc_2016/rest/ycy/post?urlParameter=aaaaaaaaaaaaaaaaaaaa";
        //����httpclient����  
        CloseableHttpClient client = HttpClients.createDefault();  
        //����post��ʽ�������  
        HttpPost httpPost = new HttpPost(url);  
        //ִ��������������õ����ͬ������  
        CloseableHttpResponse response;
		try {
			/* 
			 1��������ʹ��NameValuePairװ���������(��ֵ��)
			//ָ������ͷ��Content-type������User-Agent��  
	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
		    //װ����� ,�����ò������������  
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        nvps.add(new BasicNameValuePair("postParameter","postParameter2"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));  
	        */
			//2��������ʹ��StringEntityװ���������(�ַ�)
	        StringEntity entity =new StringEntity("&postParameter=bbbbbbbbbbbbbbb");
	        //ָ������ͷ��Content-type������User-Agent��  
	        entity.setContentType("application/x-www-form-urlencoded");
	        //װ����� ,�����ò������������  
	        httpPost.setEntity(entity);
	        System.out.println("����ʵ��: " + entity);

	        response = client.execute(httpPost);
			//��ȡ���ʵ��  
	        HttpEntity responseEntity = response.getEntity();  
	        if (responseEntity != null) {  
	            //��ָ������ת�����ʵ��ΪString����  
	            System.out.println(EntityUtils.toString(responseEntity, "utf-8")); 
	        }  
	        EntityUtils.consume(responseEntity);  
	        //�ͷ�����  
	        response.close();  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}
	
	
	
	
	
	
	
	
}
