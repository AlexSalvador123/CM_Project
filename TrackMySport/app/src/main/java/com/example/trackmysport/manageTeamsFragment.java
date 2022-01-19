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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class manageTeamsFragment extends Fragment implements Adapter.ItemClickListener{

    private FirebaseAuth mAuth;
    View view;
    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter adapter;
    ArrayList<TeamData> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_teams, container, false);
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        recyclerView  = view.findViewById(R.id.teamList);
        database = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        adapter = new Adapter(getContext(), list,this);
        recyclerView.setAdapter(adapter);

        database.child("Users").child(mAuth.getCurrentUser().getUid()).child("Teams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HashMap s = (HashMap) ds.getValue();
                    database.child("Teams").child(s.get("name").toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println(snapshot);
                            TeamData data = snapshot.getValue(TeamData.class);
                            list.add(data);
                            /*for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                                TeamData data = dataSnapshot.getValue(TeamData.class);
                                list.add(data);

                            }*/
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }

    @Override
    public void onItemClick(TeamData dataTeam) {
        Fragment fragment = DetailTeamFragment.newInstance(dataTeam.getTeamname());

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        //transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}