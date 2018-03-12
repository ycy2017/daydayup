package trydemo.httpframe;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;

public class httpclientget {

	public static void main(String[] args) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpGet httpget = new HttpGet("http://localhost:8080/prj_mas_dtc_2016/rest/ycy/get?urlParameter=aaaaaaaaaaaaaaaaaaaa");
		
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//�򿪵��� URL �����Ӳ�����һ�����ڴӸ����Ӷ���� InputStream��        
		        Reader reader = new InputStreamReader(new BufferedInputStream(entity.getContent()),"utf-8");     
		        int c;  
		        while ((c = reader.read()) != -1) {        
	                System.out.print((char) c);        
	        }        
	        reader.close();    
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
