package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import customfonts.MyTextView_Poppins_Medium;

public class EmailVerifyActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    MyTextView_Poppins_Medium send_email,check_verify ;
    TextView showVeri;
    private static final String TAG = "EmailVerifyActivity";
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        getSupportActionBar().setElevation(0);

        send_email = findViewById(R.id.send_check_verify);
        check_verify = findViewById(R.id.check_verify);
        showVeri = findViewById(R.id.showVeriText);
        mAuth = FirebaseAuth.getInstance();

        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVeri.setText("인증메일을 보냈습니다.");
                verification();
            }
        });

        check_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser().reload();
                if(mAuth.getCurrentUser().isEmailVerified()){
                    myStartActivity(MemberinitActivity.class);
                    finish();

                    Log.e(TAG, "intent error");
                }else{
                    // 이메일 미인증시
                    startToast("메일인증하세요");
                }


            }
        });

    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();

        }
    }



    private void verification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startToast("보냈다.");
                            check_verify.setEnabled(true);

                        }
                    }
                });
    }
    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);


    }

}
