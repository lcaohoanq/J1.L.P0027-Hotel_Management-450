package constants;

public class Message {

    // Input prompts for hotel information
    public static final String INPUT_HOTEL_ID = "Input hotel id: ";
    public static final String INPUT_HOTEL_NAME = "Input hotel name: ";
    public static final String INPUT_HOTEL_ROOM_AVAILABLE = "Input hotel room available: ";
    public static final String INPUT_HOTEL_ADDRESS = "Input hotel address: ";
    public static final String INPUT_HOTEL_PHONE = "Input hotel phone: ";
    public static final String INPUT_HOTEL_RATING = "Input hotel rating: ";

    // Validation messages for hotel information
    public static final String HOTEL_ID_MUST_BE_H_AND_2_DIGITS = "Hotel id must be in the format H01, H02, ...";
    public static final String HOTEL_PHONE_MUST_BE_10_DIGITS = "Hotel phone must be 10 digits";
    public static final String HOTEL_RATING_MUST_BE_NUMBER_AND_STAR = "Hotel rating must be in the format 1 star, 2 stars, ...";
    public static final String HOTEL_ADDRESS_IS_REQUIRED = "Hotel address is required";
    public static final String HOTEL_NAME_IS_REQUIRED = "Hotel name is required";
    public static final String HOTEL_NAME_MUST_BE_LETTER = "Hotel name must consist of characters only";
    public static final String HOTEL_ROOM_AVAILABLE_MUST_BE_NUMBER = "Hotel room available must be a number";
    public static final String HOTEL_ROOM_AVAILABLE_IS_REQUIRED = "Hotel room available is required";
    public static final String HOTEL_ADDRESS_MUST_NOT_CONTAIN_SPECIAL_CHARACTER = "Hotel address must not contain special characters (except commas)";
    public static final String HOTEL_PHONE_IS_REQUIRED = "Hotel phone is required";
    public static final String HOTEL_RATING_IS_REQUIRED = "Hotel rating is required";
    public static final String HOTEL_ID_IS_REQUIRED = "Hotel id is required";
    public static final String HOTEL_ID_NOT_FOUND = "Hotel id not found";
    public static final String HOTEL_ADDRESS_NOT_FOUND = "Hotel address not found";

    // Hotel existence and uniqueness messages
    public static final String EXIST_HOTEL = "Exist Hotel";
    public static final String NO_HOTEL_FOUND = "No Hotel Found!";
    public static final String HOTEL_DOES_NOT_EXIST = "Hotel does not exist";
    public static final String HOTEL_ID_IS_EXISTED = "Hotel id is already in use";
    public static final String HOTEL_NAME_IS_EXISTED = "Hotel name is already in use";
    public static final String HOTEL_NAME_IS_NOT_EXISTED = "Hotel name does not exist";

    // Operation outcome messages
    public static final String ADD_NEW_HOTEL_SUCCESSFULLY = "Add new hotel successfully";
    public static final String ADD_NEW_HOTEL_FAILED = "Add new hotel failed";
    public static final String UPDATE_HOTEL_SUCCESSFULLY = "Update hotel successfully";
    public static final String DELETE_HOTEL_SUCCESSFULLY = "Delete hotel successfully";
    public static final String DELETE_HOTEL_FAILED = "Delete hotel failed";

    // User confirmation prompts
    public static final String DO_YOU_WANT_TO_CONTINUE = "Do you want to continue? (y/n): ";
    public static final String DO_YOU_WANT_TO_DELETE = "Do you want to delete? (y/n): ";
    public static final String PLEASE_INPUT_Y_OR_N = "Please input 'y' or 'n'";

}
