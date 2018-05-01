package com.timotiusoktorio.quizapp.data;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    /**
     * Helper method to generate the list of questions.
     * @return - an array list of questions.
     */
    public static List<Question> generateQuestions() {
        Question question_1 = new Question("What represents a single user interface screen?", "activity");
        Question question_2 = new Question("What does API stands for?", "Application Programming Interface");
        Question question_3 = new Question("What is Android?",
                "A stack of software's for mobility",
                new String[] {"A stack of software's for mobility", "Google mobile device name", "Virtual machine", "None of the above"});
        Question question_4 = new Question("What year was Android founded?",
                "2003",
                new String[] {"2003", "2008", "2001", "2005"});
        Question question_5 = new Question("What kind of layout that allows you to specify exact locations (x/y coordinates) of its children?",
                "RelativeLayout",
                new String[] {"AbsoluteLayout", "RelativeLayout", "LinearLayout", "FrameLayout"});
        Question question_6 = new Question("All layout classes are direct subclass of...",
                "android.view.ViewGroup",
                new String[] {"android.widget.AbsoluteLayout", "java.lang.Object", "android.view.ViewGroup", "android.view.View"});
        Question question_7 = new Question("Select the unit that is not recommended for specifying dimensions",
                "Pixels",
                new String[] {"Points", "Scale Independent Pixels (sp)", "Density Independent Pixels (dp)", "Pixels"});
        Question question_8 = new Question("How to kill an activity in Android?",
                new String[] {"finish()", "finishActivity(int requestCode)", "kill()", "None of the above"},
                new int[] {0, 1});
        Question question_9 = new Question("Which features are considered while creating android application?",
                new String[] {"Screen size", "Input configuration", "Platform version", "Device features"},
                new int[] {0, 1, 2, 3});
        Question question_10 = new Question("How to get current location in Android?",
                new String[] {"Using GPRS", "Using location provider", "Using SQlite", "Using network servers"},
                new int[] {0, 1});

        List<Question> questions = new ArrayList<>();
        questions.add(question_1);
        questions.add(question_2);
        questions.add(question_3);
        questions.add(question_4);
        questions.add(question_5);
        questions.add(question_6);
        questions.add(question_7);
        questions.add(question_8);
        questions.add(question_9);
        questions.add(question_10);
        return questions;
    }
}