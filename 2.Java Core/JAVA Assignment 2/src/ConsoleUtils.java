import java.util.Scanner;

/**
 * ConsoleUtils - noi giu doi tuong Scanner dung chung
 *
 * Ly do phai dung chung 1 Scanner:
 * Scanner co bo dem (buffer) rieng. Neu moi class tu tao 1 Scanner moi tren
 * System.in, Scanner nay se doc va giu lai du lieu trong buffer cua no,
 * khien Scanner cua class khac khong doc duoc gi -> loi NoSuchElementException.
 */
public class ConsoleUtils {

    public static final Scanner SCANNER = new Scanner(System.in);

    // Khong cho phep tao doi tuong cua class tien ich nay
    private ConsoleUtils() {
    }
}
