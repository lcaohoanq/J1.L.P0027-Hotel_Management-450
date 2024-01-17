package constants;

public class Regex {
    public static final String HOTEL_ID = "^[hH]\\d{2}$"; // H01, H02, H03, ...
    public static final String HOTEL_NAME = "^[a-zA-Z\\s]+$"; // name
    public static final String HOTEL_ADDRESS = "^[a-zA-Z0-9,\\s]+$"; // address:
    public static final String HOTEL_PHONE = "^\\d{10}$"; // phone: 10 digits
    public static final String HOTEL_RATING = "^\\d+\\sstar[s]?$"; // 1 star, 2 star[s], 3 star[s], ...
    public static final String OPTIONS_YES_NO = "^[yYnN]$"; //y,Y,n,N
}
