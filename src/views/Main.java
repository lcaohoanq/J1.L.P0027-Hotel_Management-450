package views;

import constants.Path;
import controllers.HotelManagement;
import models.Menu;

public class Main {
    public static void main(String[] args) {

        HotelManagement hm = new HotelManagement();

        Menu menu = new Menu("The Hotel Management - Read and Write File");;
        Menu searchMenu = new Menu("Search Hotel");

        initMenu(menu);
        initSearchMenu(searchMenu);

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
                    int searchChoice;
                    do{
                        searchMenu.print();
                        searchChoice = searchMenu.getChoice();
                        switch (searchChoice){
                            case 1:
                                hm.searchHotelById();
                                break;
                            case 2:
                                hm.searchHotelByAddress();
                                break;
                            case 3:
                                break;
                        }
                    } while (searchChoice != searchMenu.optionList.size());
                    break;
                case 7:
                    hm.displayHotelList();
                    break;
                case 8:
                    hm.saveToFile(Path.URL);
                    break;
                case 9:
                    System.out.println("Thank you for using our program!");
                    return;
            }
        } while (choice != menu.optionList.size());
    }

    public static void initMenu(Menu menu){
        menu.addOption("Load data from file to program");
        menu.addOption("Adding new hotel");
        menu.addOption("Checking exists hotel");
        menu.addOption("Updating Hotel information");
        menu.addOption("Deleting Hotel");
        menu.addOption("Searching Hotel");
        menu.addOption("Displaying a hotel list (descending by Hotel_Name)");
        menu.addOption("Save data to file");
        menu.addOption("Others Quit");
    }

    public static void initSearchMenu(Menu searchMenu){
        searchMenu.addOption("Search by hotel Id");
        searchMenu.addOption("Search by hotel Address");
        searchMenu.addOption("Exit to main menu");
    }
}
