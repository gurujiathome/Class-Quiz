package odu.handson.classquiz.model;

/**
 * Created by rgudipati on 10/4/2016.
 */
public class QuizDetails {
    private String name;
    private int quizId;
    private String requiresLocation;
    private String answersRecorded;
    private int studentQuizId;
    private String status;

    public String getName() {
        return name;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getRequiresLocation() {
        return requiresLocation;
    }

    public String getAnswersRecorded() {
        return answersRecorded;
    }

    public int getStudentQuizId() {
        return studentQuizId;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setRequiresLocation(String requiresLocation) {
        this.requiresLocation = requiresLocation;
    }

    public void setAnswersRecorded(String answersRecorded) {
        this.answersRecorded = answersRecorded;
    }

    public void setStudentQuizId(int studentQuizId) {
        this.studentQuizId = studentQuizId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
