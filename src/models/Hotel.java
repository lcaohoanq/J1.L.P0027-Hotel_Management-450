package models;

import java.io.Serializable;

import utils.ConsoleColors;

public class Hotel implements Serializable {

    private String id;
    private String name;
    private String roomAvailable;
    private String address;
    private String phone;
    private String rating;

    public Hotel() {
    }

    public Hotel(String id, String name, String roomAvailable, String address, String phone, String rating) {
        this.id = id.toUpperCase();
        this.name = name;
        this.roomAvailable = roomAvailable;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(String roomAvailable) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void showInfo() {
        String str = String.format(ConsoleColors.GREEN + "%5s," + ConsoleColors.RESET + "%15s,%5s,%80s,%11s,%10s",
                id, name, roomAvailable,
                address,
                phone, rating);
        System.out.println(str);
    }

    @Override
    public String toString() {
        return String.format("%5s-%15s-%2s-%80s-%11s-%10s\n", id, name, roomAvailable,
                address,
                phone, rating);
    }

}
