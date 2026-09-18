import java.util.Scanner;


/**
 * Program - class chua main() method
 * Co menu de nguoi dung chon Exercise muon chay
 */
public class Program {

    private static final Scanner scanner = ConsoleUtils.SCANNER;

    public static void main(String[] args) {
        while (true) {
            System.out.println("########## TESTING SYSTEM ASSIGNMENT 2 ##########");
            System.out.println("\t1. Exercise 1 - Flow Control");
            System.out.println("\t2. Exercise 2 - System.out.printf");
            System.out.println("\t3. Exercise 3 - Date Format");
            System.out.println("\t4. Exercise 4 - Random Number");
            System.out.println("\t5. Exercise 5 - Input from Console");
            System.out.println("\t6. Exercise 6 - Method");
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
                    runExercise6();
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
        System.out.println("\n===== EXERCISE 1: FLOW CONTROL =====\n");
        Exercise1 exercise = new Exercise1();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
        exercise.question5();
        exercise.question6();
        exercise.question7();
        exercise.question8();
        exercise.question9();
        exercise.question10();
        exercise.question11();
        exercise.question12();
        exercise.question13();
        exercise.question14();
        exercise.question15();
        exercise.question16();
        exercise.question17();
    }

    private static void runExercise2() {
        System.out.println("\n===== EXERCISE 2: SYSTEM.OUT.PRINTF =====\n");
        Exercise2 exercise = new Exercise2();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
        exercise.question5();
        exercise.question6();
    }

    private static void runExercise3() {
        System.out.println("\n===== EXERCISE 3: DATE FORMAT =====\n");
        Exercise3 exercise = new Exercise3();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
        exercise.question5();
    }

    private static void runExercise4() {
        System.out.println("\n===== EXERCISE 4: RANDOM NUMBER =====\n");
        Exercise4 exercise = new Exercise4();

        exercise.question1();
        exercise.question2();
        exercise.question3();
        exercise.question4();
        exercise.question5();
        exercise.question6();
        exercise.question7();
    }

    /**
     * Exercise 5 can nhap du lieu tu console nen tach thanh menu con
     * de nguoi dung chon tung Question muon test
     */
    private static void runExercise5() {
        Exercise5 exercise = new Exercise5();

        while (true) {
            System.out.println("\n===== EXERCISE 5: INPUT FROM CONSOLE =====");
            System.out.println("\t1. Question 1 - Nhap 3 so nguyen");
            System.out.println("\t2. Question 2 - Nhap 2 so thuc");
            System.out.println("\t3. Question 3 - Nhap ho va ten");
            System.out.println("\t4. Question 4 - Nhap ngay sinh nhat");
            System.out.println("\t5. Question 5 - Tao account");
            System.out.println("\t6. Question 6 - Tao department");
            System.out.println("\t7. Question 7 - Nhap so chan");
            System.out.println("\t8. Question 8 + 10 + 11 - Menu chuc nang");
            System.out.println("\t9. Question 9 - Them group vao account");
            System.out.println("\t0. Quay lai menu chinh");
            System.out.print("Chon Question muon chay: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    exercise.question1();
                    break;
                case "2":
                    exercise.question2();
                    break;
                case "3":
                    exercise.question3();
                    break;
                case "4":
                    exercise.question4();
                    break;
                case "5":
                    exercise.question5();
                    break;
                case "6":
                    exercise.question6();
                    break;
                case "7":
                    exercise.question7();
                    break;
                case "8":
                    exercise.question8();
                    break;
                case "9":
                    exercise.question9();
                    break;
                case "0":
                    System.out.println();
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Moi ban nhap lai!");
            }
        }
    }

    private static void runExercise6() {
        System.out.println("\n===== EXERCISE 6: METHOD =====\n");
        Exercise6 exercise = new Exercise6();

        exercise.question1();
        exercise.question2();
        exercise.question3();
    }
}
