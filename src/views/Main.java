package views;

import constants.Path;
import controllers.HotelManagement;
import models.Menu;

public class Main {
    public static void main(String[] args) {

        HotelManagement hm = new HotelManagement();

        hm.loadFromFile(Path.URL);

        Menu menu = new Menu("Hotel Management System");
        menu.addOption("Adding new hotel");
        menu.addOption("Checking exists hotel");
        menu.addOption("Updating Hotel information");
        menu.addOption("Deleting Hotel");
        menu.addOption("Searching Hotel");
        menu.addOption("Display a hotel list");
        menu.addOption("Save to file");
        menu.addOption("Others Quit");

        int choice;
        do {
            menu.print();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    hm.addNewHotel();
                    break;
                case 2:
                    hm.checkExistsHotel();
                    break;
                case 3:
                    hm.updateHotel();
                    break;
                case 4:
                    hm.deleteHotel();
                    break;
                case 5:
                    hm.searchHotel();
                    break;
                case 6:
                    hm.displayHotelList();
                    break;
                case 7:
                    hm.saveToFile(Path.URL);
                    break;
                case 8:
                    hm.quit();
                    break;
            }
        } while (choice != menu.optionList.size());
    }
}
