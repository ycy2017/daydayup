package trydemo.httpframe;

import org.apache.http.entity.StringEntity;

public class StringEntityAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   // ������Ϣʵ��
        StringEntity entity = new StringEntity(  "application/x-www-form-urlencoded","gbk");
        System.out.println(entity);
	}

}
