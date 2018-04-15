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

	public static final String SERVER_GET_FORECAST_STRING = "/WeatherForecastServer/?action=getForecast&";

	public static final String SERVER_RUN = "/WeatherForecastServer/?action=run";

	public static void main(String[] args) {
		// holds weather information in memory
		Scanner sc = new Scanner(System.in);
		WeatherUndergroundNetwork connection = new WeatherUndergroundNetwork();
		GetRequestParser parser = new GetRequestParser();
		String line = null;

		System.out.println(" This Program simulates a web service on the command line to receive\n"
				+ " weather information either in the future (forecast) or past (history).\n");
		System.out.println("To use this program, enter the following commands:\n\n"
				+ "\"/WeatherForecastServer/?action=getForecast&days=<INTEGER>&state=<STATE>&city=<CITY>\"\n"
				+ "\"/WeatherForecastServer/?action=run\"\n\n");
		System.out.println("NOTE: Enter any negative integer or positive integer up to 10 for <INTEGER>\n"
				+ "The abbreviation of the state for <STATE>\n" + "A city name for <CITY>\n");
		System.out
				.println("EXAMPLE: /WeatherForecastServer/?action=getForecast&days=3&state=CA&city=San_Francisco\n\n");

		ArrayList<String> queries = new ArrayList<String>();
		while (sc.hasNext()) {
			if ((line = sc.nextLine()).startsWith(SERVER_GET_FORECAST_STRING)) {
				// getForecast
				queries.add(line);
			} else if (line.equals(SERVER_RUN)) {
				// run
				int size = queries.size();
				for (int i = 0; i < size; i++) {
					parser.parse(queries.get(0).substring(SERVER_GET_FORECAST_STRING.length()));
					try {
						// gets weather information and adds to the arraylist
						System.out.println(connection.getForecast(parser.getDays(), parser.getState(), parser.getCity()));
						System.out.println("---------------------------");

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					queries.remove(0);
				}
			}
		}
		sc.close();
	}

}
