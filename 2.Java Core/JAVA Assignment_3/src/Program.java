import java.util.Scanner;

/**
 * Program - class chua main() method
 * Co menu de nguoi dung chon Exercise muon chay
 */
public class Program {

    private static final Scanner scanner = ConsoleUtils.SCANNER;

    public static void main(String[] args) {
        while (true) {
            System.out.println("########## TESTING SYSTEM ASSIGNMENT 3 ##########");
            System.out.println("\t1. Exercise 1 - Datatype Casting");
            System.out.println("\t2. Exercise 2 - Default value");
            System.out.println("\t3. Exercise 3 - Boxing & Unboxing");
            System.out.println("\t4. Exercise 4 - String");
            System.out.println("\t5. Exercise 5 - Object's Method");
            System.out.println("\t6. *** BTVN BAT BUOC *** (Exercise 4 toan bo + Exercise 5 cau 5,6,7)");
            System.out.println("\t0. Thoat chuong trinh");
            System.out.print("Chon Exercise muon chay: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    runExercise1();
                    break;
                case "2":
                    runExercise2();
                    break;
                case "3":
                    runExercise3();
                    break;
                case "4":
                    runExercise4();
                    break;
                case "5":
                    runExercise5();
                    break;
                case "6":
                    runMandatoryHomework();
                    break;
                case "0":
                    System.out.println("Ket thuc chuong trinh. Cam on ban!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Moi ban nhap lai!\n");
            }
        }
    }

    private static void runExercise1() {
        System.out.println("\n===== EXERCISE 1: DATATYPE CASTING =====\n");
        Exercise1 exercise = new Exercise1();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
    }

    private static void runExercise2() {
        System.out.println("\n===== EXERCISE 2: DEFAULT VALUE =====\n");
        Exercise2 exercise = new Exercise2();

        exercise.question1();
    }

    private static void runExercise3() {
        System.out.println("\n===== EXERCISE 3: BOXING & UNBOXING =====\n");
        Exercise3 exercise = new Exercise3();

        exercise.question1();
        exercise.question2();
        exercise.question3();
    }

    /**
     * Exercise 4 co nhieu question can nhap console, tach thanh menu con
     * de nguoi dung chon tung Question muon test
     */
    private static void runExercise4() {
        Exercise4 exercise = new Exercise4();

        while (true) {
            System.out.println("\n===== EXERCISE 4: STRING =====");
            System.out.println("\t1.  Question 1  - Dem so tu trong cau");
            System.out.println("\t2.  Question 2  - Noi 2 chuoi");
            System.out.println("\t3.  Question 3  - Viet hoa chu cai dau ten");
            System.out.println("\t4.  Question 4  - In tung ky tu cua ten");
            System.out.println("\t5.  Question 5  - Ghep ho va ten");
            System.out.println("\t6.  Question 6  - Tach ho, ten dem, ten");
            System.out.println("\t7.  Question 7  - Chuan hoa ho ten");
            System.out.println("\t8.  Question 8  - Group chua chu Java");
            System.out.println("\t9.  Question 9  - Group ten dung la Java");
            System.out.println("\t10. Question 10 - Kiem tra 2 chuoi dao nguoc nhau");
            System.out.println("\t11. Question 11 - Dem ky tu 'a'");
            System.out.println("\t12. Question 12 - Dao nguoc chuoi");
            System.out.println("\t13. Question 13 - Kiem tra chuoi co chua chu so");
            System.out.println("\t14. Question 14 - Thay the ky tu");
            System.out.println("\t15. Question 15 - Dao nguoc theo tu");
            System.out.println("\t16. Question 16 - Chia chuoi thanh n phan");
            System.out.println("\t0.  Quay lai menu chinh");
            System.out.print("Chon Question muon chay: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": exercise.question1(); break;
                case "2": exercise.question2(); break;
                case "3": exercise.question3(); break;
                case "4": exercise.question4(); break;
                case "5": exercise.question5(); break;
                case "6": exercise.question6(); break;
                case "7": exercise.question7(); break;
                case "8": exercise.question8(); break;
                case "9": exercise.question9(); break;
                case "10": exercise.question10(); break;
                case "11": exercise.question11(); break;
                case "12": exercise.question12(); break;
                case "13": exercise.question13(); break;
                case "14": exercise.question14(); break;
                case "15": exercise.question15(); break;
                case "16": exercise.question16(); break;
                case "0":
                    System.out.println();
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Moi ban nhap lai!");
            }
        }
    }

    /**
     * Chay dung phan BTVN BAT BUOC theo yeu cau:
     *   - Exercise 4: TOAN BO (Question 1 -> 16)
     *   - Exercise 5: CHI Question 5, 6, 7 (Question 1-4 la optional, khong bat buoc)
     *
     * Luu y: Exercise 4 hau het cac Question deu can nhap du lieu tu console,
     * nen khi chay muc nay chuong trinh se lan luot hoi nhap cho tung Question,
     * giong nhu chay tuan tu (khac voi muc "4. Exercise 4" o menu chinh la
     * mot menu con cho phep chon rieng tung Question).
     */
    private static void runMandatoryHomework() {
        System.out.println("\n========== BTVN BAT BUOC ==========");

        System.out.println("\n----- Exercise 4: String (toan bo) -----\n");
        Exercise4 exercise4 = new Exercise4();
        exercise4.question1();
        exercise4.question2();
        exercise4.question3();
        exercise4.question4();
        exercise4.question5();
        exercise4.question6();
        exercise4.question7();
        exercise4.question8();
        exercise4.question9();
        exercise4.question10();
        exercise4.question11();
        exercise4.question12();
        exercise4.question13();
        exercise4.question14();
        exercise4.question15();
        exercise4.question16();

        System.out.println("\n----- Exercise 5: Object's Method (chi Question 5, 6, 7) -----\n");
        Exercise5 exercise5 = new Exercise5();
        exercise5.question5();
        exercise5.question6();
        exercise5.question7();

        System.out.println("========== HOAN THANH BTVN BAT BUOC ==========\n");
    }

    private static void runExercise5() {
        System.out.println("\n===== EXERCISE 5: OBJECT'S METHOD =====\n");
        Exercise5 exercise = new Exercise5();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
        exercise.question5();
        exercise.question6();
        exercise.question7();
    }
}
