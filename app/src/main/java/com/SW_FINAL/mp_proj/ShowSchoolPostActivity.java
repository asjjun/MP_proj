package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ShowSchoolPostActivity extends AppCompatActivity {
    Intent intent;
    TextView titleText, contentText, userNameText;
    EditText replyEditText;
    ImageView deptImg;
    String writeTitle, writeContent, publisher, postKey,replyContent, userName;
    ArrayList<ReplyInfo> replyList;
    ReplyAdapter replyAdapter;
    ListView listView;
    Button replyBtn;
    FirebaseDatabase database;
    FirebaseUser user;
    String formattedDate;
    DatabaseReference replyRef;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost_dept);

        getSupportActionBar().setElevation(0);

        intent=getIntent();
        user= FirebaseAuth.getInstance().getCurrentUser();

        listView =(ListView)findViewById (R.id.lv_reply);
        replyBtn = findViewById(R.id.replyBtn);
        replyEditText = findViewById(R.id.replyEditText);

        View header = getLayoutInflater().inflate(R.layout.postheader,null,false);
        listView.addHeaderView(header);
        userNameText = (TextView) findViewById(R.id.user_id);
        titleText = findViewById(R.id.hdPostTitle);
        contentText = findViewById(R.id.hdPostContent);
        deptImg =(ImageView)findViewById(R.id.showdeptImg);

        replyList = new ArrayList<ReplyInfo>();
        replyAdapter = new ReplyAdapter(replyList);

        userName =  Storage.MyName;

        writeTitle = intent.getStringExtra("title");
        writeContent = intent.getStringExtra("content");
        publisher = intent.getStringExtra("publisher");
        postKey = intent.getStringExtra("postID");

        storageReference = FirebaseStorage.getInstance().getReference("school").child(postKey);
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(ShowSchoolPostActivity.this).load(task.getResult()).into(deptImg);
                }
                else{

                }
            }
        });

        titleText.setText(writeTitle);
        contentText.setText(writeContent);
        userNameText.setText(publisher);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

        replyRef = database.getInstance().getReference("reply").child(postKey);


        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyContent = replyEditText.getText().toString();


                Map<String,Object> data = new HashMap<>();
                data.put("userName",userName);
                data.put("content",replyContent);
                data.put("date",formattedDate);
                data.put("userUID",user.getUid());

                replyRef.push().setValue(data);


            }
        });



        replyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                replyList.clear();

                for(DataSnapshot snap : snapshot.getChildren()){
                    Log.e("아아아아", String.valueOf(snap.getValue()));

                    String name = snap.child("userName").getValue(String.class);
                    String content = snap.child("content").getValue(String.class);
                    ReplyInfo replyInfo = new ReplyInfo(name,content);
                    replyList.add(replyInfo);

                }

                replyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException()));
            }
        });

        listView.setAdapter(replyAdapter);
        replyAdapter.notifyDataSetChanged();

    }
}
