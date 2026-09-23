import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Exercise 3: Date Format
 *
 * Ghi chu ve cac pattern hay dung trong DateTimeFormatter:
 *   dd   -> ngay (2 chu so)
 *   MM   -> thang (2 chu so)     MMMM -> ten thang day du
 *   yyyy -> nam (4 chu so)
 *   HH   -> gio (0-23)
 *   mm   -> phut                 ss -> giay
 */
public class Exercise3 {

    /**
     * Question 1: In thong tin Exam thu 1, createDate format kieu Vietnamese
     * Dinh dang Viet Nam: ngay/thang/nam (khac My la thang/ngay/nam)
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        Exam exam = DataStorage.exams.get(0);

        // Locale "vi" giup ten thang, ten ngay duoc hien thi theo tieng Viet
        Locale vietnamLocale = Locale.forLanguageTag("vi-VN");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", vietnamLocale);

        System.out.println("Exam ID: " + exam.getExamId());
        System.out.println("Code: " + exam.getCode());
        System.out.println("Title: " + exam.getTitle());
        System.out.println("Category: " + exam.getCategory().getCategoryName());
        System.out.println("Duration: " + exam.getDuration() + " phut");
        System.out.println("Creator: " + exam.getCreator().getFullName());
        System.out.println("Create date (Vietnamese): " + exam.getCreateDate().format(formatter));
        System.out.println();
    }

    /**
     * Question 2: In ngay tao Exam theo dinh dang
     * Nam - thang - ngay - gio - phut - giay
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        LocalDateTime createDate = DataStorage.exams.get(0).getCreateDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy - MM - dd - HH - mm - ss");
        System.out.println("Exam duoc tao ngay: " + createDate.format(formatter));
        System.out.println();
    }

    /**
     * Question 3: Chi in ra nam cua createDate
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        LocalDateTime createDate = DataStorage.exams.get(0).getCreateDate();

        // Cach 1: dung formatter
        System.out.println("Nam (dung formatter): " + createDate.format(DateTimeFormatter.ofPattern("yyyy")));
        // Cach 2: dung method co san cua LocalDateTime
        System.out.println("Nam (dung getYear): " + createDate.getYear());
        System.out.println();
    }

    /**
     * Question 4: Chi in ra thang va nam cua createDate
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        LocalDateTime createDate = DataStorage.exams.get(0).getCreateDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        System.out.println("Thang - Nam: " + createDate.format(formatter));
        System.out.println();
    }

    /**
     * Question 5: Chi in ra "MM-DD" (thang - ngay) cua createDate
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        LocalDateTime createDate = DataStorage.exams.get(0).getCreateDate();

        // Luu y: trong Java dung "dd" (chu thuong) cho ngay trong thang
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        System.out.println("MM-DD: " + createDate.format(formatter));
        System.out.println();
    }
}
