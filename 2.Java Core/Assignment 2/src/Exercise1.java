import java.util.List;

/**
 * Exercise 1: Flow Control
 * Moi Question duoc tach thanh 1 method rieng
 */
public class Exercise1 {

    // ==================================================================
    // IF
    // ==================================================================

    /**
     * Question 1: Kiem tra phong ban cua account thu 2 bang if-else
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        Account account = DataStorage.accounts.get(1);

        if (account.getDepartment() == null) {
            System.out.println("Nhan vien nay chua co phong ban");
        } else {
            System.out.println("Phong ban cua nhan vien nay la " + account.getDepartment().getDepartmentName());
        }
        System.out.println();
    }

    /**
     * Question 2: Kiem tra so luong group ma account thu 2 tham gia
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        Account account = DataStorage.accounts.get(1);
        List<Group> groupsOfAccount = DataStorage.getGroupsOfAccount(account);
        int totalGroup = groupsOfAccount.size();

        if (totalGroup == 0) {
            System.out.println("Nhan vien nay chua co group");
        } else if (totalGroup <= 2) {
            StringBuilder groupNames = new StringBuilder();
            for (int i = 0; i < groupsOfAccount.size(); i++) {
                groupNames.append(groupsOfAccount.get(i).getGroupName());
                if (i < groupsOfAccount.size() - 1) {
                    groupNames.append(", ");
                }
            }
            System.out.println("Group cua nhan vien nay la " + groupNames);
        } else if (totalGroup == 3) {
            System.out.println("Nhan vien nay la nguoi quan trong, tham gia nhieu group");
        } else {
            System.out.println("Nhan vien nay la nguoi hong chuyen, tham gia tat ca cac group");
        }
        System.out.println();
    }

    /**
     * Question 3: Lam lai Question 1 bang toan tu ternary
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        Account account = DataStorage.accounts.get(1);

        String result = (account.getDepartment() == null)
                ? "Nhan vien nay chua co phong ban"
                : "Phong ban cua nhan vien nay la " + account.getDepartment().getDepartmentName();
        System.out.println(result);
        System.out.println();
    }

    /**
     * Question 4: Kiem tra Position cua account thu 1 bang toan tu ternary
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        Account account = DataStorage.accounts.get(0);

        String result = (account.getPosition() == Position.DEV)
                ? "Day la Developer"
                : "Nguoi nay khong phai la Developer";
        System.out.println(result);
        System.out.println();
    }

    // ==================================================================
    // SWITCH CASE
    // ==================================================================

    /**
     * Question 5: Dem so luong account trong nhom thu 1 bang switch case
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        Group group = DataStorage.groups.get(0);
        int totalAccount = DataStorage.countAccountsInGroup(group);

        switch (totalAccount) {
            case 1:
                System.out.println("Nhom co mot thanh vien");
                break;
            case 2:
                System.out.println("Nhom co hai thanh vien");
                break;
            case 3:
                System.out.println("Nhom co ba thanh vien");
                break;
            default:
                System.out.println("Nhom co nhieu thanh vien");
                break;
        }
        System.out.println();
    }

    /**
     * Question 6: Lam lai Question 2 bang switch case
     */
    public void question6() {
        System.out.println("===== Question 6 =====");
        Account account = DataStorage.accounts.get(1);
        List<Group> groupsOfAccount = DataStorage.getGroupsOfAccount(account);
        int totalGroup = groupsOfAccount.size();

        switch (totalGroup) {
            case 0:
                System.out.println("Nhan vien nay chua co group");
                break;
            case 1:
            case 2:
                StringBuilder groupNames = new StringBuilder();
                for (int i = 0; i < groupsOfAccount.size(); i++) {
                    groupNames.append(groupsOfAccount.get(i).getGroupName());
                    if (i < groupsOfAccount.size() - 1) {
                        groupNames.append(", ");
                    }
                }
                System.out.println("Group cua nhan vien nay la " + groupNames);
                break;
            case 3:
                System.out.println("Nhan vien nay la nguoi quan trong, tham gia nhieu group");
                break;
            default:
                System.out.println("Nhan vien nay la nguoi hong chuyen, tham gia tat ca cac group");
                break;
        }
        System.out.println();
    }

    /**
     * Question 7: Lam lai Question 4 bang switch case
     */
    public void question7() {
        System.out.println("===== Question 7 =====");
        Account account = DataStorage.accounts.get(0);

        switch (account.getPosition()) {
            case DEV:
                System.out.println("Day la Developer");
                break;
            default:
                System.out.println("Nguoi nay khong phai la Developer");
                break;
        }
        System.out.println();
    }

    // ==================================================================
    // FOREACH
    // ==================================================================

    /**
     * Question 8: In thong tin cac account bang vong lap foreach
     */
    public void question8() {
        System.out.println("===== Question 8 =====");
        for (Account account : DataStorage.accounts) {
            String departmentName = (account.getDepartment() == null)
                    ? "Chua co phong ban"
                    : account.getDepartment().getDepartmentName();
            System.out.println("Email: " + account.getEmail()
                    + " | Full name: " + account.getFullName()
                    + " | Phong ban: " + departmentName);
        }
        System.out.println();
    }

    /**
     * Question 9: In thong tin cac phong ban bang vong lap foreach
     */
    public void question9() {
        System.out.println("===== Question 9 =====");
        for (Department department : DataStorage.departments) {
            System.out.println("Id: " + department.getDepartmentId()
                    + " | Name: " + department.getDepartmentName());
        }
        System.out.println();
    }

    // ==================================================================
    // FOR
    // ==================================================================

    /**
     * Question 10: In thong tin cac account theo dinh dang bang vong lap for
     */
    public void question10() {
        System.out.println("===== Question 10 =====");
        for (int i = 0; i < DataStorage.accounts.size(); i++) {
            Account account = DataStorage.accounts.get(i);
            String departmentName = (account.getDepartment() == null)
                    ? "Chua co phong ban"
                    : account.getDepartment().getDepartmentName();

            System.out.println("Thong tin account thu " + (i + 1) + " la:");
            System.out.println("\tEmail: " + account.getEmail());
            System.out.println("\tFull name: " + account.getFullName());
            System.out.println("\tPhong ban: " + departmentName);
            System.out.println();
        }
    }

    /**
     * Question 11: In thong tin cac phong ban theo dinh dang bang vong lap for
     */
    public void question11() {
        System.out.println("===== Question 11 =====");
        for (int i = 0; i < DataStorage.departments.size(); i++) {
            Department department = DataStorage.departments.get(i);

            System.out.println("Thong tin department thu " + (i + 1) + " la:");
            System.out.println("\tId: " + department.getDepartmentId());
            System.out.println("\tName: " + department.getDepartmentName());
            System.out.println();
        }
    }

    /**
     * Question 12: Chi in ra thong tin 2 department dau tien
     */
    public void question12() {
        System.out.println("===== Question 12 =====");
        for (int i = 0; i < DataStorage.departments.size(); i++) {
            if (i >= 2) {
                break;
            }
            Department department = DataStorage.departments.get(i);

            System.out.println("Thong tin department thu " + (i + 1) + " la:");
            System.out.println("\tId: " + department.getDepartmentId());
            System.out.println("\tName: " + department.getDepartmentName());
            System.out.println();
        }
    }

    /**
     * Question 13: In thong tin tat ca account ngoai tru account thu 2
     */
    public void question13() {
        System.out.println("===== Question 13 =====");
        for (int i = 0; i < DataStorage.accounts.size(); i++) {
            if (i == 1) {
                continue;
            }
            Account account = DataStorage.accounts.get(i);
            String departmentName = (account.getDepartment() == null)
                    ? "Chua co phong ban"
                    : account.getDepartment().getDepartmentName();

            System.out.println("Thong tin account thu " + (i + 1) + " la:");
            System.out.println("\tEmail: " + account.getEmail());
            System.out.println("\tFull name: " + account.getFullName());
            System.out.println("\tPhong ban: " + departmentName);
            System.out.println();
        }
    }

    /**
     * Question 14: In thong tin tat ca account co id < 4
     */
    public void question14() {
        System.out.println("===== Question 14 =====");
        for (Account account : DataStorage.accounts) {
            if (account.getAccountId() < 4) {
                String departmentName = (account.getDepartment() == null)
                        ? "Chua co phong ban"
                        : account.getDepartment().getDepartmentName();

                System.out.println("Id: " + account.getAccountId()
                        + " | Email: " + account.getEmail()
                        + " | Full name: " + account.getFullName()
                        + " | Phong ban: " + departmentName);
            }
        }
        System.out.println();
    }

    /**
     * Question 15: In ra cac so chan nho hon hoac bang 20
     */
    public void question15() {
        System.out.println("===== Question 15 =====");
        for (int i = 0; i <= 20; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");
    }
}
