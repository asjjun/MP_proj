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

public class DeptBoardFragment extends Fragment {
    public View view;

    private SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton writePostBtn;
    ArrayList<WriteInfo> arrayList;
    FirebaseDatabase database;
    PostAdapter postAdapter;

    FirebaseUser user;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deptboard,container,false);

        user= FirebaseAuth.getInstance().getCurrentUser();
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        writePostBtn = (FloatingActionButton) view.findViewById(R.id.writePostBtn);
        database = FirebaseDatabase.getInstance();
        arrayList = new ArrayList<WriteInfo>();
        RecyclerView recyclerView = view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postAdapter = new PostAdapter(arrayList);
        recyclerView.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();

        writePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(WritePostActivity.class);
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
        DatabaseReference postRef = database.getReference("deptPosts").child(Storage.MyDepartment);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    WriteInfo write = snap.getValue(WriteInfo.class);
                    arrayList.add(write);
                }
                postAdapter.notifyDataSetChanged();

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