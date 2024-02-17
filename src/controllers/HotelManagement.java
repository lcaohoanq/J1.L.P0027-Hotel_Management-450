package controllers;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

import constants.Message;
import constants.Regex;
import models.FileService;
import models.Hotel;
import models.Searchable;
import utils.Utils;
import utils.StringTools;

public class HotelManagement implements Searchable, FileService {

    private ArrayList<Hotel> hotelList = new ArrayList<>();
    private ArrayList<Hotel> searchList = new ArrayList<>();

    //Function 2: Add new hotel
    public void addNewHotel() {
        boolean isExisted;
        String id;
        //show list hotel
        this.displayHotelList();
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
            //khi add mot hotel moi, thi so phong phai lon hon 0
            int room = Utils.getInt(Message.INPUT_HOTEL_ROOM_AVAILABLE,0);
            String address = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_HOTEL_ADDRESS, Regex.ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED, Message.HOTEL_ADDRESS_MUST_SEPARATE_BY_COMMA));
            String phone = Utils.getString(Message.INPUT_HOTEL_PHONE, Regex.PHONE, Message.HOTEL_PHONE_IS_REQUIRED, Message.HOTEL_PHONE_MUST_START_WITH_0_AND_FOLLOW_9_DIGIT);
            //so sao cua khach san phai lon hon 0 va nam trong khoang tu 1 den 7
            int rating = Utils.getInt(Message.INPUT_HOTEL_RATING, Message.HOTEL_RATING_IS_REQUIRED_BETWEEN_1_AND_7, 1, 7);
            
            Hotel hotel = new Hotel(id, name, room, address, phone, rating);
            hotelList.add(hotel);
            this.displayHotelList();
            System.out.println(Message.ADD_NEW_HOTEL_SUCCESSFULLY);
        } while (Utils.getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
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
                Hotel hotel = this.searchObjectById(id); // search trong hotelList
                if (hotel == null) {
                    System.out.println(Message.NO_HOTEL_FOUND);
                } else {
                    System.out.println(Message.EXIST_HOTEL);
                    StringTools.printTitle();
                    StringTools.printLine();
                    hotel.showInfo();
                    StringTools.printLine();
                }
            } while (Utils.getUserConfirmation(Message.DO_YOU_WANT_TO_CONTINUE));
        }
    }

    //Function 4: Update hotel
    public void updateHotelInformation() {
        if(hotelList.isEmpty()){
            System.out.println(Message.NOTHING_TO + "update");
        }else{
            String id = Utils
                    .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                            Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS);

            Hotel hotel = this.searchObjectById(id);
//                int index = this.searchHotelIndexId(hotelList, id);
            if (hotel == null) {
                System.out.println(Message.UPDATE_HOTEL_FAILED + ", " + Message.HOTEL_DOES_NOT_EXIST);
            } else {
                System.out.println("-------------------------------------------------------Before updating: ---------------------------------------------------------------");
                StringTools.printTitle();
                StringTools.printLine();
                hotel.showInfo();
                StringTools.printLine();

                // các field dữ liệu update có thể rỗng, đề không nói constraint,
                // các field có thể rỗng nhưng nếu nhập phải theo regex
                //chỉ xử lí các trường hợp tiêu biểu: dư khoảng trắng, xử lí rating sai grammar, bọc regex lại nhưng vẫn cho nhập rỗng
                String newName = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_NEW_HOTEL_NAME_OR_BLANK, Regex.NAME, Message.HOTEL_NAME_MUST_START_WITH_LETTER));
                int newRoom = Utils.getInt(Message.INPUT_NEW_HOTEL_ROOM_AVAILABLE_OR_BLANK, Regex.ROOM, Message.HOTEL_ROOM_AVAILABLE_MUST_BE_A_POSITIVE_NUMBER);
                String newAddress = StringTools.removeTwoSpace(Utils.getString(Message.INPUT_NEW_HOTEL_ADDRESS_OR_BLANK, Regex.ADDRESS, Message.HOTEL_ADDRESS_MUST_SEPARATE_BY_COMMA));
                String newPhone = Utils.getString(Message.INPUT_NEW_HOTEL_PHONE_OR_BLANK, Regex.PHONE, Message.HOTEL_PHONE_MUST_START_WITH_0_AND_FOLLOW_9_DIGIT);
                int newRating = Utils.getInt(Message.INPUT_NEW_HOTEL_RATING_OR_BLANK, Regex.ROOM, Message.HOTEL_RATING_IS_REQUIRED_BETWEEN_1_AND_7);

                if(!newName.isEmpty()) {
                    hotel.setName(newName);
                }
                if(newRoom != -1) {
                    hotel.setRoomAvailable(newRoom);
                }
                if(!newAddress.isEmpty()) {
                    hotel.setAddress(newAddress);
                }
                if(!newPhone.isEmpty()) {
                    hotel.setPhone(newPhone);
                }
                if(newRating != -1) {
                    hotel.setRating(newRating);
                }

                System.out.println("--------------------------------------------------------After updating: ---------------------------------------------------------------");
                StringTools.printTitle();
                StringTools.printLine();
                hotel.showInfo();
                StringTools.printLine();
                System.out.println(Message.UPDATE_HOTEL_SUCCESSFULLY);
            }
        }
    }

    //Function 5: Delete hotel
    public void deleteHotel() {
        if(hotelList.isEmpty()){
            System.out.println(Message.NOTHING_TO + "delete");
        }else{
            System.out.println("-------------------------------------------------Select the hotel want to delete below-------------------------------------------------");
            this.displayHotelList();
            String id = Utils
                    .getString(Message.INPUT_HOTEL_ID, Regex.ID, Message.HOTEL_ID_IS_REQUIRED,
                            Message.HOTEL_ID_MUST_BE_H_AND_2_DIGITS).toUpperCase();

            // tìm vị trí của hotel cần xóa
            Hotel hotel = this.searchObjectById(id);

            if (hotel == null) {
                System.out.println(Message.DELETE_HOTEL_FAILED + "," + Message.HOTEL_DOES_NOT_EXIST);
            } else {
                System.out.println("------------------------------------------------------Before deleting: --------------------------------------------------------------");
                StringTools.printTitle();
                StringTools.printLine();
                hotel.showInfo();
                StringTools.printLine();
                //if user press y/Y => delete
                if (Utils.getUserConfirmation(Message.DO_YOU_READY_WANT_TO_DELETE_THIS_HOTEL)) {
                    hotelList.remove(hotel);
                    System.out.println(Message.DELETE_HOTEL_SUCCESSFULLY);
                    this.displayHotelList();
                }
            }
        }
    }

    //Function 6: Search hotel
    public void searchHotelById(){
        String id = Utils.getString(Message.INPUT_HOTEL_ID, Message.HOTEL_NAME_IS_REQUIRED);
        Hotel hotel = this.searchObjectById(id);
        if (hotel != null) {
            System.out.println(Message.HOTEL_ID_FOUNDED);
            StringTools.printTitle();
            StringTools.printLine();
            hotel.showInfo();
            StringTools.printLine();
        } else {
            System.out.println(Message.HOTEL_ID_NOT_FOUND);
        }
    }

    public void searchHotelByAddress(){
        String address = Utils.getString(Message.INPUT_HOTEL_ADDRESS, Message.HOTEL_ADDRESS_IS_REQUIRED);
        searchList = this.searchHotelListByAddress(address);
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
    }

    //Function 7: Display hotel and sort descending by hotel name
    public void displayHotelList() {
        if (hotelList.isEmpty()) {
            System.out.println(Message.HOTEL_LIST_IS_EMPTY);
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

    //Function 1: Load data from file to program (at least 4 hotel are available)
    @Override
    public void loadDataFromFile(String url) {
        if (!hotelList.isEmpty()) {
            hotelList.clear();
        }
        try {
            File f = new File(url);
            if (!f.exists()) {
                throw new Exception(Message.FILE_NOT_FOUND);
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            Hotel hotel;
            try {
                while ((hotel = (Hotel) fo.readObject()) != null) {
                    hotelList.add(hotel);
                }
            } catch (EOFException e) {
                // do nothing
            }
            fo.close();
            fi.close();

            System.out.println(Message.READ_FILE_SUCCESS + url);
        } catch (Exception e) {
            System.out.println(Message.READ_FILE_FAILED + e.getMessage());
        }
    }

    //Function 8: Save to fle
    @Override
    public void saveToFile(String url) {
        if (hotelList.isEmpty()) {
            System.out.println(Message.NOTHING_TO + "write, is empty list");
            return;
        }
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

    @Override
    public int searchIndexById(String keyId) {
        for (int i = 0; i < hotelList.size(); i++) {
            if (hotelList.get(i).getId().equalsIgnoreCase(keyId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Hotel searchObjectById(String keyId) {
        int pos = this.searchIndexById(keyId);
        return pos == -1 ? null : hotelList.get(pos);
    }

    @Override
    public ArrayList<Hotel> searchHotelListByAddress(String keyAddress) {
        if(!searchList.isEmpty()){
            searchList.clear();
        }
        for (Hotel hotel : hotelList) {
            if (hotel.getAddress().toLowerCase().contains(StringTools.removeTwoSpace(keyAddress).toLowerCase())) {
                searchList.add(hotel);
            }
        }
        Comparator<Hotel> orderByRoomAvailable = new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                if(o2.getRoomAvailable() > o1.getRoomAvailable()){
                    return 1;
                }
                return -1;
            }
        };
        searchList.sort(orderByRoomAvailable);
        return searchList;
    }
}
