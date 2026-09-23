/**
 * Exercise 3: Boxing & Unboxing
 *
 * Giai thich khai niem:
 *   - Kieu nguyen thuy (primitive): int, double, float, boolean...
 *     nam trong "kieu du lieu co ban", nhe, nhanh, KHONG the = null.
 *   - Kieu bao (wrapper class): Integer, Double, Float, Boolean...
 *     la CLASS boc quanh kieu nguyen thuy, la mot Object, CO THE = null.
 *
 *   Boxing   = tu dong "boc" primitive vao wrapper.   int    -> Integer
 *   Unboxing = tu dong "thao" wrapper ra primitive.   Integer -> int
 *
 * Java tu lam Boxing/Unboxing ngam dinh (auto-boxing/auto-unboxing) tu
 * ban 5 tro di, nhung hieu ro co che nay giup tranh loi NullPointerException
 * (khi mot Integer = null bi unbox thanh int).
 */
public class Exercise3 {

    /**
     * Question 1: Luong kieu Integer = 5000, convert ra float, hien thi 2 so
     * thap phan
     */
    public void question1() {
        System.out.println("===== Question 1 =====");

        Integer salary = 5000; // day chinh la AUTO-BOXING: int 5000 -> Integer

        // Muon doi Integer sang float, phai unbox ve int truoc (hoac dung
        // .floatValue() co san trong class Integer), sau do ep sang float
        float salaryAsFloat = salary.floatValue();

        System.out.println("Luong (Integer): " + salary);
        System.out.printf("Luong sau khi convert ra float: %.2f%n", salaryAsFloat);
        System.out.println();
    }

    /**
     * Question 2: String "1234567" -> convert ra int
     */
    public void question2() {
        System.out.println("===== Question 2 =====");

        String numberAsString = "1234567";

        // Integer.parseInt() la cach chuan de doi String thanh int
        // (khac voi Boxing/Unboxing - day la PARSE chuoi, khong phai boxing)
        int number = Integer.parseInt(numberAsString);

        System.out.println("String goc: \"" + numberAsString + "\"");
        System.out.println("Sau khi convert ra int: " + number);
        System.out.println();
    }

    /**
     * Question 3: Integer co gia tri la CHUOI "1234567" -> convert ra int
     *
     * Luu y: de bai ghi "Integer co value la chu '1234567'" - Integer thuc
     * su KHONG THE chua truc tiep 1 chuoi (no chi chua so nguyen). Cach hop
     * ly nhat la: dung Integer.valueOf() de parse chuoi do thanh doi tuong
     * Integer, sau do unbox ve int nhu Question 2.
     */
    public void question3() {
        System.out.println("===== Question 3 =====");

        String text = "1234567";

        // Integer.valueOf(String) parse chuoi thanh doi tuong Integer
        Integer numberObject = Integer.valueOf(text);

        // Unbox tu Integer ve int (Java tu dong lam, khong can viet gi them)
        int numberPrimitive = numberObject;

        System.out.println("Gia tri chuoi ban dau: \"" + text + "\"");
        System.out.println("Sau khi convert thanh Integer: " + numberObject);
        System.out.println("Sau khi unbox ve int: " + numberPrimitive);
        System.out.println();
    }
}
