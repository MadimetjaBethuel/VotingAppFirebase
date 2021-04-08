package com.vouta.plebiscite.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vouta.plebiscite.Adapters.RegisterAdapter;
import com.vouta.plebiscite.activities.AddCandidadateForm;
import com.vouta.plebiscite.R;
import com.vouta.plebiscite.getterandsetters.Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RegisterCandidateFragment extends Fragment {
    View v;
    private FloatingActionButton floatingActionButton;

    private List<Model> lists = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseReference Candidateref;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register_organization, container, false);

        floatingActionButton = v.findViewById(R.id.fab);
        recyclerView = v.findViewById(R.id.my_recycler);

        Candidateref = FirebaseDatabase.getInstance()
                .getReference()
                .child("Candidates");

       RegisterAdapter registerAdapter = new RegisterAdapter(getContext(),lists);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(registerAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCandidadateForm.class);
                startActivity(intent);
            }
        });

        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListOfCandidates();
    }
    public void getListOfCandidates(){
        Candidateref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    lists.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                        Model list = dataSnapshot.getValue(Model.class);
                        lists.add(list);
                    }
                }
                RegisterAdapter adapter = new RegisterAdapter(getContext(),lists);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
