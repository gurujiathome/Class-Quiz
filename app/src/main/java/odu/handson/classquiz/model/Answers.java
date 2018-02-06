package odu.handson.classquiz.model;

import java.util.List;

/**
 * Created by rgudipati on 10/12/2016.
 */
public class Answers {
    private int studentQuizId;
    private List<UserAnswers> answers;


    public int getStudentQuizId() {
        return studentQuizId;
    }

    public void setStudentQuizId(int studentQuizId) {
        this.studentQuizId = studentQuizId;
    }

    public List<UserAnswers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswers> answers) {
        this.answers = answers;
    }

    public static class UserAnswers
    {
        private int optionId;
        private int questionId;

        public int getOptionId() {
            return optionId;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }
    }
}
