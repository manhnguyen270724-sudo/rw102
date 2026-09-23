/**
 * Exercise 1: Datatype Casting
 *
 * Ghi chu chung ve ep kieu (casting) trong Java:
 *   - Ep tu kieu "nho" sang kieu "lon" (vd: int -> float) : Java tu lam,
 *     khong can viet gi them, goi la "implicit casting" (ep kieu ngam dinh).
 *   - Ep tu kieu "lon" sang kieu "nho" (vd: float -> int) : PHAI ep tuong
 *     minh bang cach viet (int), (float)... truoc bien, goi la "explicit
 *     casting" (ep kieu tuong minh), vi co the bi MAT DU LIEU.
 */
public class Exercise1 {

    /**
     * Question 1: Khai bao 2 luong kieu float, lam tron ra so int
     *
     * Luu y quan trong: ep (int) tu float sang int trong Java se CAT BO
     * phan thap phan (khong lam tron toan hoc), vi du (int) 5240.9f = 5240
     * chu khong phai 5241. Neu muon lam tron dung nghia (5240.5 -> 5241 hoac
     * 5240 tuy quy uoc), nen dung Math.round() thay vi ep kieu truc tiep.
     */
    public void question1() {
        System.out.println("===== Question 1 =====");

        float salaryAccount1 = 5240.5f;
        float salaryAccount2 = 10970.055f;

        // Cach 1: ep kieu truc tiep (int) -> CAT BO phan thap phan
        int roundedSalary1ByCast = (int) salaryAccount1;
        int roundedSalary2ByCast = (int) salaryAccount2;

        // Cach 2: dung Math.round() -> LAM TRON dung nghia toan hoc
        int roundedSalary1ByMathRound = Math.round(salaryAccount1);
        int roundedSalary2ByMathRound = Math.round(salaryAccount2);

        System.out.println("Luong Account 1 (float): " + salaryAccount1);
        System.out.println("  -> ep kieu (int): " + roundedSalary1ByCast);
        System.out.println("  -> Math.round(): " + roundedSalary1ByMathRound);

        System.out.println("Luong Account 2 (float): " + salaryAccount2);
        System.out.println("  -> ep kieu (int): " + roundedSalary2ByCast);
        System.out.println("  -> Math.round(): " + roundedSalary2ByMathRound);
        System.out.println();
    }

    /**
     * Question 2: Lay ngau nhien 1 so co 5 chu so
     * (so duoi 5 chu so thi them so 0 o dau cho du 5 chu so)
     *
     * Ky thuat: dung String.format("%05d", number) de tu dong dem so 0
     * vao truoc cho du 5 ky tu. Vi du so 42 -> "00042".
     */
    public void question2() {
        System.out.println("===== Question 2 =====");

        // random tu 0 den 99999 (100_000 gia tri, du de lap day 5 chu so)
        int randomNumber = (int) (Math.random() * 100_000);

        // %05d nghia la: so nguyen (d), do rong toi thieu 5 ky tu (5),
        // neu thieu thi dem so 0 vao truoc (0)
        String formattedNumber = String.format("%05d", randomNumber);

        System.out.println("So ngau nhien goc: " + randomNumber);
        System.out.println("So sau khi dam bao du 5 chu so: " + formattedNumber);
        System.out.println();
    }

    /**
     * Question 3: Lay 2 so cuoi cua so o Question 2
     */
    public void question3() {
        System.out.println("===== Question 3 =====");

        int randomNumber = (int) (Math.random() * 100_000);
        String formattedNumber = String.format("%05d", randomNumber);
        System.out.println("So 5 chu so: " + formattedNumber);

        // Cach 1: convert ra String, lay 2 ky tu cuoi bang substring()
        // substring(length - 2) nghia la lay tu vi tri (length-2) den het chuoi
        String lastTwoDigitsByString = formattedNumber.substring(formattedNumber.length() - 2);
        System.out.println("Cach 1 (substring): " + lastTwoDigitsByString);

        // Cach 2: chia lay du cho 100 -> chi con lai 2 chu so cuoi
        // Vi du 12345 % 100 = 45
        int lastTwoDigitsByModulo = randomNumber % 100;
        System.out.println("Cach 2 (% 100): " + lastTwoDigitsByModulo);
        System.out.println();
    }

    /**
     * Question 4: Method nhap vao 2 so nguyen a, b va tra ve thuong cua chung
     */
    public void question4() {
        System.out.println("===== Question 4 =====");

        double result1 = divide(10, 3);
        double result2 = divide(20, 4);
        double result3 = divide(7, 2);

        System.out.println("10 / 3 = " + result1);
        System.out.println("20 / 4 = " + result2);
        System.out.println("7 / 2 = " + result3);
        System.out.println();
    }

    /**
     * Tra ve thuong (phep chia) cua 2 so nguyen a va b
     *
     * Luu y RAT QUAN TRONG: neu a va b deu la int, phep "a / b" trong Java
     * la CHIA NGUYEN (bo phan du), vi du 7 / 2 = 3 (KHONG phai 3.5).
     * De ra ket qua co phan thap phan, phai ep it nhat 1 so sang double
     * TRUOC KHI chia: (double) a / b.
     */
    public double divide(int a, int b) {
        if (b == 0) {
            System.out.println("Canh bao: khong the chia cho 0!");
            return 0;
        }
        return (double) a / b;
    }
}
