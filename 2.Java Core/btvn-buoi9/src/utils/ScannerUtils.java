package utils;

import java.util.Scanner;

/**
 * Lớp tiện ích hỗ trợ đọc dữ liệu an toàn từ Scanner console.
 */
public class ScannerUtils {

    public static int inputInt(Scanner sc, String errorMessage) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println(errorMessage);
                System.out.print("Vui lòng nhập lại: ");
            }
        }
    }

    public static int inputIntPositive(Scanner sc, String errorMessage) {
        while (true) {
            int value = inputInt(sc, errorMessage);
            if (value > 0) {
                return value;
            }
            System.err.println(errorMessage);
            System.out.print("Vui lòng nhập lại: ");
        }
    }

    public static String inputString(Scanner sc, String errorMessage) {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.err.println(errorMessage);
            System.out.print("Vui lòng nhập lại: ");
        }
    }
}
