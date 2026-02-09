package com.example.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class my_Adapter extends RecyclerView.Adapter<my_Adapter.MyViewHolder>{
    Context context;

    ArrayList<postclass> list;

    public my_Adapter(Context context, ArrayList<postclass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        postclass user = list.get(position);
        holder.p.setText(user.getpost());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView p;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            p = itemView.findViewById(R.id.rep);


        }
}}
