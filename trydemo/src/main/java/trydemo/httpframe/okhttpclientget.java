package trydemo.httpframe;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class okhttpclientget {
	
	public static void main(String[] args) {

		String url = "http://localhost:8080/prj_mas_dtc_2016/rest/ycy/get?urlParameter=aaaaaaaaaaaaaaaaaaaa";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		request.headers( "application/x-www-form-urlencoded");
		Response response;
		try {
			Call call  = client.newCall(request);
			response = call.execute();
			System.out.println(response.body().string());
			System.out.println(response.headers().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
