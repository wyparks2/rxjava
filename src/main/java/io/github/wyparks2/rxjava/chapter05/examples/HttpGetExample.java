package io.github.wyparks2.rxjava.chapter05.examples;

import java.io.IOException;

import io.github.wyparks2.rxjava.common.CommonUtils;
import io.github.wyparks2.rxjava.common.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpGetExample {
	private final OkHttpClient client = new OkHttpClient();
	
	private static final String URL_README = 
			"https://raw.githubusercontent.com/yudong80/rxjava/master/README.md";
	
	public void run() { 
		Request request = new Request.Builder()
	        .url(URL_README)
	        .build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.i(response.body().string());
			}			
		});
		CommonUtils.exampleComplete();
	}
		
	public static void main(String[] args) { 
		HttpGetExample demo = new HttpGetExample();
		demo.run();
	}
}
