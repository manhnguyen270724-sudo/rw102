import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        String fullName = "Nguyễn Mạnh";
        int age = 22;
        LocalDate birthday = LocalDate.of(2004, 9, 11);

        System.out.println("FullName: " + fullName);
        System.out.println("Age: " + age);
        System.out.println("Birthday: " + birthday);

        // Tạo object Position bằng constructor
        Position position = new Position(1, PositionName.DEV);
        position.printInfo();
    }
}