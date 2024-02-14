package models;

import java.util.ArrayList;

public interface Searchable {
    int searchIndexById(String keyId);
    Hotel searchObjectById(String keyId);
    ArrayList<Hotel> searchHotelListByAddress(String keyAddress);
}
