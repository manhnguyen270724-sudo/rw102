import java.util.Scanner;

/**
 * Exercise 4: String
 *
 * Ghi chu chung ve String trong Java:
 *   - String la IMMUTABLE (bat bien): moi lan "sua" mot String (concat, replace,
 *     toUpperCase...) thuc chat la TAO RA 1 CHUOI MOI, chuoi cu khong doi.
 *   - split("\\s+") : tach chuoi tai NHIEU khoang trang lien tiep, huu ich khi
 *     nguoi dung go nhieu dau cach giua cac tu.
 *   - trim() : xoa khoang trang o DAU va CUOI chuoi (khong dong den giua).
 */
public class Exercise4 {

    private final Scanner scanner = ConsoleUtils.SCANNER;

    /**
     * Question 1: Nhap 1 xau ky tu, dem so luong tu (cac tu co the cach nhau
     * boi nhieu khoang trang)
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        System.out.print("Nhap 1 cau: ");
        String input = scanner.nextLine();

        // trim() bo khoang trang thua o dau/cuoi, tranh sinh ra "tu rong"
        // split("\\s+") tach tai 1 hoac nhieu khoang trang lien tiep
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            System.out.println("So luong tu: 0");
        } else {
            String[] words = trimmedInput.split("\\s+");
            System.out.println("So luong tu: " + words.length);
        }
        System.out.println();
    }

    /**
     * Question 2: Nhap 2 xau s1, s2, noi s2 vao sau s1
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        System.out.print("Nhap xau s1: ");
        String s1 = scanner.nextLine();
        System.out.print("Nhap xau s2: ");
        String s2 = scanner.nextLine();

        // Co the dung "+" hoac s1.concat(s2), ket qua giong nhau
        String result = s1 + s2;
        System.out.println("Sau khi noi: " + result);
        System.out.println();
    }

    /**
     * Question 3: Nhap ten, neu chua viet hoa chu cai dau thi viet hoa len
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        System.out.print("Nhap ten: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Ban chua nhap gi ca!");
            System.out.println();
            return;
        }

        char firstChar = name.charAt(0);
        String result;

        if (Character.isUpperCase(firstChar)) {
            // Da viet hoa san, giu nguyen
            result = name;
        } else {
            // Chua viet hoa -> viet hoa ky tu dau, giu nguyen phan con lai
            // charAt(0) lay ky tu dau, substring(1) lay tu ky tu thu 2 den het
            result = Character.toUpperCase(firstChar) + name.substring(1);
        }

        System.out.println("Ten sau khi chuan hoa: " + result);
        System.out.println();
    }

    /**
     * Question 4: Nhap ten, in tung ky tu ra
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        System.out.print("Nhap ten: ");
        String name = scanner.nextLine();

        // charAt(i) lay ky tu tai vi tri i (i bat dau tu 0)
        for (int i = 0; i < name.length(); i++) {
            System.out.println("Ky tu thu " + (i + 1) + " la: " + name.charAt(i));
        }
        System.out.println();
    }

    /**
     * Question 5: Nhap ho, roi nhap ten, in ho ten day du
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        System.out.print("Nhap ho: ");
        String lastName = scanner.nextLine();
        System.out.print("Nhap ten: ");
        String firstName = scanner.nextLine();

        String fullName = lastName + " " + firstName;
        System.out.println("Ho ten day du: " + fullName);
        System.out.println();
    }

    /**
     * Question 6: Nhap ho ten day du, tach ra ho, ten dem, ten
     *
     * Cach lam: tach chuoi thanh cac tu (split theo khoang trang).
     * Quy uoc: TU DAU la ho, TU CUOI la ten, cac tu CON LAI o giua la ten dem.
     */
    public void question6() {
        System.out.println("===== Question 6 =====");
        System.out.print("Nhap ho ten day du: ");
        String fullName = scanner.nextLine().trim();

        String[] words = fullName.split("\\s+");

        if (words.length < 2) {
            System.out.println("Ho ten phai co it nhat 2 tu (ho va ten)!");
            System.out.println();
            return;
        }

        String lastName = words[0];                    // tu dau tien
        String firstName = words[words.length - 1];     // tu cuoi cung

        // Ghep cac tu o giua (neu co) lam ten dem
        StringBuilder middleNameBuilder = new StringBuilder();
        for (int i = 1; i < words.length - 1; i++) {
            if (middleNameBuilder.length() > 0) {
                middleNameBuilder.append(" ");
            }
            middleNameBuilder.append(words[i]);
        }
        String middleName = middleNameBuilder.toString();

        System.out.println("Ho la: " + lastName);
        System.out.println("Ten dem la: " + (middleName.isEmpty() ? "(khong co)" : middleName));
        System.out.println("Ten la: " + firstName);
        System.out.println();
    }

    /**
     * Question 7: Nhap ho ten, chuan hoa:
     * a) Xoa khoang cach thua o dau/cuoi/giua
     * b) Viet hoa chu cai dau moi tu
     */
    public void question7() {
        System.out.println("===== Question 7 =====");
        System.out.print("Nhap ho ten (co the go du khoang trang): ");
        String input = scanner.nextLine();

        // Buoc a: trim() xoa dau/cuoi, replaceAll("\\s+", " ") gom nhieu
        // khoang trang lien tiep o GIUA thanh 1 khoang trang duy nhat
        String normalizedSpacing = input.trim().replaceAll("\\s+", " ");

        // Buoc b: viet hoa chu cai dau cua TUNG tu
        String[] words = normalizedSpacing.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                result.append(capitalizedWord);
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        System.out.println("Truoc chuan hoa : \"" + input + "\"");
        System.out.println("Sau chuan hoa   : \"" + result + "\"");
        System.out.println();
    }

    /**
     * Question 8: In tat ca group co CHUA chu "Java" trong ten
     * (vi du "Java Fresher", "Java Basic" deu thoa man)
     */
    public void question8() {
        System.out.println("===== Question 8 =====");
        System.out.println("Cac group co chua chu \"Java\":");

        for (Group group : DataStorage.groups) {
            // contains() kiem tra chuoi con co xuat hien ben trong khong,
            // phan biet hoa/thuong
            if (group.getGroupName().contains("Java")) {
                System.out.println("\t- " + group.getGroupName());
            }
        }
        System.out.println();
    }

    /**
     * Question 9: In tat ca group co ten DUNG BANG "Java"
     * (khac Question 8: "Java Fresher" KHONG thoa man, chi "Java" moi thoa man)
     */
    public void question9() {
        System.out.println("===== Question 9 =====");
        System.out.println("Cac group co ten dung la \"Java\":");

        for (Group group : DataStorage.groups) {
            // equals() so sanh CHINH XAC tung ky tu, khong hon khong kem
            if (group.getGroupName().equals("Java")) {
                System.out.println("\t- " + group.getGroupName());
            }
        }
        System.out.println();
    }

    /**
     * Question 10: Kiem tra 2 chuoi co la dao nguoc cua nhau khong
     */
    public void question10() {
        System.out.println("===== Question 10 =====");
        System.out.print("Nhap chuoi thu 1: ");
        String s1 = scanner.nextLine();
        System.out.print("Nhap chuoi thu 2: ");
        String s2 = scanner.nextLine();

        String reversedS2 = reverseString(s2);

        if (s1.equals(reversedS2)) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }
        System.out.println();
    }

    /**
     * Question 11: Dem so lan xuat hien ky tu 'a' trong chuoi
     */
    public void question11() {
        System.out.println("===== Question 11 =====");
        System.out.print("Nhap 1 chuoi: ");
        String input = scanner.nextLine();

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'a') {
                count++;
            }
        }

        System.out.println("Ky tu 'a' xuat hien " + count + " lan trong chuoi \"" + input + "\"");
        System.out.println();
    }

    /**
     * Question 12: Dao nguoc chuoi su dung vong lap
     */
    public void question12() {
        System.out.println("===== Question 12 =====");
        System.out.print("Nhap 1 chuoi: ");
        String input = scanner.nextLine();

        System.out.println("Chuoi sau khi dao nguoc: " + reverseString(input));
        System.out.println();
    }

    /**
     * Question 13: Kiem tra chuoi co chua chu so hay khong
     * Neu CO chu so -> in ra false. Neu KHONG co chu so -> in ra true.
     * Truong hop null cung tra ve false.
     *
     * De bai cho san bo test case, nen minh chay thu truc tiep cac gia tri do
     * thay vi nhap tu console, de ban de doi chieu ket qua.
     */
    public void question13() {
        System.out.println("===== Question 13 =====");

        String[] testCases = {"abc", "1abc", "abc1", "123", "a1bc", null};

        for (String testCase : testCases) {
            System.out.println("hasNoDigit(" + testCase + ") = " + hasNoDigit(testCase));
        }
        System.out.println();
    }

    /**
     * Question 14: Thay the ky tu chi dinh sang ky tu khac
     * Vi du: "VTI Academy" thay 'e' -> '*' ra "VTI Acad*my"
     */
    public void question14() {
        System.out.println("===== Question 14 =====");

        String original = "VTI Academy";
        char oldChar = 'e';
        char newChar = '*';

        // replace() cua String da lam san viec nay, khong can viet vong lap
        String result = original.replace(oldChar, newChar);

        System.out.println("Chuoi goc: " + original);
        System.out.println("Sau khi thay '" + oldChar + "' bang '" + newChar + "': " + result);
        System.out.println();
    }

    /**
     * Question 15: Dao nguoc thu tu CAC TU trong chuoi, khong dung thu vien
     * (khong dung split(), khong dung StringBuilder.reverse()...)
     * Vi du: " I am developer " -> "developer am I"
     */
    public void question15() {
        System.out.println("===== Question 15 =====");
        System.out.print("Nhap 1 cau: ");
        String input = scanner.nextLine();

        System.out.println("Sau khi dao nguoc theo tu: " + reverseWordsManually(input));
        System.out.println();
    }

    /**
     * Question 16: Chia chuoi str thanh cac phan bang nhau, moi phan n ky tu
     * Neu khong chia het thi in "KO"
     */
    public void question16() {
        System.out.println("===== Question 16 =====");
        System.out.print("Nhap chuoi str: ");
        String str = scanner.nextLine();
        System.out.print("Nhap so n (do dai moi phan): ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        if (n <= 0 || str.length() % n != 0) {
            System.out.println("KO");
            System.out.println();
            return;
        }

        System.out.print("Ket qua chia: ");
        // Nhay tung buoc n ky tu mot: 0, n, 2n, 3n...
        for (int i = 0; i < str.length(); i += n) {
            System.out.print("[" + str.substring(i, i + n) + "] ");
        }
        System.out.println("\n");
    }

    // ==================================================================
    // Cac method ho tro (dung chung cho nhieu question ben tren)
    // ==================================================================

    /**
     * Dao nguoc 1 chuoi bang vong lap (khong dung StringBuilder.reverse())
     */
    private String reverseString(String input) {
        StringBuilder reversed = new StringBuilder();
        // Duyet tu ky tu CUOI ve ky tu DAU
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }
        return reversed.toString();
    }

    /**
     * Kiem tra chuoi co chua chu so hay khong
     * Tra ve true neu KHONG co chu so nao (va khac null)
     * Tra ve false neu CO it nhat 1 chu so, hoac chuoi la null
     */
    private boolean hasNoDigit(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Dao nguoc thu tu cac tu trong 1 cau, KHONG dung split()/reverse() co san
     * Cach lam thu cong:
     *   1. Tu duyet tung ky tu de tu tach cac "tu" (nhom ky tu khong phai
     *      khoang trang) ra thanh 1 danh sach.
     *   2. In danh sach do theo thu tu NGUOC LAI.
     */
    private String reverseWordsManually(String input) {
        // Buoc 1: tu tach cac tu bang tay, khong dung String.split()
        String[] words = new String[input.length()]; // toi da co the co bang nay tu
        int wordCount = 0;
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ') {
                if (currentWord.length() > 0) {
                    words[wordCount] = currentWord.toString();
                    wordCount++;
                    currentWord = new StringBuilder();
                }
            } else {
                currentWord.append(c);
            }
        }
        // Dung quen tu CUOI CUNG (khong co dau cach sau no de "chot")
        if (currentWord.length() > 0) {
            words[wordCount] = currentWord.toString();
            wordCount++;
        }

        // Buoc 2: ghep lai theo thu tu nguoc, tu tu cuoi ve tu dau
        StringBuilder result = new StringBuilder();
        for (int i = wordCount - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
