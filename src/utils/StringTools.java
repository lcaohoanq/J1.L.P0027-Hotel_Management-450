package utils;

public class StringTools {

    //khi chuỗi có 2 khoảng trắng thừa thì sẽ xử lí còn 1
    //chưa bọc hết trường hợp có thể có
    public static String formatString(String inp) {
        inp = inp.trim();
        while (inp.contains("  ")) {
            inp = inp.replaceAll("\\s\\s", " ");
        }
        return inp;
    }

    public static void printTitle() {
        String str = String.format("|%3s|%15s|%15s|%70s|%15s|%10s|", "ID", "Name", "Room Available", "Address", "Phone", "Rating");
        System.out.println(str);
    }

    public static void printLine() {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
    }
}
