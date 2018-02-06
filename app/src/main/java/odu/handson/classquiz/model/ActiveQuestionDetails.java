package odu.handson.classquiz.model;

import java.util.List;

/**
 * Created by rgudipati on 10/12/2016.
 */
public class ActiveQuestionDetails {
    private int statusCode;
    DataActive data;

    public int getStatusCode() {
        return statusCode;
    }

    public DataActive getData() {
        return data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setData(DataActive data) {
        this.data = data;
    }

    public static class DataActive
    {
        private int studentQuizId;
        private List<Question> questions;
        private int duration;
        private int quizId;

        public int getStudentQuizId() {
            return studentQuizId;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public int getDuration() {
            return duration;
        }

        public int getQuizId() {
            return quizId;
        }

        public void setStudentQuizId(int studentQuizId) {
            this.studentQuizId = studentQuizId;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public void setQuizId(int quizId) {
            this.quizId = quizId;
        }

        public static class Question
        {
            private int questionId;
            private String question;
            private String questionType;
            private List<Options> options;

            public int getQuestionId() {
                return questionId;
            }

            public String getQuestion() {
                return question;
            }

            public String getQuestionType() {
                return questionType;
            }

            public List<Options> getOptions() {
                return options;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public void setQuestionType(String questionType) {
                this.questionType = questionType;
            }

            public void setOptions(List<Options> options) {
                this.options = options;
            }
        }
    }
}
