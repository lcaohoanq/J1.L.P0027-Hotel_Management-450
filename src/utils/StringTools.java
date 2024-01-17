package utils;

public class StringTools {

    //khi chuỗi có 2 khoảng trắng thừa thì sẽ xử lí còn 1
    //chưa bọc hết trường hợp có thể có
    public static String formatRedundantWhiteSpace(String inp) {
        inp = inp.trim();
        while (inp.contains("  ")) {
            inp = inp.replaceAll("\\s\\s", " ");
        }
        return inp;
    }
    public static String formatRating(String rating){
        String str = rating.trim();
        if(rating.startsWith("1")){
            str = "1 star";
        }else if(!rating.endsWith("s")){
            str = rating.concat("s");
        }
        return str;
    }

    public static void printTitle() {
        String str = String.format("|%3s|%15s|%15s|%70s|%15s|%10s|", "ID", "Name", "Room Available", "Address", "Phone", "Rating");
        System.out.println(str);
    }

    public static void printLine() {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
    }
}
