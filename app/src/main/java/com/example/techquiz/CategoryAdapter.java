package com.example.techquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
  Context context;
  ArrayList<com.example.techquiz.CategoryModel> categoryModel;

    public CategoryAdapter(Context context, ArrayList<com.example.techquiz.CategoryModel> categoryModel) {
        this.context = context;
        this.categoryModel = categoryModel;
    }
     @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample,null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Viewholder holder, int position) {
    final com.example.techquiz.CategoryModel Model=categoryModel.get(position);
       holder.textView.setText(Model.getText());
       Glide.with(context).load(Model.getImage())
               .into(holder.imageView);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, com.example.techquiz.Quiz.class);
               intent.putExtra("catId",Model.getCategoryid());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
  TextView textView;
  ImageView imageView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.category);
            imageView=itemView.findViewById(R.id.image1);

        }
    }
}
