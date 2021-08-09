package com.example.techquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techquiz.databinding.ActivityResultsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Results extends AppCompatActivity {
 ActivityResultsBinding binding;
 int COINS=10;
 FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int correctAnswer=getIntent().getIntExtra("correctAnswer",0);
        int totalquestion=getIntent().getIntExtra("totalquestion",0);
        long totalcoins=correctAnswer*COINS;
        binding.score.setText(String.format("%d/%d",correctAnswer,totalquestion));
        binding.earnedCoins.setText(String.valueOf(totalcoins));
        database=FirebaseFirestore.getInstance();
        database.collection("user").document(FirebaseAuth.getInstance().getUid()).
                update("coins", FieldValue.increment(totalcoins));
    }
}