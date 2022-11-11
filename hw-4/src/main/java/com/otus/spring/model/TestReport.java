package com.otus.spring.model;

import java.util.List;

public class TestReport {

    private final Credentials credentials;
    private final TestResults results;

    public TestReport(Credentials credentials) {
        this.credentials = credentials;
        this.results = new TestResults();
    }

    public void addResult(Question question, List<Answer> answers) {
        results.totalNumberOfQuestions++;
        if (question.isCorrect(answers)) {
            results.numberOfCorrectAnswers++;
        }
    }

    public TestResults getResults() {
        return results;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public static class Credentials {
        private final String username;

        public Credentials(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

    }

    public static class TestResults {
        private int totalNumberOfQuestions = 0;
        private int numberOfCorrectAnswers = 0;

        public int getTotalNumberOfQuestions() {
            return totalNumberOfQuestions;
        }

        public int getNumberOfCorrectAnswers() {
            return numberOfCorrectAnswers;
        }
    }

}
