package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import customfonts.MyTextView_Poppins_Medium;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    MyTextView_Poppins_Medium loginButton, signUpButton;
    FirebaseUser user;
    Boolean isTrue=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setElevation(0);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        user = mAuth.getCurrentUser();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(SignUpActivity.class);
            }
        });



    }

    private void login(){
        String email = ((EditText)findViewById(R.id.username_input)).getText().toString();
        String password = ((EditText)findViewById(R.id.pass)).getText().toString();

        if(email.length() > 0 && password.length()> 0 ){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user =  mAuth.getCurrentUser();
                                startToast("로그인에 성공했습니다.");
                                if(user.isEmailVerified()){
                                    Storage.MyId =user.getUid();
                                    checkDB();
                                }else{
                                    Storage.MyId =user.getUid();
                                    myStartActivity(EmailVerifyActivity.class);
                                    finish();
                                }


                            } else {
                                // If sign in fails, display a message to the user.


                            }
                        }
                    });
        }else{
            startToast("이메일 또는 비밀번호를 입력하세요.");
        }

    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private  void checkDB() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot snapshot : task.getResult().getChildren())
                    {
                        if(Storage.MyId.equals(snapshot.child("uid").getValue()))
                        {
                            isTrue = true;
                        }


                    }

                    if(isTrue==true)
                    {Log.e( "파베유저아이디", Storage.MyId);
//                            Storage.MyId = user.getUid();
                        myStartActivity(MainActivity.class);
                    }else{
                        myStartActivity(MemberinitActivity.class);

                    }

                    finish();

                }
            }
        });
    }
}