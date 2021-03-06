package io.github.wyparks2.rxjava.chapter05.examples;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.wyparks2.rxjava.common.CommonUtils;
import io.github.wyparks2.rxjava.common.Log;
import io.github.wyparks2.rxjava.common.OkHttpHelper;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class OpenWeatherMapV2 {
	private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=";
	
	public void run() { 
		CommonUtils.exampleStart();

		Observable<String> source = Observable.just(URL + CommonUtils.API_KEY)
				.map(OkHttpHelper::getWithLog)
				.subscribeOn(Schedulers.io())
				.share()
				.observeOn(Schedulers.newThread());
		
		source.map(this::parseTemperature).subscribe(Log::it);
		source.map(this::parseCityName).subscribe(Log::it);
		source.map(this::parseCountry).subscribe(Log::it);
		
		CommonUtils.sleep(1000);
	}
	
	private String parseTemperature(String json) { 
		return parse(json, "\"temp\":[0-9]*.[0-9]*");
	}

	private String parseCityName(String json) { 
		return parse(json, "\"name\":\"[a-zA-Z]*\"");
	}

	private String parseCountry(String json) { 
		return parse(json, "\"country\":\"[a-zA-Z]*\"");
	}
	
	private String parse(String json, String regex) { 
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(json);
		if (match.find()) {
			return match.group();
		}
		return "N/A";				
	}
	
	public static void main(String[] args) { 
		OpenWeatherMapV2 demo = new OpenWeatherMapV2();
		demo.run();
	}	
}
