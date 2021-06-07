package com.SW_FINAL.mp_proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchingFragment extends Fragment {
    public View view;
    FloatingActionButton interestBtn;
    ImageView matching_loading, searching;
    String content;
    TextView dia;
    RecyclerView matching_rv;
    boolean searching_btnClick=true;
    boolean isReady;
    FirebaseDatabase database;
    DatabaseReference matchingRef, chatRef;
    ArrayList<ChatRoomInfo> chatRoomArrayList;
    EattoAdapter eattoAdapter;
    private FirebaseDatabase mDatabase = null;
    private DatabaseReference mRefMemberInfo;
    private DataSnapshot mSnapMemberInfo = null;

    ArrayList<MatchedInfo> matchedUIDArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matching,container,false);
        final UserModel userModel = new UserModel();

        matching_loading = (ImageView) view.findViewById(R.id.matching_loading);
        searching = (ImageView) view.findViewById(R.id.searching);
        interestBtn = (FloatingActionButton) view.findViewById(R.id.fab);
        Glide.with(this).load(R.drawable.matching_loading).into(matching_loading);
        database = FirebaseDatabase.getInstance();
        matchedUIDArrayList = new ArrayList<>();

        dia = (TextView) view.findViewById(R.id.dia);

        //리싸이클러 뷰
        chatRoomArrayList = new ArrayList<ChatRoomInfo>();
        matching_rv = view.findViewById(R.id.matching_rv);
        eattoAdapter = new EattoAdapter(chatRoomArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        matching_rv.setLayoutManager(linearLayoutManager);
        matching_rv.setAdapter(eattoAdapter);
        eattoAdapter.notifyDataSetChanged();
        mDatabase = FirebaseDatabase.getInstance();
        mRefMemberInfo = mDatabase.getReference("users");
        matchingRef = database.getReference("matching");
        chatRef = database.getInstance().getReference("chat");
        chatRoomLoad();
        searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searching_btnClick)
                {
                    if(Storage.MyInterest.equals(""))
                    {
                        Toast.makeText(v.getContext(), "관심사를 선택하세요", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        matching_loading.setVisibility(View.VISIBLE);
                        searching_btnClick = false;
                        matching_addUser();
                        isReady =true;
                        matchThread matchThread = new matchThread();
                        matchThread.start();
                    }
                }
                else
                {
                    matching_loading.setVisibility(View.GONE);
                    searching_btnClick = true;
                    removeMatching();
                    isReady=false;
                }


            }
        });

        interestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InterestActivity.class);
                startActivity(intent);
                removeMatching();
                isReady=false;
            }
        });


        mRefMemberInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mSnapMemberInfo = snapshot;
                for(DataSnapshot child : mSnapMemberInfo.getChildren()){
                    UserModel userModel = child.getValue(UserModel.class);

                    String FbUserId = userModel.getUid();
                    String FbInterest = userModel.getInterest(); // 식사 3대3으로 할지 2대2로할지 라디오버튼으로 가져와야됨
                    int FbDiamond = userModel.getDiamond();
                    String FbDepartment = userModel.getDepartment();
                    String FbUserName = userModel.getUserName();
                    /**
                     * 자신의 아이디와 일치할경우 DB정보 가져온다.
                     */
                    if(FbUserId.equals(Storage.MyId))
                    {

                        Storage.MyInterest = FbInterest;
                        Storage.Mydiamond = FbDiamond;
                        Storage.MyDepartment = FbDepartment;
                        Storage.MyName = FbUserName;

                        Log.e("Diamond", String.valueOf(Storage.MyName));
                        dia.setText(" X " + Storage.Mydiamond);
                        break;
                    }
                }
                if(Storage.Mydiamond == 0){
                    searching.setVisibility(View.INVISIBLE);
                }
                else{
                    searching.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("AccountFragment", "Failed to read value.", error.toException());
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e( "onStop: ","This is onPause" );
        matching_loading.setVisibility(View.GONE);
        searching_btnClick = true;

    }



    void chatRoomLoad(){
        DatabaseReference chatRoomRef = database.getReference("chat");
        chatRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatRoomArrayList.clear();
                String roomName;
                String inRoomUser;
                for(DataSnapshot snap : snapshot.getChildren()){
                    Boolean isCheckRoom = false;
                    roomName = snap.getKey();

                    for(DataSnapshot sn: snap.child("comments").getChildren()){
                        content= sn.child("message").getValue(String.class);
                    }



                    for(DataSnapshot dataSnapshot: snap.child("users").getChildren())
                    {
                        inRoomUser = dataSnapshot.getKey();

                        if(Storage.MyId.equals(inRoomUser))
                        {
                            isCheckRoom = true;
                        }
                    }

                    if(isCheckRoom ==true)
                    {
                        ChatRoomInfo chatRoomInfo = new ChatRoomInfo(roomName,content);
                        chatRoomArrayList.add(chatRoomInfo);
                    }


                }

                eattoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void matching_addUser(){

        /**
         * DB에 매칭버튼을 누른 유저이름을 넣는다.
         */

        matchingRef.child(Storage.MyInterest).child(Storage.MyName).setValue(Storage.MyId);
    }

    void matching_removeUser(){
        /**
         *  매칭중인상태에서 매칭을 취소하고 싶은경우에  DB 삭제
         */

        matchingRef.child(Storage.MyInterest).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    void removeMatching(){
        /**
         *  매칭중인상태에서 매칭을 취소하고 싶은경우에  DB 삭제
         */

        matchingRef.child(Storage.MyInterest).child(Storage.MyName).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    void matchingMember(){

        matchingRef.child(Storage.MyInterest).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchedUIDArrayList.clear();
                for(DataSnapshot child : snapshot.getChildren())
                {
                    String name = child.getKey();
                    String uid = child.getValue(String.class);

                    MatchedInfo matchedInfo = new MatchedInfo(name,uid);
                    matchedUIDArrayList.add(matchedInfo);

                }
                Log.d( "matchedUidArrayList: ", String.valueOf(matchedUIDArrayList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    class matchThread extends Thread{
        @Override
        public void run() {

            while(isReady)
            {
                try {

                    matchingMember();
                    sleep(1000);
                    Log.d( "사이즈: ", String.valueOf(matchedUIDArrayList.size()));
                    if(matchedUIDArrayList.size() == 2 && Storage.MyInterest.equals("1대1"))
                    {
                        useDiamond();
                        Activity root = getActivity();
                        Intent intent = new Intent(root, MessageActivity.class);
                        addUidChat();
                        startActivity(intent);
                        sleep(1000);
                        matching_removeUser();
                        matchedUIDArrayList.clear();
                        break;


                    }
                    else if(matchedUIDArrayList.size() == 4 && Storage.MyInterest.equals("2대2"))
                    {
                        useDiamond();
                        Activity root = getActivity();
                        Intent intent = new Intent(root, MessageActivity.class);
                        startActivity(intent);
                        addUidChat();
                        matchedUIDArrayList.clear();
                        sleep(1000);
                        matching_removeUser();
                        break;

                    }
                    else if(matchedUIDArrayList.size() == 3 && Storage.MyInterest.equals("3인"))
                    {
                        useDiamond();
                        Activity root = getActivity();
                        Intent intent = new Intent(root, MessageActivity.class);
                        startActivity(intent);
                        addUidChat();
                        matchedUIDArrayList.clear();
                        sleep(1000);
                        matching_removeUser();
                        break;

                    }
                    else if(matchedUIDArrayList.size() == 2 && Storage.MyInterest.equals("2인"))
                    {
                        useDiamond();
                        Activity root = getActivity();
                        Intent intent = new Intent(root, MessageActivity.class);
                        startActivity(intent);
                        addUidChat();
                        matchedUIDArrayList.clear();
                        sleep(1000);
                        matching_removeUser();
                        break;
                    }



                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    void addUidChat(){
        String roomName="";
        for(MatchedInfo matchedInfo : matchedUIDArrayList){
            roomName += matchedInfo.getName() +", ";
        }
        roomName = roomName.substring(0, roomName.length()-2);
        Storage.MyroomName = roomName;

        for(MatchedInfo matchedInfo : matchedUIDArrayList){
            chatRef.child(roomName).child("users").child(matchedInfo.getUid()).setValue("true");
        }

        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("name","관리자");
        objectMap.put("message",  Storage.MyName +"님 환영합니다.");
        chatRef.child(Storage.MyroomName).child("comments").push().setValue(objectMap);



    }

    void useDiamond(){
        if(Storage.Mydiamond > 0){
            Storage.Mydiamond -= 1;
            Map<String, Object> objectMap = new HashMap<String, Object>();
            objectMap.put("diamond", Storage.Mydiamond);
            mRefMemberInfo.child(Storage.MyId).updateChildren(objectMap);
            Log.e( "diamond", String.valueOf(Storage.Mydiamond));
        }
    }


}