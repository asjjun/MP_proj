package com.SW_FINAL.mp_proj;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<WriteInfo> arrayList = null;
    public PostAdapter(ArrayList<WriteInfo> arrayList){
        this.arrayList = arrayList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_post_item,parent,false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder)holder).postTitle.setText(arrayList.get(position).getTitle()) ;
        ((PostViewHolder)holder).postContents.setText(arrayList.get(position).getContents());
        ((PostViewHolder)holder).tv_postTime.setText(arrayList.get(position).getDate());

        ((PostViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postID = arrayList.get(position).getPostID();
                String title = arrayList.get(position).getTitle();
                String content = arrayList.get(position).getContents();
                String publisher = arrayList.get(position).getPublisher();
                Log.e("bbbbbbbbbbbbbbbbbb",publisher);
                Intent intent = new Intent(v.getContext(), ShowDeptPostActivity.class);
                intent.putExtra("postID",postID);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("publisher",publisher);

                v.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
       private TextView postTitle;
        private TextView postContents;
        private TextView tv_postTime;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postTitle = itemView.findViewById(R.id.postTitle);
            this.postContents = itemView.findViewById(R.id.postContents);
            this.tv_postTime = itemView.findViewById(R.id.tv_postTime);

        }

    }

}
