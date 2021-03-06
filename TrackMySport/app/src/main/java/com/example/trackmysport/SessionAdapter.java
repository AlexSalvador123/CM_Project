package com.example.trackmysport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder>{

    Context context;

    ArrayList<SessionData> list;

    SessionAdapter.ItemClickListener clickListener;

    public SessionAdapter(Context context, ArrayList<SessionData> list, SessionAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener  = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_session,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.MyViewHolder holder, int position) {

        SessionData session_data = list.get(position);
        holder.plan_name.setText(session_data.getPlan_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(session_data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView plan_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            plan_name = itemView.findViewById(R.id.plan_name);
        }
    }

    public interface ItemClickListener {

        public void onItemClick(SessionData data_session);
    }

}
