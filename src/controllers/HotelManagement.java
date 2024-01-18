package controllers;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import constants.Message;
import constants.Regex;
import models.Hotel;
import utils.Utils;
import utils.StringTools;

public class HotelManagement {

    private ArrayList<Hotel> hotelList = new ArrayList<>();

    private ArrayList<Hotel> userActionList = new ArrayList<>();

    private ArrayList<Hotel> searchList = new ArrayList<>();

    public void addNewHotel() {
        boolean isExisted;
        String id;
        do {
            do {
                isExisted = false; // reset isExisted
                id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS)
                        .toUpperCase();
                for (Hotel hotel : userActionList) {
                    if (hotel.getId().equals(id)) {
                        isExisted = true;
                        System.out.println(Message.HOTEL_ID_IS_EXISTED + "\n" + Message.ADD_NEW_HOTEL_FAILED);
                        break;
                    }
                }
            } while (isExisted);

            String name = StringTools.formatRedundantWhiteSpace(Utils.getString(Message.INPUT_HOTEL_NAME, Regex.HOTEL_NAME,
                    Message.HOTEL_NAME_IS_REQUIRED, Message.HOTEL_NAME_MUST_BE_LETTER));
            int roomAvailable = Utils.getInt(Message.INPUT_HOTEL_ROOM_AVAILABLE, 0);
            String address = StringTools.formatRedundantWhiteSpace(
                    Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED,
                            Message.HOTEL_ADDRESS_MUST_NOT_CONTAIN_SPECIAL_CHARACTER));
            String phone = Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.HOTEL_PHONE,
                    Message.HOTEL_PHONE_IS_REQUIRED,
                    Message.HOTEL_PHONE_MUST_BE_10_DIGITS); // check regex
            String rating = StringTools.formatRating(Utils.getString(Message.INPUT_HOTEL_RATING, Regex.HOTEL_RATING,
                    Message.HOTEL_RATING_IS_REQUIRED,
                    Message.HOTEL_RATING_MUST_BE_DIGIT_AND_STAR));

            Hotel hotel = new Hotel(id, name, roomAvailable, address, phone,
                    rating);
            // add to userActionList
            userActionList.add(hotel);
//            hotelList.add(hotel);
            System.out.println(Message.ADD_NEW_HOTEL_SUCCESSFULLY);
        } while (getUserConfirmation());
    }

    // search trong hotelList gốc
    private int searchHotelIndexId(ArrayList<Hotel> hotel, String keyId) {
        for (int i = 0; i < hotel.size(); i++) {
            if (hotel.get(i).getId().equals(keyId)) {
                return i;
            }
        }
        return -1;
    }

    private Hotel searchHotelByID(ArrayList<Hotel> hotel, String keyId) {
        int pos = this.searchHotelIndexId(hotel, keyId);
        return pos == -1 ? null : hotel.get(pos);
    }

    // yêu cầu đề thay đổi, search bằng name và sort desc theo room available
    private ArrayList<Hotel> searchHotelListByAddress(String address) {
        searchList.clear(); // reset searchList
        // tìm trong hotelList
        // Cần format lại chuỗi người dùng nhập vào
        // string không có 2 khoảng trắng liên tiếp
        for (Hotel hotel : hotelList) {
            if (hotel.getAddress().toLowerCase().contains(StringTools.formatRedundantWhiteSpace(address).toLowerCase())) {
                searchList.add(hotel);
            }
        }
        Comparator<Hotel> orderByRoomAvailable = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                if (o1.getRoomAvailable() > o2.getRoomAvailable()) {
                    return -1;
                }
                return 1;
            }
        };
        Collections.sort(searchList, orderByRoomAvailable);
        return searchList;
    }

    public void checkExistsHotel() {
        do {
            String id = Utils
                    .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                            Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
            Hotel hotel = this.searchHotelByID(hotelList, id); // search trong hotelList gốc
            if (hotel == null) {
                System.out.println(Message.NO_HOTEL_FOUND);
            } else {
                System.out.println(Message.EXIST_HOTEL);
            }
        } while (getUserConfirmation());
    }

    // after updating, the program return to the main screen
    public void updateHotel() {
        String id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
        Hotel hotel = this.searchHotelByID(userActionList, id);  //search trong userActionList đã clone hotelList về từ khi load file 
        if (hotel == null) {
            System.out.println(Message.HOTEL_DOES_NOT_EXIST);
        } else {
            System.out.println("Before updating: ");
            hotel.showInfo();

            // các field dữ liệu update có thể rỗng, đề không nói constraint,
            // các field có thể rỗng nhưng nếu nhập phải theo regex
            //chỉ xử lí các trường hợp tiêu biểu: dư khoảng trắng, xử lí rating sai grammar, bọc regex lại nhưng vẫn cho nhập rỗng
            String name = StringTools.formatRedundantWhiteSpace(Utils.getString(Message.INPUT_HOTEL_NAME, Regex.HOTEL_NAME, Message.HOTEL_NAME_MUST_BE_LETTER));
            String roomAvailable = StringTools.formatRedundantWhiteSpace(Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE, Regex.HOTEL_ROOM_AVAILABLE, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_A_POSITIVE_NUMBER));
            String address = StringTools.formatRedundantWhiteSpace(Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.HOTEL_ADDRESS, Message.HOTEL_ADDRESS_MUST_NOT_CONTAIN_SPECIAL_CHARACTER));
            String phone = StringTools.formatRedundantWhiteSpace(Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.HOTEL_PHONE, Message.HOTEL_PHONE_MUST_BE_10_DIGITS));
            String rating = StringTools.formatRedundantWhiteSpace(StringTools.formatRating(Utils.getString(Message.INPUT_HOTEL_RATING, Regex.HOTEL_RATING, Message.HOTEL_RATING_MUST_BE_DIGIT_AND_STAR)));

            // If new information is blank, then not change old information.
            if (name.isEmpty()) {
                hotel.setName(hotel.getName());
            } else {
                hotel.setName(name);
            }

            if (roomAvailable.isEmpty()) {
                hotel.setRoomAvailable(hotel.getRoomAvailable());
            } else {
                hotel.setRoomAvailable(Integer.parseInt(roomAvailable)); //cần nhập chuỗi để lấy rỗng, và trường của hotel là int phải set lại int
            }

            if (address.isEmpty()) {
                hotel.setAddress(hotel.getAddress());
            } else {
                hotel.setAddress(address);
            }

            if (phone.isEmpty()) {
                hotel.setPhone(hotel.getPhone());
            } else {
                hotel.setPhone(phone);
            }

            if (rating.isEmpty()) {
                hotel.setRating(hotel.getRating());
            } else {
                hotel.setRating(rating);
            }
            System.out.println("After updating: ");
            hotel.showInfo();
            System.out.println(Message.UPDATE_HOTEL_SUCCESSFULLY);
        }
    }

    // after deleting, the program return to the main screen
    public void deleteHotel() {
        String id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS).toUpperCase();
        // tìm vị trí của hotel cần xóa
        // tìm trong cái mảng gốc
        Hotel hotel = this.searchHotelByID(hotelList, id);
        //trường hợp người dùng thêm 1 hotel mà chưa save file
        //dữ liệu đang ở userActionList
        //người dùng muốn xoá dữ liệu mới nhập thì không được, vì chưa lưu xuống file -> nhận null 

        if (hotel == null) {
            System.out.println(Message.DELETE_HOTEL_FAILED);
        } else {
            //pass qua được ở trên thì dữ liệu chắc chắn có trong hotelList
            //nếu dữ liệu có trong hotelList thì userActionList sẽ có luôn (clone)
            //ta cần phải xoá ở cả 2 mảng, để trường hợp ghi file ra sẽ không bị xung đột giữa 2 mảng, 1 có 1 không
            hotel.showInfo();
            if (!getUserConfirmation()) {
                return;
            }
            userActionList.remove(hotel);
            hotelList.remove(hotel);
            System.out.println(Message.DELETE_HOTEL_SUCCESSFULLY);
        }
    }

    public void searchHotel() {
        int choice;
        do {
            System.out.println(Message.SEARCH_HOTEL);
            System.out.println(Message.SEARCH_OPTION_ID);
            System.out.println(Message.SEARCH_OPTION_ADDRESS);
            choice = Utils.getInt(Message.INPUT_YOUR_CHOICE, Message.SEARCH_HOTEL_MUST_IN_1_OR_2, 1, 2);
            switch (choice) {
                // search by id
                case 1:
                    String id = Utils.getString(Message.INPUT_HOTEL_ID, Message.HOTEL_NAME_IS_REQUIRED);
                    Hotel hotel = this.searchHotelByID(hotelList, id);
                    if (hotel != null) {
                        hotel.showInfo();
                    } else {
                        System.out.println(Message.HOTEL_ID_NOT_FOUND);
                    }
                    break;

                // search by name
                case 2:
                    String address = Utils.getString(Message.INPUT_HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED);
                    searchList = this.searchHotelListByAddress(address);
                    if (searchList.isEmpty()) {
                        System.out.println(Message.HOTEL_ADDRESS_NOT_FOUND);
                    } else {
                        StringTools.printLine();
                        StringTools.printTitle();
                        StringTools.printLine();
                        for (Hotel item : searchList) {
                            item.showInfo();
                            StringTools.printLine();
                        }
                    }
                    break;
            }
        } while (getUserConfirmation());
    }

    public void displayHotelList() {
        if (hotelList.isEmpty()) {
            System.out.println(Message.NO_HOTEL_FOUND);
            return;
        }
        Comparator<Hotel> orderByName = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                return o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
            }
        };
        Collections.sort(hotelList, orderByName);
        StringTools.printLine();
        StringTools.printTitle();
        StringTools.printLine();
        for (Hotel item : hotelList) {
            item.showInfo();
            StringTools.printLine();
        }
    }

    public void loadFromFile(String url) {
        //nếu mảng đang chứa dữ liệu thì phải xoá sạch dữ liêu trong mảng
        //rồi mới load file
        if (!hotelList.isEmpty()) {
            hotelList.clear();
        }
        try {
            File f = new File(url);
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            Hotel hotel;
            try {
                while ((hotel = (Hotel) fo.readObject()) != null) {
                    hotelList.add(hotel);
                }
            } catch (EOFException e) {
//                System.out.println("End of file " + e.getMessage());
            }
            fo.close();
            fi.close();

            // dùng deepcopy, khi đó khi ta tác động đến phần tử trong
            // userActionList
            // thì phần tử trong hotelList không bị tác động theo
            userActionList = (ArrayList<Hotel>) hotelList.clone();

            // userActionList = new ArrayList<>(hotelList); // copy hotelList to
            // userActionList when load from file
            // shallow copy, khi mà copy phần tử như này, nếu ta tác động đến phần tử trong
            // userActionList
            // thì phần tử trong hotelList cũng bị tác động theo
        } catch (Exception e) {
            System.out.println(Message.READ_FILE_FAILED + e.getMessage());
        }
    }

    public void saveToFile(String url) {
        if (hotelList.isEmpty()) {
            System.out.println(Message.NO_HOTEL_FOUND);
            return;
        }
        try {
            FileOutputStream fOut = new FileOutputStream(url);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            for (Hotel item : userActionList) {
                out.writeObject(item);
            }
            out.close();
            fOut.close();

            hotelList = (ArrayList<Hotel>) userActionList.clone();
            //sau khi write file, thì nội dung file được cập nhật từ userActionList
            //nhưng trong mảng hotelList vẫn chứa nội dung cũ
            //xử lí bằng việc clone lại userActionList cho hotelList 

            System.out.println(Message.WRITE_FILE_SUCCESS + url);
        } catch (IOException e) {
            System.out.println(Message.WRITE_FILE_FAILED + e.getMessage());
        }
    }

    public void quit() {
        System.exit(0);
    }

    //chấp nhận người dùng chỉ nhập vào 4 kí tự y,Y,n,N và kết quả sẽ đem lowerCase 
    //check equals với y => return true;
    //                 n => return false; 
    //sử dụng 2 hàm này trong do-while
    public boolean getUserConfirmation() {
        return Utils.getYesNo(Message.DO_YOU_WANT_TO_CONTINUE, Message.PLEASE_INPUT_Y_OR_N, Regex.OPTIONS_YES_NO).equalsIgnoreCase("y");
    }
}
