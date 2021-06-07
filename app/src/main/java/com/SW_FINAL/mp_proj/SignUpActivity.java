package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
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

import customfonts.MyTextView_Poppins_Medium;

public class SignUpActivity extends AppCompatActivity {
    private  static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    MyTextView_Poppins_Medium signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setElevation(0);

        signUpButton = findViewById(R.id.signUpButton);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void sign(){
        String email = ((EditText)findViewById(R.id.sign_emailEditText)).getText().toString() ;
        String password = ((EditText)findViewById(R.id.sign_passwordEditText)).getText().toString();
        String passwordCheck = ((EditText) findViewById(R.id.sign_passwordCheckEditText)).getText().toString();

        if(email.length() > 0 && password.length()> 0 && passwordCheck.length() >0){
            if(password.equals(passwordCheck))
            {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("회원가입 성공했습니다.");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    myStartActivity(LoginActivity.class);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    startToast(task.getException().toString());
                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }

    }


    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private  void myStartActivity(Class c){
        Intent intent = new  Intent(this,c);
        startActivity(intent);
    }

    /*public void MyputExtra(Class a, String b){ //intent를 통해 다른 액티비티로 값 전달
        Intent intent = new Intent(this, a);
        intent.putExtra("value", b);
        startActivity(intent);
    }*/

}