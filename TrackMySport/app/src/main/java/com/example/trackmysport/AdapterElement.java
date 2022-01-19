package com.example.trackmysport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class AdapterElement extends RecyclerView.Adapter<AdapterElement.MyViewHolder> {

    Context context;
    ArrayList<ArrayList<String>> list;

    public AdapterElement(Context context, ArrayList<ArrayList<String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterElement.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_element,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterElement.MyViewHolder holder, int position) {

        ArrayList<String> data = list.get(position);
        holder.title.setText(data.get(0));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
