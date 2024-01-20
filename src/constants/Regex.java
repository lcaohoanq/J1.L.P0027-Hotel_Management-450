package constants;

public class Regex {
    public static final String ID = "^[hH]\\d{2}$"; // H01, H02, H03, ...
    public static final String NAME = "^[a-zA-Z]+[a-zA-Z0-9\\s]*$"; // name: start with letter, can contain number, space
    public static final String ROOM = "^[0-9]+$"; // number: start without 0,
    public static final String ADDRESS = "^[a-zA-Z0-9]+[a-zA-Z0-9,\\s]*$"; // address: start with letter or number, can contain comma,number,space
    public static final String PHONE = "^0\\d{9}$"; // phone: 10 digits must start with 0
    public static final String YES_NO = "^[yYnN]$"; //y,Y,n,N
}
