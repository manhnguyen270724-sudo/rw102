/**
 * Exercise 6: Method
 */
public class Exercise6 {

    /**
     * Question 1: In ra cac so chan nguyen duong nho hon 10
     * Luu y: so nguyen duong la so > 0, nen khong tinh so 0
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        System.out.print("Cac so chan nguyen duong nho hon 10: ");

        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");
    }

    /**
     * Question 2: In thong tin cac account
     */
    public void question2() {
        System.out.println("===== Question 2 =====");

        for (Account account : DataStorage.accounts) {
            String departmentName = (account.getDepartment() == null)
                    ? "Chua co phong ban"
                    : account.getDepartment().getDepartmentName();

            System.out.println("Id: " + account.getAccountId()
                    + " | Email: " + account.getEmail()
                    + " | Full name: " + account.getFullName()
                    + " | Phong ban: " + departmentName
                    + " | Chuc vu: " + account.getPosition());
        }
        System.out.println();
    }

    /**
     * Question 3: In ra cac so nguyen duong nho hon 10
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        System.out.print("Cac so nguyen duong nho hon 10: ");

        for (int i = 1; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }
}
