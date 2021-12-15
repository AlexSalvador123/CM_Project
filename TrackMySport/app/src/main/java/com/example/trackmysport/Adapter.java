package com.example.trackmysport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ItemClickListener clickListener;
    ArrayList<TeamData> list;

    public Adapter(Context context, ArrayList<TeamData> list, ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener  = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_team,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {

        TeamData tdata = list.get(position);
        holder.teamname.setText(tdata.getTeamname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(tdata);
            }
        });


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView teamname;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            teamname = itemView.findViewById(R.id.teamname);
        }
    }
    public interface ItemClickListener {

        public void onItemClick(TeamData dataTeam);
    }
}
