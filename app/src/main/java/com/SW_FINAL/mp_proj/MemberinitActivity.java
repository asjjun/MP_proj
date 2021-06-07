package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import customfonts.MyTextView_Poppins_Medium;

public class MemberinitActivity extends AppCompatActivity {
    MyTextView_Poppins_Medium checkButton;
    FirebaseAuth mAuth;
    TextInputLayout txt_input_name, txt_input_birth, txt_input_number, txt_input_address;
    Spinner spinner;
    RadioGroup radioGroup;
    RadioButton rb_man, rb_woman;
    String department,gender;
    private static final  String TAG = "MemberInitActivity";
    private long backKeyPressedTime = 0;
    private Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        getSupportActionBar().setElevation(0);

        checkButton = findViewById(R.id.checkButton);
        mAuth = FirebaseAuth.getInstance();

        txt_input_name = findViewById(R.id.txt_input_name);
        txt_input_birth = findViewById(R.id.txt_input_birth);
        txt_input_number = findViewById(R.id.txt_input_number);
        txt_input_address = findViewById(R.id.txt_input_address);

        spinner = (Spinner)findViewById(R.id.spinner);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_man = findViewById(R.id.man_radioBtn);
        rb_woman = findViewById(R.id.wm_radioBtn);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.man_radioBtn)
                {
                    gender = rb_man.getText().toString();

                }
                else if(checkedId == R.id. wm_radioBtn)
                {
                    gender = rb_woman.getText().toString();
                }
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(txt_input_name.getEditText().getText().toString().length() == 0 || txt_input_birth.getEditText().getText().toString().length() == 0 ||
                        txt_input_number.getEditText().getText().toString().length() == 0 || txt_input_address.getEditText().getText().toString().length() == 0 || gender== null)
                {
                    startToast("빈칸을 채워주세요");
                }
                else if(txt_input_name.getEditText().getText().toString().length() > 0 && txt_input_number.getEditText().getText().toString().length() > 0 &&
                        txt_input_birth.getEditText().getText().toString().length() > 0 && txt_input_address.getEditText().getText().toString().length() > 0 && gender != null)
                {
                    if(txt_input_name.getEditText().getText().toString().length() < 21 && txt_input_number.getEditText().getText().toString().length() < 12 &&
                            txt_input_birth.getEditText().getText().toString().length() < 7 && txt_input_address.getEditText().getText().toString().length() < 31) {
                        profileUpdate();
                        myStartActivity(MainActivity.class);
                        finish();
                    } else {
                        profileUpdate();
                    }
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








    /*public boolean validateName(){
        String name = txt_input_name.getEditText().getText().toString();

        if(name.isEmpty()){
            txt_input_name.setError("입력해주세요");
            return false;
        } else if(name.length()>20){
            txt_input_name.setError("이름이 너무 깁니다.");
            return false;
        } else{
            txt_input_name.setError(null);
            return true;
        }
    }
    public boolean validateBirth(){
        String birthDay = txt_input_birth.getEditText().getText().toString();

        if(birthDay.isEmpty()){
            txt_input_birth.setError("입력해주세요");
            return false;
        } else if(birthDay.length()>6){
            txt_input_birth.setError("980101과 같이 입력해주세요.");
            return false;
        } else{
            txt_input_birth.setError(null);
            return true;
        }
    }
    public boolean validateNumber(){
        String phoneNumber = txt_input_number.getEditText().getText().toString();

        if(phoneNumber.isEmpty()){
            txt_input_number.setError("입력해주세요");
            return false;
        } else if(phoneNumber.length()>10){
            txt_input_number.setError("번호가 너무 깁니다.");
            return false;
        } else{
            txt_input_number.setError(null);
            return true;
        }
    }
    public boolean validateAddress(){
        String address = txt_input_address.getEditText().getText().toString();

        if(address.isEmpty()){
            txt_input_address.setError("입력해주세요");
            return false;
        } else if(address.length()>30){
            txt_input_address.setError("주소가 너무 깁니다.");
            return false;
        } else{
            txt_input_address.setError(null);
            return true;
        }
    }*/

    private void profileUpdate(){
        String name = txt_input_name.getEditText().getText().toString().trim();
        String birthDay = txt_input_birth.getEditText().getText().toString().trim();
        String phoneNumber = txt_input_number.getEditText().getText().toString().trim();
        String address = txt_input_address.getEditText().getText().toString().trim();

        if(name.length() < 21 && phoneNumber.length() < 12 && birthDay.length() < 7 && address.length() < 31){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users").child(user.getUid());

            if(user != null)
            {
                Map<String, Object> data = new HashMap<>();
                data.put("userName", name);
                data.put("phoneNumber", phoneNumber);
                data.put("birthDay",birthDay);
                data.put("interest","");
                data.put("uid",user.getUid());
                data.put("address", address);
                data.put("department", department);
                data.put("gender", gender);
                data.put("diamond", 5);
                myRef.setValue(data);
                startToast("회원정보가 정상적으로 등록되었습니다.");

            }
        }
        else{
            startToast("글자수를 초과했습니다.");
        }

    }
    private  void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
}