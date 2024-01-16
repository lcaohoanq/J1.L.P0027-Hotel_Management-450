package utils;

public class StringTools {

    public static String formatString(String inp) {
        inp = inp.trim();
        while (inp.contains("  ")) {
            inp = inp.replaceAll("\\s\\s", " ");
        }
        return inp;
    }

    public static void printTitle() {
        String str = String.format("|%3s|%15s|%5s|%70s|%15s|%10s|", "ID", "Name", "Room", "Address", "Phone", "Rating");
        System.out.println(str);
    }

    public static void printLine() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
    }
}
