package com.SW_FINAL.mp_proj;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import customfonts.MyTextView_Poppins_Medium;

public class InterestActivity extends AppCompatActivity{
    public static String TAG = "InterestActivity";

    private RadioGroup radioInterest;
    private RadioButton two_people,three_people,one_to_one,two_to_two;
    MyTextView_Poppins_Medium btnSetInterest;


    /**
     * FiraBase DB
     */
    private FirebaseDatabase mDatabase = null;
    private DatabaseReference mRefMemberInfo;
    private DataSnapshot mSnapMemberInfo = null;
    private ValueEventListener valueEventListener;
    private ArrayList<String> interestArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        getSupportActionBar().setElevation(0);
        radioInterest = (RadioGroup)findViewById(R.id.radioInterest);
        radioInterest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String rb;
                if(checkedId == R.id.two_people)
                {
                    rb = two_people.getText().toString();
                    Storage.MyInterest = rb;
                }
                else if(checkedId == R.id.three_people)
                {
                    rb = three_people.getText().toString();
                    Storage.MyInterest = rb;
                }
                else if( checkedId == R.id.one_to_one)
                {
                    rb = one_to_one.getText().toString();
                    Storage.MyInterest = rb;
                }
                else{
                    rb = two_to_two.getText().toString();
                    Storage.MyInterest = rb;
                }
            }
        });

        two_people = (RadioButton)findViewById(R.id.two_people);
        three_people = (RadioButton)findViewById(R.id.three_people);
        one_to_one = (RadioButton)findViewById(R.id.one_to_one);
        two_to_two = (RadioButton)findViewById(R.id.two_to_two);

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance();
        mRefMemberInfo = mDatabase.getReference();

        // Read from the database
        valueEventListener = mRefMemberInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                mSnapMemberInfo = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        btnSetInterest = findViewById(R.id.btn_set_interest);         //관심설정 버튼
        btnSetInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSnapMemberInfo == null)
                {
                    Toast.makeText(InterestActivity.this, "회원정보 검색중 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }



                if(two_people.isChecked() == false && three_people.isChecked() == false && one_to_one.isChecked() == false && two_to_two.isChecked() == false)
                {
                    Toast.makeText(InterestActivity.this, "매칭조건을 1개이상 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mRefMemberInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mRefMemberInfo.child("users").child(Storage.MyId).child("interest").setValue(Storage.MyInterest);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(InterestActivity.this, "매칭조건 설정 완료", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "어래이 : "+interestArray);
                finish();
            }

        });

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mRefMemberInfo.removeEventListener(valueEventListener);
    }

}