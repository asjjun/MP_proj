package com.SW_FINAL.mp_proj;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForecdTerminationService  extends Service {
    DatabaseReference matchingRef;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        removeMatching();
        stopSelf(); //서비스 종료
    }

    void removeMatching(){
        /**
         *  매칭중인상태에서 매칭을 취소하고 싶은경우에  DB 삭제
         */
        matchingRef = FirebaseDatabase.getInstance().getReference("matching");
        matchingRef.child(Storage.MyInterest).child(Storage.MyName).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}