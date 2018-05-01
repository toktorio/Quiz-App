package com.timotiusoktorio.quizapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.timotiusoktorio.quizapp.R;
import com.timotiusoktorio.quizapp.data.QuestionBank;
import com.timotiusoktorio.quizapp.data.Question;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {

    private List<Question> mQuestions;
    private int mQuestionNumber;
    private int mScore;

    @BindView(R.id.question_text_view) TextView mQuestionTextView;
    @BindView(R.id.answer_edit_text) EditText mAnswerEditText;
    @BindView(R.id.radio_group) RadioGroup mRadioGroup;
    @BindView(R.id.radio_button_A) RadioButton mRadioButton_A;
    @BindView(R.id.radio_button_B) RadioButton mRadioButton_B;
    @BindView(R.id.radio_button_C) RadioButton mRadioButton_C;
    @BindView(R.id.radio_button_D) RadioButton mRadioButton_D;
    @BindView(R.id.check_box_container) LinearLayout mCheckboxContainer;
    @BindView(R.id.check_box_A) CheckBox mCheckBox_A;
    @BindView(R.id.check_box_B) CheckBox mCheckBox_B;
    @BindView(R.id.check_box_C) CheckBox mCheckBox_C;
    @BindView(R.id.check_box_D) CheckBox mCheckBox_D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Too lazy to write findViewById code, so decided to use ButterKnife library.
        ButterKnife.bind(this);

        // Initialize all global variables.
        mQuestions = QuestionBank.generateQuestions();
        mQuestionNumber = 0;
        mScore = 0;

        // Prepare the first question.
        prepareQuestion();
    }

    /**
     * Method that is invoked when the user presses the Next button.
     *
     * @param view - the Next Button.
     */
    public void checkAnswer(View view) {
        Question question = mQuestions.get(mQuestionNumber);
        if (question.getChoices() == null) {
            // This is a non-multiple choice question.
            checkInputAnswer(question);
        } else {
            if (question.getCorrectChoices() == null) {
                // This is a multiple choice question with one correct answer.
                checkSingleAnswer(question);
            } else {
                // This is a multiple choice question with multiple correct answers.
                checkMultipleAnswers(question);
            }
        }
    }

    /**
     * Method to check the input type question (non-multiple choice).
     *
     * @param question - the current Question.
     */
    private void checkInputAnswer(Question question) {
        // Get the user input from the EditText. Also trim the text to remove any whitespaces.
        String userInput = mAnswerEditText.getText().toString().trim();

        // If the user does not input anything, do nothing.
        if (userInput.isEmpty()) {
            return;
        }

        // The answer is correct if the input is equal to the answer.
        if (userInput.equalsIgnoreCase(question.getAnswer())) {
            mScore++;
        }

        // Proceed to next question.
        proceedToNextQuestion();
    }

    /**
     * Method to check the multiple choice question with one correct answer.
     *
     * @param question - the current Question.
     */
    private void checkSingleAnswer(Question question) {
        // Get the checked button ID from the RadioGroup.
        int checkedButtonId = mRadioGroup.getCheckedRadioButtonId();

        // The getCheckedRadioButtonId() method returns -1 if no RadioButton is checked. Do nothing.
        if (checkedButtonId == -1) {
            return;
        }

        // Find the checked RadioButton using the checked button ID.
        RadioButton checkedRadioButton = findViewById(checkedButtonId);
        if (checkedRadioButton != null) {
            // The choice is right if the checked radio button text is equal to the answer.
            if (checkedRadioButton.getText().toString().equals(question.getAnswer())) {
                mScore++;
            }
            proceedToNextQuestion();
        }
    }

    /**
     * Method to check the multiple choice question with multiple correct answers.
     *
     * @param question - the current Question.
     */
    private void checkMultipleAnswers(Question question) {
        int totalCorrectChoices = question.getCorrectChoices().length;
        int correctChoicesChecked = 0; // Variable to keep track the number of correct choices.
        int incorrectChoicesChecked = 0; // Variable to keep track the number of incorrect choices.

        // Since CheckBox has no ViewGroup that indicates which CheckBox is checked like RadioGroup,
        // all choices CheckBox must be checked one by one. The user must only check the correct
        // choices to get the correct result. So if the user check more than the total correct
        // choices or doesn't check all the correct choices, the result will be incorrect.

        // If the CheckBox is checked and it contains a tag object, it is correct.
        // Else if the CheckBox is checked but it does not contains a tag object, it is incorrect.
        if (mCheckBox_A.isChecked() && mCheckBox_A.getTag() != null) correctChoicesChecked++;
        else if (mCheckBox_A.isChecked() && mCheckBox_A.getTag() == null) incorrectChoicesChecked++;

        if (mCheckBox_B.isChecked() && mCheckBox_B.getTag() != null) correctChoicesChecked++;
        else if (mCheckBox_B.isChecked() && mCheckBox_B.getTag() == null) incorrectChoicesChecked++;

        if (mCheckBox_C.isChecked() && mCheckBox_C.getTag() != null) correctChoicesChecked++;
        else if (mCheckBox_C.isChecked() && mCheckBox_C.getTag() == null) incorrectChoicesChecked++;

        if (mCheckBox_D.isChecked() && mCheckBox_D.getTag() != null) correctChoicesChecked++;
        else if (mCheckBox_D.isChecked() && mCheckBox_D.getTag() == null) incorrectChoicesChecked++;

        // The user gets a correct result if the total correct choices checked is equal to the
        // total correct choices and the total incorrect choices checked is 0.
        if (correctChoicesChecked == totalCorrectChoices && incorrectChoicesChecked == 0) {
            mScore++;
        }
        proceedToNextQuestion();
    }

    private void proceedToNextQuestion() {
        // Proceed to the next question only if there is a next question.
        if (mQuestionNumber < (mQuestions.size() - 1)) {
            mQuestionNumber++;
            resetQuestion();
            prepareQuestion();
        } else {
            // This is the last questions. Show the quiz result.
            String toastMessage = getString(R.string.result_toast_message, mScore, mQuestions.size());
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void resetQuestion() {
        // Hide the soft keyboard.
        if (mAnswerEditText.getVisibility() == View.VISIBLE) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }

        // Clear the EditText input.
        mAnswerEditText.setText("");

        // Clear all RadioButton selection.
        mRadioGroup.clearCheck();

        // Clear all CheckBox selection and reset the tag to null.
        mCheckBox_A.setChecked(false);
        mCheckBox_B.setChecked(false);
        mCheckBox_C.setChecked(false);
        mCheckBox_D.setChecked(false);
        mCheckBox_A.setTag(null);
        mCheckBox_B.setTag(null);
        mCheckBox_C.setTag(null);
        mCheckBox_D.setTag(null);

        // Hide all the answers UI to prevent duplicate UI elements.
        mAnswerEditText.setVisibility(View.GONE);
        mRadioGroup.setVisibility(View.GONE);
        mCheckboxContainer.setVisibility(View.GONE);
    }

    private void prepareQuestion() {
        // Get the current question from the list using the question number variable.
        Question question = mQuestions.get(mQuestionNumber);

        // Set the question to the question TextView.
        mQuestionTextView.setText(question.getQuestion());

        // Determine the question type to show the correct UI (EditText, RadioButton, or CheckBox).
        if (question.getChoices() == null) {
            // This is a non-multiple choice question. Show the EditText.
            mAnswerEditText.setVisibility(View.VISIBLE);

            // Show the soft keyboard.
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        } else {
            if (question.getCorrectChoices() == null) {
                // This is a multiple choice question with one correct answer.
                // First, set the radio buttons text with the choices.
                mRadioButton_A.setText(question.getChoices()[0]);
                mRadioButton_B.setText(question.getChoices()[1]);
                mRadioButton_C.setText(question.getChoices()[2]);
                mRadioButton_D.setText(question.getChoices()[3]);

                // Next, show the radio buttons.
                mRadioGroup.setVisibility(View.VISIBLE);
            } else {
                // This is a multiple choice question with multiple correct answers.
                // First, set the checkboxes text with the choices.
                mCheckBox_A.setText(question.getChoices()[0]);
                mCheckBox_B.setText(question.getChoices()[1]);
                mCheckBox_C.setText(question.getChoices()[2]);
                mCheckBox_D.setText(question.getChoices()[3]);

                // Next, assign an indicator to the checkboxes that contains the correct choices.
                // One way is to use the tag property of CheckBox to hold the indicator.
                for (int correctChoice : question.getCorrectChoices()) {
                    mCheckboxContainer.getChildAt(correctChoice).setTag("correct_choice");
                }

                // Finally, show the checkboxes.
                mCheckboxContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}