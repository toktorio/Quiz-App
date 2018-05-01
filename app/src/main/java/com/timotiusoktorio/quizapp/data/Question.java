package com.timotiusoktorio.quizapp.data;

@SuppressWarnings("unused")
public class Question {

    private String mQuestion;
    private String mAnswer;
    private String[] mChoices;
    private int[] mCorrectChoices;

    /**
     * Public constructor for input type question (non-multiple choice).
     *
     * @param question - the question.
     * @param answer   - the correct answer.
     */
    Question(String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }

    /**
     * Public constructor for multiple choice question with one correct answer.
     *
     * @param question - the question.
     * @param answer   - the correct answer.
     * @param choices  - String array of choices.
     */
    Question(String question, String answer, String[] choices) {
        mQuestion = question;
        mAnswer = answer;
        mChoices = choices;
    }

    /**
     * Public constructor for multiple choice question with multiple correct answers.
     *
     * @param question       - the question.
     * @param choices        - String array of choices.
     * @param correctChoices - int array of correct choices.
     */
    Question(String question, String[] choices, int[] correctChoices) {
        mQuestion = question;
        mChoices = choices;
        mCorrectChoices = correctChoices;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String[] getChoices() {
        return mChoices;
    }

    public void setChoices(String[] choices) {
        mChoices = choices;
    }

    public int[] getCorrectChoices() {
        return mCorrectChoices;
    }

    public void setCorrectChoices(int[] correctChoices) {
        mCorrectChoices = correctChoices;
    }
}