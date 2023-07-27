package com.example.sqlitequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView txtQuestion, txtScore, txtQuestionCount, txtCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnConfirm;
    private List<QuestionModel> questionList;
    private int questionCountTotal;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txt_question);
        txtScore = findViewById(R.id.txt_score);
        txtCountDown = findViewById(R.id.txt_quiz_count);
        txtCountDown = findViewById(R.id.txt_countdown);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_btn1);
        rb2 = findViewById(R.id.radio_btn2);
        rb3 = findViewById(R.id.radio_btn3);

        btnConfirm = findViewById(R.id.btn_confirm);

        DBHelper dbHelper = new DBHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();

    }
}