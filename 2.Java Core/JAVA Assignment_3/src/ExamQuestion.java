/**
 * ExamQuestion - dai dien cho bang ExamQuestion (bang trung gian) trong database
 * ExamID va QuestionID duoc chuyen thanh Object
 */
public class ExamQuestion {
    private Exam exam;
    private Question question;

    public ExamQuestion() {
    }

    public ExamQuestion(Exam exam, Question question) {
        this.exam = exam;
        this.question = question;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "ExamQuestion{" +
                "exam=" + (exam != null ? exam.getTitle() : "null") +
                ", question=" + (question != null ? question.getQuestionId() : "null") +
                '}';
    }
}
