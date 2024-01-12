package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import constants.Message;
import constants.Regex;
import models.HotelModel;
import utils.ConsoleColors;
import utils.Utils;
import utils.StringTools;

public class HotelManagement {

    private ArrayList<HotelModel> hotelList;

    private ArrayList<HotelModel> userActionList;

    private ArrayList<HotelModel> searchList;

    public HotelManagement() {
        this.hotelList = new ArrayList<HotelModel>();
        this.userActionList = new ArrayList<HotelModel>();
        this.searchList = new ArrayList<HotelModel>();
    }

    public void addNewHotel() {
        boolean isExisted;
        String hotel_id;
        do {
            do {
                isExisted = false; // reset isExisted
                hotel_id = Utils
                        .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                                Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS)
                        .toUpperCase();
                for (HotelModel hotel : userActionList) {
                    if (hotel.getHotel_id().equals(hotel_id)) {
                        isExisted = true;
                        System.out.println(Message.HOTEL_ID_IS_EXISTED + "\n" + Message.ADD_NEW_HOTEL_FAILED);
                        break;
                    }
                }
            } while (isExisted);

            String hotel_Name = Utils.getString(Message.INPUT_HOTEL_NAME, Regex.HOTEL_NAME,
                    Message.HOTEL_NAME_IS_REQUIRED, Message.HOTEL_NAME_MUST_BE_LETTER);
            // String hotel_Room_Available =
            // String.valueOf(Utils.getAnInteger(Message.INPUT_HOTEL_ROOM_AVAILABLE,
            // Message.HOTEL_ROOM_AVAILABLE_MUST_BE_NUMBER, 0, Integer.MAX_VALUE));
            String hotel_Room_Available = Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE,
                    Regex.HOTEL_ROOM_AVAILABLE,
                    Message.HOTEL_ROOM_AVAILABLE_IS_REQUIRED, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_NUMBER);
            String hotel_Address = StringTools.formatString(
                    Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED,
                            Message.HOTEL_ADDRESS_MUST_NOT_CONTAIN_SPECIAL_CHARACTER));
            String hotel_Phone = Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.HOTEL_PHONE,
                    Message.HOTEL_PHONE_IS_REQUIRED,
                    Message.HOTEL_PHONE_MUST_BE_10_DIGITS); // check regex
            String hotel_Rating = Utils.getString(Message.INPUT_HOTEL_RATING, Regex.HOTEL_RATING,
                    Message.HOTEL_RATING_IS_REQUIRED,
                    Message.HOTEL_RATING_MUST_BE_NUMBER_AND_STAR);

            HotelModel hotel = new HotelModel(hotel_id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone,
                    hotel_Rating);
            // add to userActionList
            userActionList.add(hotel);
            System.out.println(Message.ADD_NEW_HOTEL_SUCCESSFULLY);
        } while (getUserChoice());
    }

    // search trong hotelList gốc
    public int searchHotelIndexId(ArrayList<HotelModel> hotel, String keyId) {
        for (int i = 0; i < hotel.size(); i++) {
            if (hotel.get(i).getHotel_id().equals(keyId)) {
                return i;
            }
        }
        return -1;
    }

    public HotelModel searchHotelByID(ArrayList<HotelModel> hotel, String keyId) {
        int pos = this.searchHotelIndexId(hotel, keyId);
        return pos == -1 ? null : hotel.get(pos);
    }

    // tìm trong 2 mảng hotelList và searchList
    public ArrayList<HotelModel> searchHotelListByID(String keyId) {
        searchList.clear(); // reset searchList
        // tìm trong hotelList
        for (HotelModel hotel : hotelList) {
            if (hotel.getHotel_id().contains(keyId)) {
                searchList.add(hotel);
            }
        }
        Comparator<HotelModel> orderById = new Comparator<HotelModel>() {
            @Override
            public int compare(HotelModel o1, HotelModel o2) {
                return o2.getHotel_id().compareTo(o1.getHotel_id()); // descending
            }
        };
        Collections.sort(searchList, orderById);
        return searchList;
    }

    // yêu cầu đề thay đổi, search bằng name và sort desc theo hotel_room_available
    public ArrayList<HotelModel> searchHotelListByAddress(String address) {
        searchList.clear(); // reset searchList
        // tìm trong hotelList
        for (HotelModel hotel : hotelList) {
            if (hotel.getHotel_Address().contains(address)) {
                searchList.add(hotel);
            }
        }
        Comparator<HotelModel> orderByAddress = new Comparator<HotelModel>() {
            @Override
            public int compare(HotelModel o1, HotelModel o2) {
                return o2.getHotel_Address().compareTo(o1.getHotel_Address()); // descending
            }
        };
        Collections.sort(searchList, orderByAddress);
        return searchList;
    }

    public void checkExistsHotel() {
        do {
            String hotel_id = Utils
                    .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                            Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
            HotelModel hotelModel = this.searchHotelByID(hotelList, hotel_id); // search trong hotelList gốc
            if (hotelModel == null) {
                System.out.println(Message.NO_HOTEL_FOUND);
            } else {
                System.out.println(Message.EXIST_HOTEL);
            }
        } while (getUserChoice());
    }

    // after updating, the program return to the main screen
    public void updateHotel() {
        String hotel_id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
        HotelModel hotel = this.searchHotelByID(userActionList, hotel_id);
        if (hotel == null) {
            System.out.println(Message.HOTEL_DOES_NOT_EXIST);
        } else {
            // các field dữ liệu update có thể rỗng
            String hotel_Name = Utils.getString(Message.INPUT_HOTEL_NAME);
            String hotel_Room_Available = Utils.getString(Message.INPUT_HOTEL_ROOM_AVAILABLE);
            String hotel_Address = Utils.getString(Message.INPUT_HOTEL_ADDRESS);
            String hotel_Phone = Utils.getString(Message.INPUT_HOTEL_PHONE);
            String hotel_Rating = Utils.getString(Message.INPUT_HOTEL_RATING);

            // If new information is blank, then not change old information.
            if (hotel_Name.isEmpty()) {
                hotel.setHotel_Name(hotel.getHotel_Name());
            } else {
                hotel.setHotel_Name(hotel_Name);
            }

            if (hotel_Room_Available.isEmpty()) {
                hotel.setHotel_Room_Available(hotel.getHotel_Room_Available());
            } else {
                hotel.setHotel_Room_Available(hotel_Room_Available);
            }

            if (hotel_Address.isEmpty()) {
                hotel.setHotel_Address(hotel.getHotel_Address());
            } else {
                hotel.setHotel_Address(hotel_Address);
            }

            if (hotel_Phone.isEmpty()) {
                hotel.setHotel_Phone(hotel.getHotel_Phone());
            } else {
                hotel.setHotel_Phone(hotel_Phone);
            }

            if (hotel_Rating.isEmpty()) {
                hotel.setHotel_Rating(hotel.getHotel_Rating());
            } else {
                hotel.setHotel_Rating(hotel_Rating);
            }
            System.out.println("After updating: ");
            hotel.showInfo();
        }
    }

    // after deleting, the program return to the main screen
    public void deleteHotel() {
        String hotel_id = Utils
                .getString(Message.INPUT_HOTEL_ID, Regex.HOTEL_ID, Message.HOTEL_ID_IS_REQUIRED,
                        Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);
        // tìm vị trí của hotel cần xóa
        // tìm trong cái mảng gốc
        HotelModel hotel = this.searchHotelByID(hotelList, hotel_id);
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
                    HotelModel hotel = this.searchHotelByID(hotelList, id);
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
                        for (HotelModel item : searchList) {
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
        Comparator<HotelModel> orderByName = new Comparator<HotelModel>() {
            @Override
            public int compare(HotelModel o1, HotelModel o2) {
                return o2.getHotel_Name().compareTo(o1.getHotel_Name());
            }
        };
        Collections.sort(hotelList, orderByName);
        String str = String.format(
                ConsoleColors.RED + "   %5s,%15s,%5s,%80s,%11s,   %10s" + ConsoleColors.RESET,
                ConsoleColors.BLACK_BACKGROUND + "ID", "Name", "Room", "Address", "Phone",
                "Rating" + ConsoleColors.RESET);
        System.out.println(str);
        for (HotelModel item : hotelList) {
            item.showInfo();
        }

        System.out.println(ConsoleColors.RED_BACKGROUND + "Test UserActionList: " + ConsoleColors.RESET);
        for (HotelModel item : userActionList) {
            item.showInfo();
        }
    }

    public boolean loadFromFile(String url) {
        // hotelList.clear();
        // hotelList = FileHandler.deserialize(url);
        // return hotelList != null;
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
            HotelModel hotel;
            try {
                while (true) {
                    hotel = (HotelModel) fo.readObject();
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
            userActionList = (ArrayList<HotelModel>) hotelList.clone();

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
            for (HotelModel item : userActionList) {
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
