package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import customfonts.MyTextView_Poppins_Medium;

public class ModiPassword extends AppCompatActivity {
    private  static final String TAG = "ModiPassword";

    TextView txtID, txtMyId, txtPassword;
    EditText ModiPassword;
    MyTextView_Poppins_Medium btnPassModi;
    FirebaseDatabase database;
    String newPassword, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_password);

        getSupportActionBar().setElevation(0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        txtID = findViewById(R.id.txtID);
        txtMyId = findViewById(R.id.txtMyId);
        txtPassword = findViewById(R.id.txtPassword);
        ModiPassword = findViewById(R.id.ModiPassword);
        btnPassModi = findViewById(R.id.btnPassModi);
        newPassword = ModiPassword.getText().toString();

        /*Intent intent = getIntent();
        String myPassword = intent.getStringExtra("value"); //SignUpActivity에서 보낸 데이터 받음*/
        String myPassword = ModiPassword.getText().toString();

        if(user != null){
            email = user.getEmail();
            txtMyId.setText(email);
        }

        btnPassModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ModiPassword.getText().toString() != null){
                    newPassword = ModiPassword.getText().toString();
                    if(myPassword == newPassword){
                        startToast("비밀번호가 기존과 동일합니다.");
                    } else {
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User password updated.");
                                            myStartActivity(LoginActivity.class);
                                            startToast("비밀번호가 재설정되었습니다.");
                                        }
                                    }
                                });
                    }


                }
            }
        });


    }

    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

}