package forecast;

//This class Parses the Arguments in the command line request
public class GetRequestParser {
	private static final String DAY_SEARCH_STRING = "date=";
	private static final String STATE_SEARCH_STRING = "state=";
	private static final String CITY_SEARCH_STRING = "city=";
	
	private int days;
	private String state, city;
	
	//constructor
	public GetRequestParser() {
		days = 0;
		state = "";
		city = "";
	}
	// parse
	// This function parses the given string and assigns the result to
	// the classes fields, should call getters AFTER calling parse()
	//
	// Arguments -
	//
	//	String getRequest: 	the arguments given by the user in the form:
	//							"date=X&state=X&city=X"
	//
	// Returns - 
	// 			int: 0 if parse succeded, otherwise it did not succeed
	public int parse(String getRequest) {
		String[] parameters2BParsed = getRequest.split("&");
		
		days = Integer.parseInt(parameters2BParsed[0].substring(DAY_SEARCH_STRING.length()));
		state = parameters2BParsed[1].substring(STATE_SEARCH_STRING.length());
		city = parameters2BParsed[2].substring(CITY_SEARCH_STRING.length());
		
		return 0;
	}
	
	//getter for days post parse
	public int getDays() {
		return days;
	}
	
	//getter for state post parse
	public String getState() {
		return state;
	}
	
	//getter for city post parse
	public String getCity() {
		return city;
	}

}