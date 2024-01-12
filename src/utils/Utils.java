
package utils;

import java.util.Scanner;

public class Utils {

    private static Scanner sc = new Scanner(System.in);

    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    // ép nhập chuỗi, có thể rỗng
    public static String getString(String inpMsg) {
        System.out.println(inpMsg);
        while (true) {
            try {
                String str = sc.nextLine();
                return str;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "Error!" + ConsoleColors.RESET);
            }
        }
    }

    public static String getString(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        do {

            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static int getInt(String welcome, int min) {
        boolean check = true;
        int number = 0;
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    // method: ép nhập số nguyên trong khoảng
    public static int getInt(String inpMsg, String errMsg,
            int lowerBound, int upperBound) {
        if (lowerBound > upperBound) {
            int tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }

        System.out.println(inpMsg);
        while (true) {
            try {
                int number = Integer.parseInt(sc.nextLine());
                if (number < lowerBound | number > upperBound) {
                    throw new Exception();
                }
                return number;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + errMsg + ConsoleColors.RESET);
            }
        }
    }

    public static float getFloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    public static String getYesNo(String inpMsg, String errMsg) {
        System.out.println(inpMsg);
        while (true) {
            try {
                String str = sc.nextLine();
                if (str.isEmpty() | !str.matches("^[yYnN]$")) {
                    throw new Exception();
                }
                return str;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + errMsg + ConsoleColors.RESET);
            }
        }
    }

}
