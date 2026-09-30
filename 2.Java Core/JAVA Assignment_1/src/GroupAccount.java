import java.time.LocalDate;

/**
 * GroupAccount - dai dien cho bang GroupAccount (bang trung gian) trong database
 * GroupID va AccountID duoc chuyen thanh Object
 */
public class GroupAccount {
    private Group group;
    private Account account;
    private LocalDate joinDate;

    public GroupAccount() {
    }

    public GroupAccount(Group group, Account account, LocalDate joinDate) {
        this.group = group;
        this.account = account;
        this.joinDate = joinDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "GroupAccount{" +
                "group=" + (group != null ? group.getGroupName() : "null") +
                ", account=" + (account != null ? account.getFullName() : "null") +
                ", joinDate=" + joinDate +
                '}';
    }
}
