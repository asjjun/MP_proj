package com.SW_FINAL.mp_proj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MessageInfo> arrayList = null;
    public MessageAdapter(ArrayList<MessageInfo> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_item,parent,false);
        MessageViewHolder holder = new MessageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MessageViewHolder)holder).userID.setText(arrayList.get(position).getPostID()) ;
        ((MessageViewHolder)holder).userText.setText(arrayList.get(position).getContents());
        ((MessageViewHolder)holder).myId.setText(arrayList.get(position).getPostID());
        ((MessageViewHolder)holder).myText.setText(arrayList.get(position).getContents());
        if(Storage.MyName.equals(arrayList.get(position).getPostID())){
            ((MessageViewHolder)holder).userID.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).userText.setVisibility(View.INVISIBLE);
        }else{
            ((MessageViewHolder)holder).myId.setVisibility(View.INVISIBLE);
            ((MessageViewHolder)holder).myText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView userID;
        private TextView userText;
        private TextView myId;
        private TextView myText;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userID = itemView.findViewById(R.id.userID);
            this.userText = itemView.findViewById(R.id.userText);
            this.myId = itemView.findViewById(R.id.myId);
            this.myText = itemView.findViewById(R.id.myText);
        }

    }

}
