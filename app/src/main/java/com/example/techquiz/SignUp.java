package com.example.techquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techquiz.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
  ActivitySignUpBinding binding;
  FirebaseAuth auth;
  FirebaseFirestore database;
  ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("We are creating new account....");
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,password,name,refer;
                email=binding.emailAddresh.getText().toString();
                password=binding.password.getText().toString();
                name=binding.fullname.getText().toString();
                refer=binding.refer.getText().toString();
                User user=new User(name,email,password,refer);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       String uid=task.getResult().getUser().getUid();
                       database.collection("user").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   dialog.dismiss();
                                   startActivity(new Intent(SignUp.this,MainActivity.class));
                                   finish();
                               }else{
                                   Toast.makeText(SignUp.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

                   }else{
                       dialog.dismiss();
                       Toast.makeText(SignUp.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }
        });
        binding.alreadyaccountSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,Sign_in.class));
                finish();
            }
        });
    }
}