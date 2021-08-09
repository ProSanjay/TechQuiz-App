package com.example.techquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.techquiz.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new Home());
        transaction.commit();
      binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
          @Override
          public boolean onItemSelect(int i) {
              FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
              switch(i){
                  case 0:
                  {
                      transaction.replace(R.id.content,new Home());
                      transaction.commit();
                      break;
                  }
                  case 1:
                  {
                      transaction.replace(R.id.content,new com.example.techquiz.leaderboard());
                      transaction.commit();
                      break;
                  }
                  case 2:
                  {
                      transaction.replace(R.id.content,new com.example.techquiz.Wallet());
                      transaction.commit();
                      break;
                  }
                  case 3:
                  {
                      transaction.replace(R.id.content,new com.example.techquiz.profile());
                      transaction.commit();
                      break;
                  }
              }
              return false;
          }
      });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
      startActivity(new Intent(MainActivity.this,SignUp.class));
        }
        return super.onOptionsItemSelected(item);
    }
}