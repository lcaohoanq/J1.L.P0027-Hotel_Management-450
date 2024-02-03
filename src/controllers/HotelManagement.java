package controllers;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

import constants.Message;
import constants.Regex;
import models.Hotel;
import utils.Utils;
import utils.StringTools;

public class HotelManagement {

    private ArrayList<Hotel> hotelList = new ArrayList<>();
    private ArrayList<Hotel> searchList = new ArrayList<>();

    //Function 1: Load data from file to program (at least 4 hotel are available)
    public void loadDataFromFile(String url) {
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

            System.out.println(Message.READ_FILE_SUCCESS + url);
        } catch (Exception e) {
            System.out.println(Message.READ_FILE_FAILED + e.getMessage());
        }
    }
    //Function 2: Add new hotel
    public void addNewHotel() {
        boolean isExisted;
        String id;
        do {
            do {
                isExisted = false; // reset isExisted
                id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS)
                        .toUpperCase();
                for (Hotel hotel : hotelList) {
                    if (hotel.getId().equals(id)) {
                        isExisted = true;
                        System.out.println(Message.HOTEL_ID_IS_EXISTED + "\n" + Message.ADD_NEW_HOTEL_FAILED);
                        break;
                    }
                }
            } while (isExisted);

            String name = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_HOTEL_NAME, Regex.NAME, Message.HOTEL_NAME_IS_REQUIRED, Message.HOTEL_NAME_MUST_START_WITH_LETTER));
            String room = StringTools.formatNum(Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE, Regex.ROOM,Message.HOTEL_ROOM_AVAILABLE_IS_REQUIRED, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_A_POSITIVE_NUMBER));
            String address = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED, Message.HOTEL_ADDRESS_MUST_SEPARATE_BY_COMMA));
            String phone = Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.PHONE, Message.HOTEL_PHONE_IS_REQUIRED, Message.HOTEL_PHONE_MUST_START_WITH_0_AND_FOLLOW_9_DIGIT);
            String rating = StringTools.formatRating(StringTools.formatNum(Utils.getString(Message.INPUT_HOTEL_RATING, Regex.ROOM, Message.HOTEL_RATING_IS_REQUIRED, Message.HOTEL_RATING_MUST_BE_A_POSITIVE_NUMBER)));
            
            Hotel hotel = new Hotel(id, name, room, address, phone, rating);
            // add to userActionList
            hotelList.add(hotel);
            System.out.println(Message.ADD_NEW_HOTEL_SUCCESSFULLY);
        } while (getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
    }

    //Function 3: Check to exists hotel
    public void checkToExistsHotel() {
        if(hotelList.isEmpty()){
            System.out.println(Message.NOTHING_TO + "check");
        }else{
            do {
                String id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
                Hotel hotel = this.searchHotelByID(hotelList, id); // search trong hotelList
                if (hotel == null) {
                    System.out.println(Message.NO_HOTEL_FOUND);
                } else {
                    System.out.println(Message.EXIST_HOTEL);
                }
            } while (getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
        }
    }

    //Function 4: Update hotel
    public void updateHotelInformation() {
        if(hotelList.isEmpty()){
            System.out.println(Message.NOTHING_TO + "update");
        }else{
            do{
                String id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);

                Hotel hotel = this.searchHotelByID(hotelList, id);
                int index = this.searchHotelIndexId(hotelList, id);
                if (hotel == null) {
                    System.out.println(Message.UPDATE_HOTEL_FAILED + " ," + Message.HOTEL_DOES_NOT_EXIST);
                } else {
                    System.out.println("-------------------------------------------------------Before updating: ---------------------------------------------------------------");
                    StringTools.printTitle();
                    hotel.showInfo();

                    // các field dữ liệu update có thể rỗng, đề không nói constraint,
                    // các field có thể rỗng nhưng nếu nhập phải theo regex
                    //chỉ xử lí các trường hợp tiêu biểu: dư khoảng trắng, xử lí rating sai grammar, bọc regex lại nhưng vẫn cho nhập rỗng
                    String name = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_NEW_HOTEL_NAME_OR_BLANK, Regex.NAME, Message.HOTEL_NAME_MUST_START_WITH_LETTER));
                    String room = StringTools.formatNum(Utils.getString(Message.INPUT_NEW_HOTEL_ROOM_AVAILABLE_OR_BLANK, Regex.ROOM, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_A_POSITIVE_NUMBER));
                    String address = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_NEW_HOTEL_ADDRESS_OR_BLANK, Regex.ADDRESS, Message.HOTEL_ADDRESS_MUST_SEPARATE_BY_COMMA));
                    String phone = Utils.getString(Message.INPUT_NEW_HOTEL_PHONE_OR_BLANK, Regex.PHONE, Message.HOTEL_PHONE_MUST_START_WITH_0_AND_FOLLOW_9_DIGIT);
                    String rating = StringTools.formatRating(StringTools.formatNum(Utils.getString(Message.INPUT_NEW_HOTEL_RATING_OR_BLANK, Regex.ROOM, Message.HOTEL_RATING_MUST_BE_A_POSITIVE_NUMBER)));

                    if(name.isEmpty()) {
                        name = hotel.getName();
                    }
                    if(room.isEmpty()) {
                        room = hotel.getRoomAvailable();
                    }
                    if(address.isEmpty()) {
                        address = hotel.getAddress();
                    }
                    if(phone.isEmpty()) {
                        phone = hotel.getPhone();
                    }
                    if(rating.isEmpty()) {
                        rating = hotel.getRating();
                    }

                    Hotel newHotel = new Hotel(id, name, room, address, phone, rating);

                    hotelList.set(index, newHotel);

                    System.out.println("---------------------------------------------------------------After updating: ---------------------------------------------------------------");
                    newHotel.showInfo();
                    System.out.println(Message.UPDATE_HOTEL_SUCCESSFULLY);
                }
            }while(getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
        }
    }

    //Function 5: Delete hotel
    public void deleteHotel() {
        if(hotelList.isEmpty()){
            System.out.println(Message.NOTHING_TO + "delete");
        }else{
            do{
                String id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS).toUpperCase();

                // tìm vị trí của hotel cần xóa
                Hotel hotel = this.searchHotelByID(hotelList, id);

                if (hotel == null) {
                    System.out.println(Message.DELETE_HOTEL_FAILED + "," + Message.HOTEL_DOES_NOT_EXIST);
                } else {
                    System.out.println("------------------------------------------------------Before deleting: --------------------------------------------------------------");
                    hotel.showInfo();
                    //if user press y/Y => delete
                    if (getUserConfirmation(Message.DO_YOU_READY_WANT_TO_DELETE_THIS_HOTEL)) {
                        hotelList.remove(hotel);
                        System.out.println(Message.DELETE_HOTEL_SUCCESSFULLY);
                    }
                }
            }while(getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
        }
    }

    //Function 6: Search hotel
    public void searchHotel() {
        if(hotelList.isEmpty()) {
            System.out.println(Message.NOTHING_TO + "search");
        }else{
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
                            System.out.println(Message.HOTEL_ID_FOUNDED);
                            hotel.showInfo();
                        } else {
                            System.out.println(Message.HOTEL_ID_NOT_FOUND);
                        }
                        break;

                    // search by address
                    case 2:
                        String address = Utils.getString(Message.INPUT_HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED);
                        searchList = this.searchHotelListByAddress(hotelList,address);
                        if (searchList.isEmpty()) {
                            System.out.println(Message.HOTEL_ADDRESS_NOT_FOUND);
                        } else {
                            System.out.println(Message.HOTEL_ADDRESS_FOUNDED);
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
            } while (getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
        }
    }

    //Function 7: Display hotel and sort by name
    public void displayHotelList() {
        if (hotelList.isEmpty()) {
            System.out.println(Message.NOTHING_TO + "display");
        }else{
            Comparator<Hotel> orderByName = new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
                }
            };
            hotelList.sort(orderByName);
            StringTools.printLine();
            StringTools.printTitle();
            StringTools.printLine();
            for (Hotel item : hotelList) {
                item.showInfo();
                StringTools.printLine();
            }
        }
    }
    //Function 8: Save to fle
    public void saveToFile(String url) {
        if (hotelList.isEmpty()) {
            System.out.println(Message.NOTHING_TO + "write, is empty list");
        }else{
            try {
                FileOutputStream fOut = new FileOutputStream(url);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                for (Hotel item : hotelList) {
                    out.writeObject(item);
                }
                out.close();
                fOut.close();

                System.out.println(Message.SAVE_FILE_SUCCESS + url);
            } catch (IOException e) {
                System.out.println(Message.SAVE_FILE_FAILED + e.getMessage());
            }
        }
    }

    //Function 9: Exit program
    public void quit(String msg) {
        System.out.println(msg);
        System.exit(0);
    }

    //Other functions to support the main functions
    private int searchHotelIndexId(ArrayList<Hotel> hotel, String keyId) {
        for (int i = 0; i < hotel.size(); i++) {
            if (hotel.get(i).getId().equalsIgnoreCase(keyId)) {
                return i;
            }
        }
        return -1;
    }

    private Hotel searchHotelByID(ArrayList<Hotel> hotel, String keyId) {
        int pos = this.searchHotelIndexId(hotel, keyId);
        return pos == -1 ? null : hotel.get(pos);
    }

    // search by name and sort desc by room available
    private ArrayList<Hotel> searchHotelListByAddress(ArrayList<Hotel> list, String address) {
        if(!searchList.isEmpty()){
            searchList.clear();
        }
        for (Hotel hotel : list) {
            if (hotel.getAddress().toLowerCase().contains(StringTools.removeTwoSpace(address).toLowerCase())) {
                searchList.add(hotel);
            }
        }
        Comparator<Hotel> orderByRoomAvailable = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                if(Integer.parseInt(o2.getRoomAvailable()) > Integer.parseInt(o1.getRoomAvailable())){
                    return 1;
                }
                return -1;
            }
        };
        searchList.sort(orderByRoomAvailable);
        return searchList;
    }

    //chấp nhận người dùng chỉ nhập vào 4 kí tự y,Y,n,N
    //check equals ignore case với y => return true;
    //                             n => return false;
    //sử dụng 2 hàm này trong do-while
    private boolean getUserConfirmation(String msg) {
        return Utils.getYesNo(msg, Message.PLEASE_INPUT_Y_OR_N, Regex.YES_NO).equalsIgnoreCase("y");
    }
}
