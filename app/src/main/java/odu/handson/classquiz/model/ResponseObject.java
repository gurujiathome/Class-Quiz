package odu.handson.classquiz.model;

import java.util.List;

/**
 * Created by rgudipati on 10/6/2016.
 */
public class ResponseObject {
    private int statusCode;
    Data data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;

    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public class Data {
        private List<QuizDetails> quizzes;

        public void setQuizzes(List<QuizDetails> quizzes) {
            this.quizzes = quizzes;
        }

        public List<QuizDetails> getQuizzes() {
            return quizzes;
        }
    }
}


