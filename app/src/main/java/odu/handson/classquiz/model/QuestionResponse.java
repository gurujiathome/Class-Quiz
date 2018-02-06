package odu.handson.classquiz.model;

import java.util.List;

/**
 * Created by rgudipati on 10/7/2016.
 */
public class QuestionResponse {
    private int statusCode;
    DataQues data;

    public int getStatusCode() {
        return statusCode;
    }

    public DataQues getData() {
        return data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setData(DataQues data) {
        this.data = data;
    }

    public static class DataQues{
        List<QuestionDetails> answers;
        ReportData reportData;
        public void setReportData(ReportData reportData) {
            this.reportData = reportData;
        }

        public ReportData getReportData() {
            return reportData;
        }

        public List<QuestionDetails> getAnswers() {
            return answers;
        }

        public void setAnswers(List<QuestionDetails> answers) {
            this.answers = answers;
        }
    }
    public static class ReportData
    {
        List<Ranges> ranges;
        private int totalQuestions;
        private int correctAnswers;
        StudentRange studentRange;
        private String xLabel;
        private String yLabel;

        public void setRanges(List<Ranges> ranges) {
            this.ranges = ranges;
        }

        public void setTotalQuestions(int totalQuestions) {
            this.totalQuestions = totalQuestions;
        }

        public void setCorrectAnswers(int correctAnswers) {
            this.correctAnswers = correctAnswers;
        }

        public void setStudentRange(StudentRange studentRange) {
            this.studentRange = studentRange;
        }

        public void setxLabel(String xLabel) {
            this.xLabel = xLabel;
        }

        public void setyLabel(String yLabel) {
            this.yLabel = yLabel;
        }

        public List<Ranges> getRanges() {
            return ranges;
        }

        public int getTotalQuestions() {
            return totalQuestions;
        }

        public int getCorrectAnswers() {
            return correctAnswers;
        }

        public StudentRange getStudentRange() {
            return studentRange;
        }

        public String getxLabel() {
            return xLabel;
        }

        public String getyLabel() {
            return yLabel;
        }

        public static class StudentRange
        {
            private int rangeStart;
            private int rangeEnd;

            public void setRangeStart(int rangeStart) {
                this.rangeStart = rangeStart;
            }

            public void setRangeEnd(int rangeEnd) {
                this.rangeEnd = rangeEnd;
            }

            public int getRangeStart() {
                return rangeStart;
            }

            public int getRangeEnd() {
                return rangeEnd;
            }
        }
        public static class Ranges
        {
            private int rangeStart;
            private int rangeEnd;
            private int studentCount;

            public void setRangeStart(int rangeStart) {
                this.rangeStart = rangeStart;
            }

            public void setRangeEnd(int rangeEnd) {
                this.rangeEnd = rangeEnd;
            }

            public void setStudentCount(int studentCount) {
                this.studentCount = studentCount;
            }

            public int getRangeStart() {
                return rangeStart;
            }

            public int getRangeEnd() {
                return rangeEnd;
            }

            public int getStudentCount() {
                return studentCount;
            }
        }

    }
}
