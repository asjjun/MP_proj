package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SchoolBoardFragment extends Fragment {
    public View view;

    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton school_writePostBtn;
    ArrayList<SchoolWriteInfo> sc_arrayList;
    FirebaseDatabase database;
    SchoolPostAdapter schoolPostAdapter;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schoolboard,container,false);

        user= FirebaseAuth.getInstance().getCurrentUser();
        swipeRefreshLayout = view.findViewById(R.id.school_refresh);
        school_writePostBtn = (FloatingActionButton) view.findViewById(R.id.school_writePostBtn);
        database = FirebaseDatabase.getInstance();
        sc_arrayList = new ArrayList<SchoolWriteInfo>();
        RecyclerView recyclerView = view.findViewById(R.id.school_rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        schoolPostAdapter = new SchoolPostAdapter(sc_arrayList);
        recyclerView.setAdapter(schoolPostAdapter);
        schoolPostAdapter.notifyDataSetChanged();

        school_writePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(SchoolWritePostActivity.class);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postLoad();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        postLoad();
    }

    private void postLoad(){
        DatabaseReference postRef = database.getReference("SchoolPosts");
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sc_arrayList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    SchoolWriteInfo write = snap.getValue(SchoolWriteInfo.class);
                    sc_arrayList.add(write);
                }
                schoolPostAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException()));
            }
        });
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(view.getContext(),c);
        startActivity(intent);
    }
}