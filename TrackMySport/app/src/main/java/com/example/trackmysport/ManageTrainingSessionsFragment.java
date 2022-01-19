package com.example.trackmysport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageTrainingSessionsFragment extends Fragment implements SessionAdapter.ItemClickListener{

    View sessions_view;
    RecyclerView sessions_recyclerView;
    DatabaseReference sessions_database;
    SessionAdapter sessions_adapter;
    ArrayList<SessionData> sessions_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sessions_view = inflater.inflate(R.layout.fragment_manage_training_sessions, container, false);
        // Inflate the layout for this fragment
        sessions_recyclerView  = sessions_view.findViewById(R.id.plans_list);
        sessions_database = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Training Plans");
        sessions_recyclerView.setHasFixedSize(true);
        sessions_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sessions_list = new ArrayList<>();
        sessions_adapter = new SessionAdapter(getContext(), sessions_list,this);
        sessions_recyclerView.setAdapter(sessions_adapter);

        sessions_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    SessionData data = dataSnapshot.getValue(SessionData.class);
                    sessions_list.add(data);

                }
                sessions_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return sessions_view;
    }

    @Override
    public void onItemClick(SessionData data_session) {
        Fragment fragment = DetailSessionFragment.newInstance(data_session.getPlan_name(), data_session.getPlan_difficulty(), data_session.getPlan_exercise1(),
                data_session.getPlan_time1(), data_session.getPlan_reps1(), data_session.getPlan_exercise2(),data_session.getPlan_time2(),
                data_session.getPlan_reps2(), data_session.getTeam());

        TrainingSession.current_plan = data_session.getPlan_name();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_sessions));
        transaction.add(R.id.fragment_container_sessions, fragment);
        transaction.addToBackStack(null);
        //transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        TrainingSession.frag = 2;
    }
}