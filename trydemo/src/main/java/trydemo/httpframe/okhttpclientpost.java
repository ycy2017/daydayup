package trydemo.httpframe;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class okhttpclientpost {

	public static void main(String[] args) {

		String url = "http://localhost:8080/prj_mas_dtc_2016/rest/ycy/post?urlParameter=aaaaaaaaaaaaaaaaaaaa";
		String json = "postParameter=����bbbbbbbbbbbbb}";
		OkHttpClient client = new OkHttpClient();
		MediaType contype = MediaType.parse("application/x-www-form-urlencoded");
		// ����������
		RequestBody body = RequestBody.create(contype, json);

		Request request = new Request.Builder().url(url).post(body).build();
		Response response;
		try {
			Call call = client.newCall(request);
			response = call.execute();
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
