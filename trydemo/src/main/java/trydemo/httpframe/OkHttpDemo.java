package trydemo.httpframe;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpDemo {

	public static void main(String[] args) {
		String [] httpArr = new String[]{"http://f.apiplus.net/fc3d-1.json","http://f.apiplus.net/pl3-1.json","http://f.apiplus.net/hljtc6j1-1.json"};
        //�������ӿͻ���
        OkHttpClient client = new OkHttpClient();
        //�����ύ����(post)
        FormBody form = new FormBody.Builder()
                .add("rows", "1")//��Ӳ���ļ�ֵ��
                .build();
    
        for(int i = 0 ;i<httpArr.length;i++){
        	   Request request = new Request.Builder()
                       .url(httpArr[i])//��ַ
                       .post(form)//��post���ò���;
                       .build();
        	 //����"����" ����
            Call call = client.newCall(request);
            
            //����Callback(�ӿ�) ���ʧ�ܻص�onFailure����,�������Ӧ����onResponse����
            call.enqueue(new Callback() {
            	
                public void onFailure(Call call, IOException e) {
                	System.out.println("�߳�"+Thread.currentThread().getName());
                    //����ʧ��, ��ӡ���ʵ�ַ
                    Request r = call.request();
                    System.out.println("����ʧ��: " + r.url());
                    System.out.println(r.body());
                }

                public void onResponse(Call call, Response response) throws IOException {
                	System.out.println("�߳�"+Thread.currentThread().getName());
                    System.out.println("��������Ӧ" + call.request().url());
                    System.out.println("��Ӧ��: " + response.code());
                    System.out.println("��������: " + response.body().string());
                }
            });
        }
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        
    }
	
}
