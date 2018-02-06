package odu.handson.classquiz.model;

/**
 * Created by rgudipati on 10/12/2016.
 */
public class QuizResponse {
    private int optionId;
    private int questionId;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }
}
