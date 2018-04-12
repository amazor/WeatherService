package forecast;

//A simple class representing the necessary weather information to return to user
public class WeatherInfo {

	public String date, weather_description, max_temperature;
	
	public WeatherInfo() {
		date = null;
		weather_description = null;
		max_temperature = null;
	}
	
	public String toString() {
        return "date: " + date + "\ndescription: " + weather_description + "\nmax temperature: " + max_temperature;
        }
	
	
}
