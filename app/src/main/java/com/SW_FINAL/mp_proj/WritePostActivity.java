package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import customfonts.MyTextView_Poppins_Medium;

public class WritePostActivity extends AppCompatActivity {
    MyTextView_Poppins_Medium writeCheckBtn, addImageBtn;
    ImageView addImageView;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        getSupportActionBar().setElevation(0);

        writeCheckBtn = findViewById(R.id.writeCheckBtn);
        addImageBtn = findViewById(R.id.addImagebtn);
        addImageView = (ImageView)findViewById(R.id.addImageView);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();



        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }
        });




        writeCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteInfo();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    addImageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void WriteInfo(){
        String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        String contents = ((EditText) findViewById(R.id.contentsEditText)).getText().toString();

        if(title.length() > 0  && contents.length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("deptPosts");
            DatabaseReference nameRef = database.getReference("users").child(user.getUid());
            String postID = myRef.push().getKey();


            if(addImageView.getDrawable() != null)
            {
                StorageReference imagesRef = storageRef.child("dept").child(postID);
                // Get the data from an ImageView as bytes
                addImageView.setDrawingCacheEnabled(true);
                addImageView.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) addImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = imagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...

                    }
                });
            }



            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            String formattedDate = df.format(c.getTime());

            if(user != null)
            {
                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> data = new HashMap<>();

                        String department = snapshot.child("department").getValue(String.class);

                        data.put("title", title);
                        data.put("contents", contents);
                        data.put("publisher",snapshot.child("userName").getValue());
                        data.put("postID",postID);
                        data.put("Date",formattedDate);
                        myRef.child(department).push().setValue(data);
                        startToast("게시글이 정상적으로 등록되었습니다.");
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

        }
        else{
            startToast("게시글을 입력해주세요");
        }

    }

    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
