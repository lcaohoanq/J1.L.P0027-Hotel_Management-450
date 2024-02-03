package views;

import constants.Path;
import controllers.HotelManagement;
import models.Menu;

public class Main {
    public static void main(String[] args) {

        HotelManagement hm = new HotelManagement();

        Menu menu = new Menu("The Hotel Management - Read and Write File");

        menu.addOption("Load data from file to program");
        menu.addOption("Adding new hotel");
        menu.addOption("Checking exists hotel");
        menu.addOption("Updating Hotel information");
        menu.addOption("Deleting Hotel");
        menu.addOption("Searching Hotel");
        menu.addOption("Displaying a hotel list (descending by Hotel_Name)");
        menu.addOption("Save data to file");
        menu.addOption("Others Quit");

        int choice;
        do {
            menu.print();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    hm.loadDataFromFile(Path.URL);
                    break;
                case 2:
                    hm.addNewHotel();
                    break;
                case 3:
                    hm.checkToExistsHotel();
                    break;
                case 4:
                    hm.updateHotelInformation();
                    break;
                case 5:
                    hm.deleteHotel();
                    break;
                case 6:
                    hm.searchHotel();
                    break;
                case 7:
                    hm.displayHotelList();
                    break;
                case 8:
                    hm.saveToFile(Path.URL);
                    break;
                case 9:
                    hm.quit("Thank you for using our program!");
            }
        } while (choice != menu.optionList.size());
    }
}
