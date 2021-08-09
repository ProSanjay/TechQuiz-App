package com.example.techquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techquiz.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity {
  FirebaseAuth auth;
    ActivitySignInBinding binding;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       auth=FirebaseAuth.getInstance();
       if(auth.getCurrentUser()!=null)
       {
           startActivity(new Intent(Sign_in.this,MainActivity.class));
           finish();
       }
       dialog=new ProgressDialog(this);
       dialog.setMessage("Logging in...");
        binding.alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,pass;
                email=binding.emailAddreshsignin.getText().toString();
                pass=binding.passwordsignin.getText().toString();
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            startActivity(new Intent(Sign_in.this,MainActivity.class));
                            finish();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(Sign_in.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}