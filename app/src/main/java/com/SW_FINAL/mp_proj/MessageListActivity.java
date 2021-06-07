package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView lv_chating;
    private EditText et_send;
    private Button btn_send;
    //private ArrayAdapter<String> arrayAdapter;
    //private ArrayList<String> arr_room = new ArrayList<>();
    ArrayList<MessageInfo> arrayList;
    MessageAdapter messageAdapter;
    private String str_user_name;
    private DatabaseReference reference, roomNameRef;

    private String chat_user;
    private String chat_message;
    Intent intent;
    String roomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().setElevation(0);

        et_send = (EditText) findViewById(R.id.et_send);
        lv_chating = (RecyclerView) findViewById(R.id.lv_chating);
        btn_send = (Button) findViewById(R.id.btn_send);
        arrayList = new ArrayList<MessageInfo>();
        messageAdapter = new MessageAdapter(arrayList);
        intent = getIntent();
        roomName = intent.getStringExtra("roomName");
        lv_chating.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reference = FirebaseDatabase.getInstance().getReference("chat").child(roomName).child("comments");
        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });

        str_user_name = Storage.MyName;


        lv_chating.setAdapter(messageAdapter);
        // 리스트뷰가 갱신될때 하단으로 자동 스크롤
        //lv_chating.setTranscriptMode(RecyclerView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                // map을 사용해 name과 메시지를 가져오고, key에 값 요청
                Map<String, Object> map = new HashMap<String, Object>();

                reference.push().updateChildren(map);


                // updateChildren를 호출하여 database 최종 업데이트
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("name", str_user_name);
                objectMap.put("message", et_send.getText().toString());

                reference.push().updateChildren(objectMap);

                et_send.setText("");
            }
        });



    }

    // addChildEventListener를 통해 실제 데이터베이스에 변경된 값이 있으면,
    // 화면에 보여지고 있는 Listview의 값을 갱신함
    private void chatConversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            chat_message = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();
            MessageInfo message = new MessageInfo(chat_user, chat_message);
            arrayList.add(message);
        }


        messageAdapter.notifyDataSetChanged();
    }
}