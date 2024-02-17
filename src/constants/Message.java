package constants;

import utils.ConsoleColors;

public class Message {

    // Input prompts for hotel information
    public static final String INPUT_HOTEL_ID = "Input hotel id: ";
    public static final String INPUT_HOTEL_NAME = "Input hotel name: ";
    public static final String INPUT_HOTEL_ROOM_AVAILABLE = "Input hotel room available: ";
    public static final String INPUT_HOTEL_ADDRESS = "Input hotel address: ";
    public static final String INPUT_HOTEL_PHONE = "Input hotel phone: ";
    public static final String INPUT_HOTEL_RATING = "Input hotel rating: ";

    // Validation messages for hotel information
    public static final String HOTEL_ID_MUST_BE_H_AND_2_DIGITS = "Hotel id must be in the format Hxx: H01, H02, ...";
    public static final String HOTEL_NAME_MUST_START_WITH_LETTER = "Hotel name must start with a letter";
    public static final String HOTEL_ROOM_AVAILABLE_MUST_BE_A_POSITIVE_NUMBER = "Hotel room available must be a positive number";
    public static final String HOTEL_ADDRESS_MUST_SEPARATE_BY_COMMA = "Hotel address must start with a letter or number and separated by comma";
    public static final String HOTEL_PHONE_MUST_START_WITH_0_AND_FOLLOW_9_DIGIT = "Hotel phone must start with 0 and follow 9 digits";
    public static final String HOTEL_RATING_MUST_BE_A_POSITIVE_NUMBER = "Hotel rating must be a positive number";

    //Validation message if null value
    public static final String HOTEL_ID_IS_REQUIRED = ConsoleColors.RED + "Hotel id is required" + ConsoleColors.RESET;
    public static final String HOTEL_NAME_IS_REQUIRED = "Hotel name is required";
    public static final String HOTEL_ROOM_AVAILABLE_IS_REQUIRED = "Hotel room available is required";
    public static final String HOTEL_ADDRESS_IS_REQUIRED = ConsoleColors.RED + "Hotel address is required" + ConsoleColors.RESET;
    public static final String HOTEL_PHONE_IS_REQUIRED = ConsoleColors.RED + "Hotel phone is required" + ConsoleColors.RESET;
    public static final String HOTEL_RATING_IS_REQUIRED = "Hotel rating is required";
    public static final String OPTIONS_IS_REQUIRED = "Options is required";
    public static final String HOTEL_RATING_IS_REQUIRED_BETWEEN_1_AND_7 = "Hotel rating is required between 1 and 7";
    //Update hotel
    public static final String INPUT_NEW_HOTEL_NAME_OR_BLANK = "Input new hotel name or a blank to keep the old value           : ";
    public static final String INPUT_NEW_HOTEL_ROOM_AVAILABLE_OR_BLANK = "Input new hotel room available or a blank to keep the old value : ";
    public static final String INPUT_NEW_HOTEL_ADDRESS_OR_BLANK = "Input new hotel address or a blank to keep the old value        : ";
    public static final String INPUT_NEW_HOTEL_PHONE_OR_BLANK = "Input new hotel phone or a blank to keep the old value          : ";
    public static final String INPUT_NEW_HOTEL_RATING_OR_BLANK = "Input new hotel rating or a blank to keep the old value         : ";

    //Search hotel information
    public static final String HOTEL_ID_FOUNDED = "Hotel id founded: ";
    public static final String HOTEL_ID_NOT_FOUND = "Hotel id not found";
    public static final String HOTEL_ADDRESS_FOUNDED = ConsoleColors.GREEN + "Hotel address founded: " + ConsoleColors.RESET;
    public static final String HOTEL_ADDRESS_NOT_FOUND = ConsoleColors.RED + "Hotel address not found" + ConsoleColors.RESET;

    //Existed hotel information
    public static final String HOTEL_LIST_IS_EMPTY = ConsoleColors.RED + "Hotel list is empty" + ConsoleColors.RESET;
    public static final String NOTHING_TO = "Nothing to ";
    public static final String EXIST_HOTEL = ConsoleColors.GREEN + "exist hotel" + ConsoleColors.RESET;
    public static final String NO_HOTEL_FOUND = ConsoleColors.RED + "No hotel found" + ConsoleColors.RESET;
    public static final String NO_HOTEL_FOUND_PLEASE_READ_FILE_FIRST = "No hotel found. Please read file first";
    public static final String HOTEL_DOES_NOT_EXIST = ConsoleColors.RED + "Hotel does not exist" + ConsoleColors.RESET;
    public static final String HOTEL_ID_IS_EXISTED = ConsoleColors.RED + "Hotel id is existed" + ConsoleColors.RESET;

    // Operation 
    public static final String ADD_NEW_HOTEL_SUCCESSFULLY = ConsoleColors.GREEN + "Add new hotel successfully" + ConsoleColors.RESET;
    public static final String ADD_NEW_HOTEL_FAILED = ConsoleColors.RED + "Add new hotel failed" + ConsoleColors.RESET;
    public static final String UPDATE_HOTEL_SUCCESSFULLY = ConsoleColors.GREEN + "Update hotel successfully" + ConsoleColors.RESET;
    public static final String UPDATE_HOTEL_FAILED = ConsoleColors.RED + "Update hotel failed" + ConsoleColors.RESET;
    public static final String DELETE_HOTEL_SUCCESSFULLY = ConsoleColors.GREEN + "Delete hotel successfully" + ConsoleColors.RESET;
    public static final String DELETE_HOTEL_FAILED = ConsoleColors.RED + "Delete hotel failed" + ConsoleColors.RESET;

    // File
    public static final String FILE_NOT_FOUND = ConsoleColors.RED + "File not found" + ConsoleColors.RESET;;
    public static final String READ_FILE_SUCCESS = ConsoleColors.GREEN + "Read file success " + ConsoleColors.RESET;
    public static final String READ_FILE_FAILED = ConsoleColors.RED + "Read file failed " + ConsoleColors.RESET;
    public static final String SAVE_FILE_SUCCESS = ConsoleColors.GREEN + "Save file success " + ConsoleColors.RESET;
    public static final String SAVE_FILE_FAILED = ConsoleColors.RED + "Save file failed " + ConsoleColors.RESET;

    // User confirmation prompts
    public static final String DO_YOU_WANT_TO_CONTINUE = "Do you want to continue? (y/n): ";
    public static final String DO_YOU_READY_WANT_TO_DELETE_THIS_HOTEL = "Do you ready want to delete this hotel? (y/n): ";
    public static final String PLEASE_INPUT_Y_OR_N = "Please input 'y' or 'n'";

}
