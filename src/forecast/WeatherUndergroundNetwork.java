package forecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// This class acts as the getter of information from the network
// as well as the parser
public class WeatherUndergroundNetwork {
	
	// getForecast
	// This function returns weather information from a city
	// within the united states at a specific day.
	//
	// Arguments -
	//
	//	int days: 		the number of days away from the current day to
	//					gather information from. Can be any negative
	//					number and any positive number up to and including 10.
	//
	//  String state:	the state abbreviation that the city resides in (ex: CA)
	//
	//	String city:	the city from which the request will get information for
	//
	// Returns - 
	// 			WeatherInfo: a class defined in WeatherInfo.java that contains
	//			the following information: date, weather description,
	//			and max temperature
	public WeatherInfo getForecast(int days, String state, String city) throws Exception{
		WeatherInfo winf = new WeatherInfo();
		String getRequestURL;
		String weatherDescription;
		String maxTemperature;
		String dateString;
		
		if (days < 0) {
			// history url is needed
			LocalDate date = LocalDate.now().minusDays(-days);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			dateString = date.format(formatter);

			getRequestURL = "http://api.wunderground.com/api/fa8162aa00c36874/history_" + dateString + "/q/" + state  + "/" + city + ".json";

			String forecast = sendGet(getRequestURL);
		    JsonElement jelement = new JsonParser().parse(forecast);
		    JsonObject  jobject = jelement.getAsJsonObject();
		    
		    weatherDescription = "N/A";
		    
		    maxTemperature = 
		    		jobject
		    		.getAsJsonObject("history")
		    		.getAsJsonArray("dailysummary")
		    		.get(0)
		    		.getAsJsonObject()
		    		.get("maxtempm")
		    		.getAsString();
		    
		    
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dateString = date.format(formatter);
			
		
		} else if (days < 10) {
			//10dayforecast URL is needed
			
			getRequestURL = "http://api.wunderground.com/api/fa8162aa00c36874/forecast10day/q/" + state + "/" + city + ".json";
			
			
			String forecast = sendGet(getRequestURL);
		    JsonElement jelement = new JsonParser().parse(forecast);
		    JsonObject  jobject = jelement.getAsJsonObject();
		    
		    weatherDescription =
		    		jobject
		    		.getAsJsonObject("forecast")
		    		.getAsJsonObject("txt_forecast")
		    		.getAsJsonArray("forecastday")
		    		.get(days*2)
		    		.getAsJsonObject()
		    		.get("fcttext_metric")
		    		.getAsString();
		    
		    maxTemperature = 
		    		jobject
		    		.getAsJsonObject("forecast")
		    		.getAsJsonObject("simpleforecast")
		    		.getAsJsonArray("forecastday")
		    		.get(days)
		    		.getAsJsonObject()
		    		.getAsJsonObject("high")
		    		.get("celsius")
		    		.getAsString();
		    
		    
		    LocalDate date = LocalDate.now().plusDays(days);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dateString = date.format(formatter);
			
			
		} else {
			//invalid number of days
			return null;
		}
	    
	    winf.weather_description = weatherDescription;
	    winf.max_temperature = maxTemperature;
	    winf.date = dateString;
	    		
		return winf;
	}



	// sendGet
	// This function sends a GET request through http for the weather information.
	//
	// Arguments -
	//
	//	String url: the url to send the GET request to
	//
	// Returns - 
	// 			String: a JSON string.
	private String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");;

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();
	}
}