package com.example.techquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.techquiz.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Home extends Fragment {



    public Home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentHomeBinding binding;

    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        database= FirebaseFirestore.getInstance();
        ArrayList<com.example.techquiz.CategoryModel> category=new ArrayList<>();
        CategoryAdapter categoryAdapter=new CategoryAdapter(getContext(),category);//snapshot listner is use for update UI in real time
        database.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                category.clear();
                for(DocumentSnapshot snapshot:value.getDocuments())
                {
                    com.example.techquiz.CategoryModel model=snapshot.toObject(com.example.techquiz.CategoryModel.class);//it create object of category model refer to firestore database collection docs.
                    model.setCategoryid(snapshot.getId());
                    category.add(model);
                }
                categoryAdapter.notifyDataSetChanged();
            }
        });
        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.categoryList.setAdapter(categoryAdapter);
        binding.spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), com.example.techquiz.SpinWheel.class));

            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}