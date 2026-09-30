import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Exercise 4: Random Number
 *
 * Ghi chu:
 *   random.nextInt()        -> so nguyen bat ky (co the am)
 *   random.nextInt(n)       -> so nguyen tu 0 den n-1
 *   random.nextDouble()     -> so thuc tu 0.0 den 1.0
 *   LocalDate.toEpochDay()  -> doi ngay thanh so ngay tinh tu 01/01/1970
 */
public class Exercise4 {

    private final Random random = new Random();

    /**
     * Question 1: In ngau nhien 1 so nguyen
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        System.out.println("So nguyen ngau nhien (bat ky): " + random.nextInt());
        System.out.println("So nguyen ngau nhien tu 0 den 99: " + random.nextInt(100));
        System.out.println();
    }

    /**
     * Question 2: In ngau nhien 1 so thuc
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        System.out.println("So thuc ngau nhien tu 0.0 den 1.0: " + random.nextDouble());
        System.out.printf("So thuc ngau nhien tu 0 den 100: %.2f%n", random.nextDouble() * 100);
        System.out.println();
    }

    /**
     * Question 3: Khai bao array ten cac ban trong lop, in ngau nhien 1 ten
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        String[] classmates = {
                "Nguyen Van An", "Tran Thi Binh", "Le Van Cuong",
                "Pham Thi Dung", "Hoang Van Em", "Vo Thi Phuong"
        };

        int randomIndex = random.nextInt(classmates.length);
        System.out.println("Ban duoc chon ngau nhien: " + classmates[randomIndex]);
        System.out.println();
    }

    /**
     * Question 4: Lay ngau nhien 1 ngay trong khoang 24-07-1995 den 20-12-1995
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        LocalDate startDate = LocalDate.of(1995, 7, 24);
        LocalDate endDate = LocalDate.of(1995, 12, 20);

        System.out.println("Ngay ngau nhien trong khoang: " + getRandomDateBetween(startDate, endDate));
        System.out.println();
    }

    /**
     * Question 5: Lay ngau nhien 1 ngay trong khoang 1 nam tro lai day
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);

        System.out.println("Ngay ngau nhien trong 1 nam qua: " + getRandomDateBetween(oneYearAgo, today));
        System.out.println();
    }

    /**
     * Question 6: Lay ngau nhien 1 ngay trong qua khu
     */
    public void question6() {
        System.out.println("===== Question 6 =====");
        LocalDate today = LocalDate.now();
        // Chon moc qua khu la 01/01/1970 (moc epoch) den hom qua
        LocalDate pastLimit = LocalDate.of(1970, 1, 1);

        System.out.println("Ngay ngau nhien trong qua khu: "
                + getRandomDateBetween(pastLimit, today.minusDays(1)));
        System.out.println();
    }

    /**
     * Question 7: Lay ngau nhien 1 so co 3 chu so
     */
    public void question7() {
        System.out.println("===== Question 7 =====");
        // So co 3 chu so nam trong khoang 100 -> 999
        int randomNumber = random.nextInt(900) + 100;
        System.out.println("So ngau nhien co 3 chu so: " + randomNumber);
        System.out.println();
    }

    /**
     * Method ho tro: lay 1 ngay ngau nhien giua 2 moc thoi gian
     * Cach lam: doi 2 ngay thanh so ngay (epoch day), random trong khoang do,
     * roi doi nguoc lai thanh LocalDate
     */
    private LocalDate getRandomDateBetween(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
