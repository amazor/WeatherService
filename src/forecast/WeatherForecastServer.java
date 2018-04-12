package forecast;
// Author: Amir Mazor
// Date: 4/11/18
// Simple Weather Servicing System
//
// This Program simulates a web service on the command line to receive
// weather information either in the future (forecast) or past (history).
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherForecastServer {
	
	public static final String SERVER_GET_FORECAST_STRING =
			"/WeatherForecastServer/?action=getForecast&";
	
	public static final String SERVER_RUN =
			"/WeatherForecastServer/?action=run";
	
	
	public static void main(String[] args) {	
		// holds weather information in memory
		ArrayList<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
		Scanner sc = new Scanner(System.in);
		WeatherUndergroundNetwork connection = new WeatherUndergroundNetwork();
		GetRequestParser parser = new GetRequestParser();
		String line = null;
		
		System.out.println(" This Program simulates a web service on the command line to receive\n" + 
				" weather information either in the future (forecast) or past (history).\n");
		System.out.println("To use this program, enter the following commands:\n\n"
				+ "\"/WeatherForecastServer/?action=getForecast&days=<INTEGER>&state=<STATE>&city=<CITY>\"\n"
				+ "\"/WeatherForecastServer/?action=run\"\n\n");
		System.out.println("NOTE: Enter any negative integer or positive integer up to 10 for <INTEGER>\n"
				+ "The abbreviation of the state for <STATE>\n"
				+ "A city name for <CITY>\n");
		System.out.println("EXAMPLE: /WeatherForecastServer/?action=getForecast&days=3&state=CA&city=San_Francisco\n\n");
		
		while(sc.hasNext()){
            if((line = sc.nextLine()).startsWith(SERVER_GET_FORECAST_STRING)) {
            	//getForecast
            	parser.parse(line.substring(SERVER_GET_FORECAST_STRING.length()));
            	try {
            		//gets weather information and adds to the arraylist
					weatherInfos.add(weatherInfos.size(),
							connection.getForecast(
									parser.getDays(),
									parser.getState(),
									parser.getCity()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            } else if(line.equals(SERVER_RUN)) {
            	//run
            	for(int i = weatherInfos.size()-1; i >= 0; i--) {
            		//prints out information from arraylist then removes it
            		System.out.println(weatherInfos.get(i) + "\n");
            		weatherInfos.remove(i);
            	}
            	
            }
        } 
        sc.close();
	}

}
