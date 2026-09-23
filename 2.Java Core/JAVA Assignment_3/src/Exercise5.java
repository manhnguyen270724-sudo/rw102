import java.util.Arrays;

/**
 * Exercise 5: Object's Method
 *
 * Nhac lai: MOI class trong Java (ke ca class ban tu viet) deu ke thua
 * ngam dinh tu class Object. Object cung cap san mot vai method, trong do
 * quan trong nhat la:
 *   toString() -> chuyen object thanh chuoi de hien thi
 *   equals()   -> so sanh 2 object co "bang nhau" khong
 *   hashCode() -> sinh ra 1 ma so dai dien cho object (dung trong HashMap...)
 *
 * Neu KHONG override, Java se dung phien ban MAC DINH cua Object, vi du
 * toString() mac dinh se in ra dang "TenClass@diachihex" - day khong phai
 * "dia chi RAM that", ma la mot ma bam (hash) duoc bieu dien duoi dang so hex.
 */
public class Exercise5 {

    /**
     * Question 1: In thong tin phong ban thu 1 (dung toString())
     * (optional - khong bat buoc, theo yeu cau BTVN chi bat buoc Q5,6,7)
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        Department department = DataStorage.departments.get(0);

        // Khi ban viet System.out.println(object), Java TU DONG goi
        // object.toString() ben trong - ban khong can goi tay .toString()
        System.out.println(department);
        System.out.println();
    }

    /**
     * Question 2: In thong tin tat ca phong ban (dung toString())
     * (optional)
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        for (Department department : DataStorage.departments) {
            System.out.println(department);
        }
        System.out.println();
    }

    /**
     * Question 3: In ra "dia chi" cua phong ban thu 1
     * (optional)
     *
     * Vi Department cua chung ta DA override toString() (in ra dang dep,
     * de doc), nen goi department.toString() se KHONG cho ra dia chi/hash.
     * De thay duoc dang mac dinh nhu Object cung cap, minh tu ghep lai
     * cong thuc ma Object.toString() dung: getClass().getName() + "@" +
     * Integer.toHexString(hashCode())
     *
     * Luu y: day KHONG phai dia chi that trong bo nho RAM (Java khong cho
     * phep truy cap dia chi RAM truc tiep nhu C/C++), ma la 1 ma hash duoc
     * viet duoi dang so he 16 (hex).
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        Department department = DataStorage.departments.get(0);

        String defaultStyleAddress = department.getClass().getName()
                + "@" + Integer.toHexString(System.identityHashCode(department));

        System.out.println("Dia chi (dang mac dinh cua Object) cua phong ban thu 1:");
        System.out.println(defaultStyleAddress);
        System.out.println();
    }

    /**
     * Question 4: Kiem tra phong ban thu 1 co ten la "Phong A" khong?
     * (optional)
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        Department department = DataStorage.departments.get(0);

        // So sanh CHUOI phai dung equals(), KHONG duoc dung "=="
        // ("==" so sanh dia chi object, khong so sanh noi dung chuoi)
        boolean isNamedPhongA = department.getDepartmentName().equals("Phong A");

        System.out.println("Ten phong ban thu 1: " + department.getDepartmentName());
        System.out.println("Co phai la \"Phong A\" khong? " + isNamedPhongA);
        System.out.println();
    }

    /**
     * Question 5: So sanh phong ban thu 1 va thu 2 xem co bang nhau khong
     * (bang nhau khi TEN bang nhau - da dinh nghia trong Department.equals())
     * *** BAT BUOC ***
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        Department department1 = DataStorage.departments.get(0);
        Department department2 = DataStorage.departments.get(1);

        // Goi .equals() da duoc override trong class Department
        boolean areEqual = department1.equals(department2);

        System.out.println("Phong ban 1: " + department1.getDepartmentName());
        System.out.println("Phong ban 2: " + department2.getDepartmentName());
        System.out.println("Hai phong ban co bang nhau khong? " + areEqual);
        System.out.println();
    }

    /**
     * Question 6: Khoi tao array 5 phong ban, in danh sach da sap xep
     * TANG DAN theo ten (A -> Z)
     * *** BAT BUOC ***
     *
     * Vi Department da implements Comparable<Department> (dinh nghia san
     * compareTo() theo ten), Arrays.sort() se tu dong biet cach sap xep
     * ma khong can viet them tham so nao khac.
     */
    public void question6() {
        System.out.println("===== Question 6 =====");

        Department[] departments = {
                new Department(1, "Sale"),
                new Department(2, "Marketing"),
                new Department(3, "Accounting"),
                new Department(4, "Waiting room"),
                new Department(5, "Boss of director")
        };

        System.out.println("Truoc khi sap xep:");
        printDepartmentNames(departments);

        // Vi Department Comparable<Department> nen chi can goi sort binh thuong
        Arrays.sort(departments);

        System.out.println("\nSau khi sap xep tang dan theo ten:");
        printDepartmentNames(departments);
        System.out.println();
    }

    /**
     * Question 7: Giong Question 6 nhung dung Comparator RIENG thay vi
     * dua vao Comparable co san - de minh hoa cach sap xep LINH HOAT hon
     * (vi du: sap xep theo ten nhung KHONG phan biet hoa/thuong)
     * *** BAT BUOC ***
     */
    public void question7() {
        System.out.println("===== Question 7 =====");

        Department[] departments = {
                new Department(1, "Sale"),
                new Department(2, "Marketing"),
                new Department(3, "Accounting"),
                new Department(4, "waiting room"),
                new Department(5, "Boss of director")
        };

        System.out.println("Truoc khi sap xep:");
        printDepartmentNames(departments);

        // Comparator.comparing(...) cho phep chi dinh RO tieu chi sap xep,
        // o day dung String.CASE_INSENSITIVE_ORDER de "waiting room" va
        // "Waiting Room" duoc coi la nhu nhau khi so sanh
        Arrays.sort(departments, (d1, d2) ->
                d1.getDepartmentName().compareToIgnoreCase(d2.getDepartmentName()));

        System.out.println("\nSau khi sap xep (khong phan biet hoa/thuong):");
        printDepartmentNames(departments);
        System.out.println();
    }

    /**
     * In ra danh sach ten cac phong ban trong array, moi ten 1 dong
     */
    private void printDepartmentNames(Department[] departments) {
        for (Department department : departments) {
            System.out.println(department.getDepartmentName());
        }
    }
}
