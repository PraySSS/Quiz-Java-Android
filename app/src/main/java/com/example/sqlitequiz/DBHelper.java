package com.example.sqlitequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitequiz.QuizContract.QuestionsTable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    }

    private void fillQuestionTable() {
        QuestionModel q1 = new QuestionModel("A is correct", "A", "B", "C", 1);
        addQuestion(q1);
        QuestionModel q2 = new QuestionModel("B is correct", "A", "B", "C", 2);
        addQuestion(q2);
        QuestionModel q3 = new QuestionModel("C is correct", "A", "B", "C", 3);
        addQuestion(q3);
        QuestionModel q4 = new QuestionModel("A is correct again", "A", "B", "C", 1);
        addQuestion(q4);
        QuestionModel q5 = new QuestionModel("B is correct again", "A", "B", "C", 2);
        addQuestion(q5);
    }

    private void addQuestion(QuestionModel question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<QuestionModel> getAllQuestions() {
        List<QuestionModel> questionList = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null)) {

            if (cursor.moveToFirst()) {
                do {
                    QuestionModel question = new QuestionModel();
                    question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(cursor.getString(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                    question.setAnswerNr(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                    questionList.add(question);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionList;
    }


}
