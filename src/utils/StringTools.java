package utils;

public class StringTools {

    //khi chuỗi có 2 khoảng trắng thừa thì sẽ xử lí còn 1
    public static String removeTwoSpace(String inp) {
        inp = inp.trim();
        return inp.replaceAll("\\s+", " ");
    }

    public static void printTitle() {
        String str = String.format("|%3s|%15s|%15s|%70s|%15s|%10s|", "ID", "Name", "Room Available", "Address", "Phone", "Rating");
        System.out.println(ConsoleColors.PURPLE_BACKGROUND + str + ConsoleColors.RESET);
    }

    public static void printLine() {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
    }
}
