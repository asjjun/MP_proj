package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import customfonts.MyTextView_Poppins_Medium;

public class MemberInfoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtName, txtPhone, txtAddress, txtBirth, nameCon, phoneCon, addressCon, birthCon, genderCon, txtGender, departmentCon, txtDepartment;
    TextInputLayout addressEdi, phoneEdi;
    MyTextView_Poppins_Medium btnModify, btnDelete, btnPassword, addressModi, phoneModi, departmentModi;
    Spinner departmentEdi;
    String updateDepartment="", updateAddress, updatePhone;
    FirebaseDatabase database;
    private  static final String TAG = "MemberInfoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        getSupportActionBar().setElevation(0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtBirth = findViewById(R.id.txtBirth);
        btnModify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);
        btnPassword = findViewById(R.id.btnPassword);
        txtGender = findViewById(R.id.txtGender);
        genderCon = findViewById(R.id.genderCon);
        departmentCon = findViewById(R.id.departmentCon);
        txtDepartment = findViewById(R.id.txtDepartment);
        departmentModi = findViewById(R.id.departmentModi);
        departmentEdi = (Spinner)findViewById(R.id.departmentEdi);

        nameCon = findViewById(R.id.nameCon);
        phoneCon = findViewById(R.id.phoneCon);
        addressCon = findViewById(R.id.addressCon);
        birthCon = findViewById(R.id.birthCon);

        addressModi = findViewById(R.id.addressModi);
        phoneModi = findViewById(R.id.phoneModi);

        addressEdi = findViewById(R.id.addressEdi);
        phoneEdi = findViewById(R.id.phoneEdi);

        DatabaseReference userRef = database.getReference("users").child(user.getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue(String.class);
                String address = snapshot.child("address").getValue(String.class);
                String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                String birthDay = snapshot.child("birthDay").getValue(String.class);
                String gender = snapshot.child("gender").getValue(String.class);
                String department = snapshot.child("department").getValue(String.class);


                nameCon.setText(name);
                addressCon.setText(address);
                phoneCon.setText(phoneNumber);
                birthCon.setText(birthDay);
                genderCon.setText(gender);
                departmentCon.setText(department);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addressModi.setOnClickListener(this);
        phoneModi.setOnClickListener(this);
        departmentModi.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference updateRef = database.getReference("users").child(user.getUid());

        updateAddress = addressEdi.getEditText().getText().toString();
        updatePhone = phoneEdi.getEditText().getText().toString();

        switch (v.getId()) {
            case R.id.departmentModi:
                departmentEdi.setVisibility(View.VISIBLE);
                departmentEdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MemberInfoActivity.this, parent.getItemAtPosition(position) + "을 선택 했습니다.", Toast.LENGTH_SHORT).show();
                        updateDepartment = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.addressModi:
                addressEdi.setVisibility(View.VISIBLE);
                break;
            case R.id.btnPassword:
                myStartActivity(ModiPassword.class);
                break;
            case R.id.phoneModi:
                phoneEdi.setVisibility(View.VISIBLE);
                break;
            case R.id.btnModify:
                Map<String, Object> result = new HashMap<>();


                if (updateAddress.length() !=  0) {
                    result.put("address", updateAddress);
                }
                if (updatePhone.length() != 0) {
                    result.put("phoneNumber", updatePhone);
                }
                if (updateDepartment.length() >0) {
                    result.put("department", updateDepartment);
                }

                updateRef.updateChildren(result);
                startToast("회원정보가 수정되었습니다.");

                Intent intent = getIntent();    //액티비티 새로고침
                finish();
                startActivity(intent);
                break;

            case R.id.btnDelete:
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateRef.removeValue();
                            myStartActivity(LoginActivity.class);   //새로고침으로 바꿔야함
                            startToast("탈퇴되었습니다.");
                        }
                    }
                });
                break;
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
}