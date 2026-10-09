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

    // Regex kiểm tra định dạng email tiêu chuẩn
    private static final java.util.regex.Pattern EMAIL_PATTERN = 
            java.util.regex.Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
}
