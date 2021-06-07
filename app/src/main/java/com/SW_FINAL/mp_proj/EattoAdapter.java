package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EattoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<ChatRoomInfo> arrayList = null;

    public EattoAdapter(ArrayList<ChatRoomInfo> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_eatto_card_view1, parent,false);
        EattoViewHolder holder = new EattoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EattoViewHolder)holder).chatName.setText(arrayList.get(position).getChatRoomName());
        ((EattoViewHolder)holder).chatContents.setText(arrayList.get(position).getChatContent());

        ((EattoViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = arrayList.get(position).getChatRoomName();
                Intent intent = new Intent(v.getContext(), MessageListActivity.class);
                intent.putExtra("roomName",roomName);
                removeMatching();
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class EattoViewHolder extends RecyclerView.ViewHolder{
        private TextView chatName;
        private TextView chatContents;

        public EattoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chatName = itemView.findViewById(R.id.cv_textView1);
            this.chatContents = itemView.findViewById(R.id.cv_textView3);


        }

    }
    void removeMatching(){
        /**
         *  매칭중인상태에서 매칭을 취소하고 싶은경우에  DB 삭제
         */
        DatabaseReference matchingRef = FirebaseDatabase.getInstance().getReference("matching");
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