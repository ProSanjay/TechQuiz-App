package com.example.techquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techquiz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity {
ActivityQuizBinding binding;
    Question question;
    ArrayList<Question> questions;
    int index=0;
    public CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions=new ArrayList<>();
        database=FirebaseFirestore.getInstance();
       final  String catId=getIntent().getStringExtra("catId");
        Random random=new Random();
        final int rand=random.nextInt(12);
        database.collection("categories").document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index").limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               if(queryDocumentSnapshots.getDocuments().size()<5){
                   database.collection("categories").document(catId)
                           .collection("questions")
                           .whereLessThanOrEqualTo("index",rand)
                           .orderBy("index")
                           .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                       @Override
                       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                           for(DocumentSnapshot snapshot:queryDocumentSnapshots){
                               com.example.techquiz.Question question=snapshot.toObject(com.example.techquiz.Question.class);
                               questions.add(question);
                           }
                           setNextQuestion();
                       }
                   });
               }
               else{
                   for(DocumentSnapshot snapshot:queryDocumentSnapshots){
                       com.example.techquiz.Question question=snapshot.toObject(com.example.techquiz.Question.class);
                       questions.add(question);
                   }
                   setNextQuestion();
               }
            }
        });


        resetTime();

    }
    void setNextQuestion(){
        if(timer!=null){
            timer.cancel();
        }
        timer.start();
        if(index<questions.size()){
            binding.questioncount.setText(String.format("%d/%d",(index+1),questions.size()));
             question=questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());

        }
    }
    public void resetTime(){
        timer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }
    public void showanswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.correctans));
        }
         else if(question.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getResources().getDrawable(R.drawable.correctans));
        }
        else if(question.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.correctans));
        }
        else if(question.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.correctans));
        }
    }
    public void checkAnswer(TextView textView){
        String text=textView.getText().toString();
        if(text.equals(question.getAnswer())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.correctans));
        }else {
            showanswer();
            textView.setBackground(getResources().getDrawable(R.drawable.wrong));

        }
    }
    public void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option));
    }

    public void onClick(View view){
        switch (view.getId())
        {       case R.id.option1:
               case R.id.option2:
                case R.id.option3:
                case R.id.option4:
                    if(timer!=null){
                        timer.cancel();
                    }
                    TextView selected=(TextView) view;
                    checkAnswer(selected);
                    break;
               case R.id.next: {
                   reset();
                index++;
                if (index < questions.size()){
                    setNextQuestion();
            }else
                {
                    Intent intent=new Intent(Quiz.this,Results.class);
                    intent.putExtra("correctAnswer",correctAnswer);
                    intent.putExtra("totalquestion",questions.size());
                    startActivity(intent);


                }
                break;
            }
        }
    }
}