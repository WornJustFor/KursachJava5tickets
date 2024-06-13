package com.example.pdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdd.models.Data;
import com.example.pdd.models.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private LinearLayout buttonAnswers;
    private TextView titleQuestion, currentQuestion;
    private LayoutInflater layoutInflater;
    private Button nextQuestionBtn, correctButtonAnswer;
    private int curQuestion = 0, correctAnswers = 0;
    private ImageView imageQuestion;
    private Intent intent;
    private boolean isAnswered = false;
    private ArrayList<Question> questions;
    private Gson gson;
    private GsonBuilder builder = new GsonBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent extrasIntent = getIntent();
        String category = extrasIntent.getStringExtra("category");
        String ticketNum = extrasIntent.getStringExtra("ticket_num");
        try {
            InputStream inputStream = getAssets().open("data/questions/" + category + "/tickets/Билет " + ticketNum + ".json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            gson = builder.create();
            questions = gson.fromJson(inputStreamReader, Data.class).getData();
            inputStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        if (Objects.equals(category, "ABM")) {
//            questions = new QuestionDB().getAllABMQuestion();
//        } else {
//            questions = new QuestionDB().getAllCDQuestions();
//        }


//        linearLayoutQuestions = findViewById(R.id.buttonQuestionsLayout);
        buttonAnswers = findViewById(R.id.buttonAnswersLayout);
        titleQuestion = findViewById(R.id.questionTitle);
        nextQuestionBtn = findViewById(R.id.nextQuestion);
        imageQuestion = findViewById(R.id.questionImage);
        currentQuestion = findViewById(R.id.currentQuestion);

//        linearLayoutQuestions.removeAllViews();
        buttonAnswers.removeAllViews();
        currentQuestion.setText("" + (curQuestion + 1) + "/" + questions.size());


        nextQuestionBtn.setOnClickListener(view -> {
            curQuestion++;
            if (curQuestion == questions.size()) {
                intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", correctAnswers);
                intent.putExtra("questions", questions.size());
                startActivity(intent);
                finish();
            } else {
                currentQuestion.setText("" + (curQuestion + 1) + "/" + questions.size());
                updateQuestion(questions.get(curQuestion));
            }
        });

        titleQuestion.setText(questions.get(curQuestion).getQuestion());

        if (loadImageByPath(questions.get(curQuestion).getImage()) != null){
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageDrawable(loadImageByPath(questions.get(curQuestion).getImage()));
        }


//        for (int i = 0; i < questions.size(); i++) {
//            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//            View questionView = layoutInflater.inflate(R.layout.button_questions, null);
//            Button btn = questionView.findViewById(R.id.buttonQuestion);
//            btn.setText("" + (i + 1));
//            btn.setId(1300 + i);
//            btn.setOnClickListener(view -> {
//                curQuestion = (int)view.getId() - 1300;
//                updateQuestion(questions.get(curQuestion));
//            });
//            linearLayoutQuestions.addView(questionView);
//        }

        for (int i = 0; i < questions.get(curQuestion).getAnswers().size(); i++) {
            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View answerView = layoutInflater.inflate(R.layout.answer_button, null);
            Button btn = answerView.findViewById(R.id.buttonAnswer);
            btn.setId(1500 + i);

            if (questions.get(curQuestion).getAnswers().get(i).isIs_correct()) {
                correctButtonAnswer = btn;
            }

            btn.setText(questions.get(curQuestion).getAnswers().get(i).getAnswer_text());
            int finalI = i;
            btn.setOnClickListener(view -> {
                if (questions.get(curQuestion).getAnswers().get(finalI).isIs_correct() && !isAnswered) {
                    correctAnswers++;
                    isAnswered = true;
                    btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5cb85c")));
                    nextQuestionBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#428bca")));
                } else if (!isAnswered){
                    isAnswered = true;
                    btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d9534f")));
                    correctButtonAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5cb85c")));
                }
                nextQuestionBtn.setVisibility(View.VISIBLE);
            });
            buttonAnswers.addView(answerView);
        }
    }

    private Drawable loadImageByPath(String url) {
        InputStream imageStream;
        Drawable dImage;
        try {
            imageStream = getAssets().open(url);
            dImage = Drawable.createFromStream(imageStream, "image_for_pdd");
            imageStream.close();
        } catch (IOException e) {
            return null;
        }

        return dImage;
    }

    private void updateQuestion(Question queObj) {
        isAnswered = false;
        imageQuestion.setVisibility(View.GONE);
        nextQuestionBtn.setVisibility(View.GONE);

        buttonAnswers.removeAllViews();
        Drawable image = loadImageByPath(queObj.getImage());
        if (image != null) {
            imageQuestion.setVisibility(View.VISIBLE);
            imageQuestion.setImageDrawable(image);
        }

        titleQuestion.setText(questions.get(curQuestion).getQuestion());
        for (int i = 0; i < questions.get(curQuestion).getAnswers().size(); i++) {
            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View answerView = layoutInflater.inflate(R.layout.answer_button, null);
            Button btn = answerView.findViewById(R.id.buttonAnswer);
            btn.setId(1500 + i);
            btn.setText(questions.get(curQuestion).getAnswers().get(i).getAnswer_text());
            int finalI = i;

            if (questions.get(curQuestion).getAnswers().get(i).isIs_correct()) {
                correctButtonAnswer = btn;
            }

            btn.setOnClickListener(view -> {
                if (questions.get(curQuestion).getAnswers().get(finalI).isIs_correct() && !isAnswered) {
                    correctAnswers++;
                    isAnswered = true;
                    btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5cb85c")));
                    nextQuestionBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#428bca")));
                } else if (!isAnswered){
                    isAnswered = true;
                    btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#d9534f")));
                    correctButtonAnswer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5cb85c")));

                }
                nextQuestionBtn.setVisibility(View.VISIBLE);
            });
            buttonAnswers.addView(answerView);
        }
    }
}