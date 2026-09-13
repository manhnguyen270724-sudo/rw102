import java.time.LocalDate;

/**
 * Group - dai dien cho bang Group trong database
 * CreatorID duoc chuyen thanh Object Account
 */
public class Group {
    private int groupId;
    private String groupName;
    private Account creator;
    private LocalDate createDate;

    public Group() {
    }

    public Group(int groupId, String groupName, Account creator, LocalDate createDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", creator=" + (creator != null ? creator.getFullName() : "null") +
                ", createDate=" + createDate +
                '}';
    }
}
