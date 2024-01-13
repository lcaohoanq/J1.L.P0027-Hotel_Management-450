package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import constants.Message;
import constants.Regex;
import models.Hotel;
import utils.ConsoleColors;
import utils.Utils;
import utils.StringTools;

public class HotelManagement {

    private ArrayList<Hotel> hotelList;

    private ArrayList<Hotel> userActionList;

    private ArrayList<Hotel> searchList;

    public HotelManagement() {
        this.hotelList = new ArrayList<Hotel>();
        this.userActionList = new ArrayList<Hotel>();
        this.searchList = new ArrayList<Hotel>();
    }

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

            String name = Utils.getString(Message.INPUT_HOTEL_NAME, Regex.HOTEL_NAME,
                    Message.HOTEL_NAME_IS_REQUIRED, Message.HOTEL_NAME_MUST_BE_LETTER);
            String roomAvailable = Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE,
                    Regex.HOTEL_ROOM_AVAILABLE,
                    Message.HOTEL_ROOM_AVAILABLE_IS_REQUIRED, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_NUMBER);
            String address = StringTools.formatString(
                    Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED,
                            Message.HOTEL_ADDRESS_MUST_NOT_CONTAIN_SPECIAL_CHARACTER));
            String phone = Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.HOTEL_PHONE,
                    Message.HOTEL_PHONE_IS_REQUIRED,
                    Message.HOTEL_PHONE_MUST_BE_10_DIGITS); // check regex
            String rating = Utils.getString(Message.INPUT_HOTEL_RATING, Regex.HOTEL_RATING,
                    Message.HOTEL_RATING_IS_REQUIRED,
                    Message.HOTEL_RATING_MUST_BE_NUMBER_AND_STAR);

            Hotel hotel = new Hotel(id, name, roomAvailable, address, phone,
                    rating);
            // add to userActionList
            userActionList.add(hotel);
            System.out.println(Message.ADD_NEW_HOTEL_SUCCESSFULLY);
        } while (getUserChoice());
    }

    // search trong hotelList gốc
    public int searchHotelIndexId(ArrayList<Hotel> hotel, String keyId) {
        for (int i = 0; i < hotel.size(); i++) {
            if (hotel.get(i).getId().equals(keyId)) {
                return i;
            }
        }
        return -1;
    }

    public Hotel searchHotelByID(ArrayList<Hotel> hotel, String keyId) {
        int pos = this.searchHotelIndexId(hotel, keyId);
        return pos == -1 ? null : hotel.get(pos);
    }

    // yêu cầu đề thay đổi, search bằng name và sort desc theo hotel_room_available
    public ArrayList<Hotel> searchHotelListByAddress(String address) {
        searchList.clear(); // reset searchList
        // tìm trong hotelList
        for (Hotel hotel : hotelList) {
            if (hotel.getAddress().contains(address)) {
                searchList.add(hotel);
            }
        }
        Comparator<Hotel> orderByAddress = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                return o2.getAddress().compareTo(o1.getAddress()); // descending
            }
        };
        Collections.sort(searchList, orderByAddress);
        return searchList;
    }

    public void checkExistsHotel() {
        do {
            String id = Utils
                    .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                            Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
            Hotel hotelModel = this.searchHotelByID(hotelList, id); // search trong hotelList gốc
            if (hotelModel == null) {
                System.out.println(Message.NO_HOTEL_FOUND);
            } else {
                System.out.println(Message.EXIST_HOTEL);
            }
        } while (getUserChoice());
    }

    // after updating, the program return to the main screen
    public void updateHotel() {
        String id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
        Hotel hotel = this.searchHotelByID(userActionList, id);
        if (hotel == null) {
            System.out.println(Message.HOTEL_DOES_NOT_EXIST);
        } else {
            // các field dữ liệu update có thể rỗng
            String name = Utils.getString(Message.INPUT_HOTEL_NAME);
            String roomAvailable = Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE);
            String address = Utils.getString(Message.INPUT_HOTEL_ADDRESS);
            String phone = Utils.getString(Message.INPUT_HOTEL_PHONE);
            String rating = Utils.getString(Message.INPUT_HOTEL_RATING);

            // If new information is blank, then not change old information.
            if (name.isEmpty()) {
                hotel.setName(hotel.getName());
            } else {
                hotel.setName(name);
            }

            if (roomAvailable.isEmpty()) {
                hotel.setRoomAvailable(hotel.getRoomAvailable());
            } else {
                hotel.setRoomAvailable(roomAvailable);
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
        }
    }

    // after deleting, the program return to the main screen
    public void deleteHotel() {
        String id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
        // tìm vị trí của hotel cần xóa
        // tìm trong cái mảng gốc
        Hotel hotel = this.searchHotelByID(hotelList, id);
        if (hotel == null) {
            System.out.println(Message.DELETE_HOTEL_FAILED);
        } else {
            hotel.showInfo();
            if (!getUserConfirmation()) {
                return;
            }
            userActionList.remove(hotel);
            System.out.println(Message.DELETE_HOTEL_SUCCESSFULLY);
        }
    }

    public void searchHotel() {
        do {
            System.out.println("-------------Search Hotel--------------");
            int choice = Utils.getInt("1. Search hotel by id\n2. Search hotel by address\n",
                    "Please input 1,2", 1, 2);
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
                        for (Hotel item : searchList) {
                            item.showInfo();
                        }
                    }
                    break;
            }
        } while (getUserChoice());
    }

    public void displayHotelList() {
        if (hotelList.isEmpty()) {
            System.out.println("Hotel list is empty");
            return;
        }
        Comparator<Hotel> orderByName = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                return o2.getName().compareTo(o1.getName());
            }
        };
        Collections.sort(hotelList, orderByName);
        String str = String.format(
                ConsoleColors.RED + "   %5s,%15s,%5s,%80s,%11s,   %10s" + ConsoleColors.RESET,
                ConsoleColors.BLACK_BACKGROUND + "ID", "Name", "Room", "Address", "Phone",
                "Rating" + ConsoleColors.RESET);
        System.out.println(str);
        for (Hotel item : hotelList) {
            item.showInfo();
        }

        System.out.println(ConsoleColors.RED_BACKGROUND + "Test UserActionList: " + ConsoleColors.RESET);
        for (Hotel item : userActionList) {
            item.showInfo();
        }
    }

    public boolean loadFromFile(String url) {
        if (hotelList.size() > 0) {
            hotelList.clear();
        }
        try {
            File f = new File(url);
            if (!f.exists()) {
                return false;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            Hotel hotel;
            try {
                while (true) {
                    hotel = (Hotel) fo.readObject();
                    hotelList.add(hotel);
                }
            } catch (EOFException e) {
                System.out.println("End of file " + e.getMessage());
            }
            fo.close();
            fi.close();

            // fix lại bằng cách dùng deepcopy, khi đó khi ta tác động đến phần tử trong
            // userActionList
            // thì phần tử trong hotelList không bị tác động theo
            userActionList = (ArrayList<Hotel>) hotelList.clone();

            // userActionList = new ArrayList<>(hotelList); // copy hotelList to
            // userActionList when load from file
            // shallow copy, khi mà copy phần tử như này, nếu ta tác động đến phần tử trong
            // userActionList
            // thì phần tử trong hotelList cũng bị tác động theo
            System.out.println("Deserialized data is loaded from " + url);
            return true;
        } catch (Exception e) {
            System.out.println("Error read file " + e.getMessage());
            return false;
        }
    }

    public boolean saveToFile(String url) {
        if (hotelList.isEmpty()) {
            System.out.println("Hotel list is empty");
            return false;
        }
        try {
            FileOutputStream fOut = new FileOutputStream(url);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            for (Hotel item : userActionList) {
                out.writeObject(item);
            }
            out.close();
            fOut.close();
            System.out.println("Serialized data is saved in " + url);
            return true;
        } catch (IOException e) {
            System.out.println("Serialization failed: " + e.getMessage());
            return false;
        }
    }

    public void quit() {
        System.exit(0);
    }

    public boolean getUserChoice() {
        return Utils.getYesNo(Message.DO_YOU_WANT_TO_CONTINUE, Message.PLEASE_INPUT_Y_OR_N)
                .toLowerCase()
                .equals("y");
    }

    public boolean getUserConfirmation() {
        return Utils.getYesNo(Message.DO_YOU_WANT_TO_DELETE, Message.PLEASE_INPUT_Y_OR_N)
                .toLowerCase()
                .equals("y");
    }
}
