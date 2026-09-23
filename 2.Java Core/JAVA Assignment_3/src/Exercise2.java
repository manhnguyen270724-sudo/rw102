import java.time.LocalDate;

/**
 * Exercise 2: Default value
 */
public class Exercise2 {

    /**
     * Question 1: Tao array Account gom 5 phan tu bang vong lap for
     * (khong dung data cu, tu tao moi hoan toan)
     *
     * "Default value" o day am chi: neu ban khai bao "new Account[5]" (array
     * cua kieu THAM CHIEU - reference type), moi phan tu se mac dinh la null
     * cho toi khi ban gan gia tri that vao. Day la diem khac biet so voi
     * array kieu nguyen thuy (int[], double[]...) - array so mac dinh la 0.
     */
    public void question1() {
        System.out.println("===== Question 1 =====");

        // Khai bao array 5 phan tu -> luc nay ca 5 phan tu deu la null
        Account[] accounts = new Account[5];
        System.out.println("Truoc khi khoi tao, accounts[0] = " + accounts[0]);

        // Dung vong lap for de khoi tao gia tri that cho tung phan tu
        for (int i = 0; i < accounts.length; i++) {
            int order = i + 1; // thu tu hien thi, bat dau tu 1 cho de doc
            accounts[i] = new Account(
                    order,
                    "Email " + order,
                    "User name " + order,
                    "Full name " + order,
                    null,          // chua gan phong ban
                    null,          // chua gan chuc vu
                    LocalDate.now()
            );
        }

        System.out.println("\nSau khi khoi tao bang vong lap for:");
        for (Account account : accounts) {
            System.out.println("Email: " + account.getEmail()
                    + " | Username: " + account.getUsername()
                    + " | FullName: " + account.getFullName()
                    + " | CreateDate: " + account.getCreateDate());
        }
        System.out.println();
    }
}
