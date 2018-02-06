package odu.handson.classquiz.model;

import java.util.List;

/**
 * Created by rgudipati on 10/5/2016.
 */
public class QuestionDetails {
    private String question;
    private int questionId;
    private String questionType;
    private List<Options> options;
    private String studentAnswerOptionId;
    private String correctOptionId;

    public String getStudentAnswerOptionId() {
        return studentAnswerOptionId;
    }

    public void setStudentAnswerOptionId(String studentAnswerOptionId) {
        this.studentAnswerOptionId = studentAnswerOptionId;
    }

    public void setCorrectOptionId(String correctOptionId) {
        this.correctOptionId = correctOptionId;
    }

    public String getCorrectOptionId() {
        return correctOptionId;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }
}
