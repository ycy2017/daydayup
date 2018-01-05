package httpframe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class netget {

	public static void main(String[] args) {

		URL url;
		BufferedReader in = null;
		String result = "";
		try {
			url = new URL("http://localhost:8080/prj_mas_dtc_2016/rest/ycy/get?urlParameter=aaaaaaaaaaaaaaaaaaaa");
			// �򿪺�URL֮�������
			HttpURLConnection connHttp = (HttpURLConnection) url.openConnection();
			connHttp.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			connHttp.setRequestMethod("GET");
			connHttp.setDoOutput(true);
			connHttp.setDoInput(true);
			connHttp.connect();
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connHttp.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
