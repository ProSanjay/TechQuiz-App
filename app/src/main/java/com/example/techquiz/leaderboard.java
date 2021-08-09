package com.example.techquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.techquiz.databinding.FragmentLeaderboardBinding;
import com.example.techquiz.databinding.FragmentLeaderboardBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class leaderboard extends Fragment {


    public leaderboard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
   FragmentLeaderboardBinding binding;
    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLeaderboardBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();
      final  ArrayList<User> users=new ArrayList<>();
        com.example.techquiz.LeaderBoardAdapter adapter=new com.example.techquiz.LeaderBoardAdapter(getContext(),users);
        binding.leaderboard.setAdapter(adapter);
        binding.leaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        database.collection("user").orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot:queryDocumentSnapshots)
                {
                    User user=snapshot.toObject(User.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}