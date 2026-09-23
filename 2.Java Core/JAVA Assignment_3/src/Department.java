import java.util.Objects;

/**
 * Department - dai dien cho bang Department trong database
 *
 * Implements Comparable<Department> de co the sap xep (Arrays.sort/Collections.sort)
 * theo mot thu tu mac dinh - o day la sap xep theo ten (alphabet).
 */
public class Department implements Comparable<Department> {
    private int departmentId;
    private String departmentName;

    public Department() {
    }

    public Department(int departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * So sanh (theo yeu cau bai): 2 Department bang nhau khi TEN bang nhau
     * (khong quan tam departmentId).
     *
     * Luu y quan trong: khi override equals(), NEN override luon hashCode()
     * theo cung tieu chi. Neu khong, Department se hoat dong sai khi dua vao
     * cac cau truc du lieu dua tren hash nhu HashSet, HashMap.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Department)) {
            return false;
        }
        Department other = (Department) obj;
        return Objects.equals(this.departmentName, other.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName);
    }

    /**
     * Quy dinh thu tu sap xep mac dinh: theo ten, tang dan (A -> Z)
     * compareTo() < 0  -> this dung truoc other
     * compareTo() == 0 -> bang nhau
     * compareTo() > 0  -> this dung sau other
     */
    @Override
    public int compareTo(Department other) {
        return this.departmentName.compareTo(other.departmentName);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
