import java.time.LocalDateTime;

/**
 * Exam - dai dien cho bang Exam trong database
 * CategoryID, CreatorID duoc chuyen thanh Object
 */
public class Exam {
    private int examId;
    private String code;
    private String title;
    private CategoryQuestion category;
    private int duration;
    private Account creator;
    private LocalDateTime createDate;

    public Exam() {
    }

    public Exam(int examId, String code, String title, CategoryQuestion category,
                int duration, Account creator, LocalDateTime createDate) {
        this.examId = examId;
        this.code = code;
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryQuestion getCategory() {
        return category;
    }

    public void setCategory(CategoryQuestion category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                ", duration=" + duration +
                ", creator=" + (creator != null ? creator.getFullName() : "null") +
                ", createDate=" + createDate +
                '}';
    }
}
