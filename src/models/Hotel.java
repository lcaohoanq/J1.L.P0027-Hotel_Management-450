package models;

import java.io.Serializable;

public class Hotel implements Serializable {

    private String id;
    private String name;
    private int roomAvailable;
    private String address;
    private String phone;
    private int rating;

    public Hotel() {
    }

    public Hotel(String id, String name, int roomAvailable, String address, String phone, int rating) {
        this.id = id.toUpperCase();
        this.name = name;
        this.roomAvailable = roomAvailable;
        this.address = address;
        this.phone = phone;
        this.rating = rating ;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(int roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void showInfo() {
        String str = String.format("|%3s|%15s|%15d|%70s|%15s|%5d star|",
                id, name, roomAvailable,
                address,
                phone, rating);
        System.out.println(str);
    }

    @Override
    public String toString() {
        return String.format("|%3s|%15s|%15d|%70s|%15s|%5d star|\n", id, name, roomAvailable,
                address,
                phone, rating);
    }

}
