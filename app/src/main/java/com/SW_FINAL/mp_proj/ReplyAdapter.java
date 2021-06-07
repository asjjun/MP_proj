package com.SW_FINAL.mp_proj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReplyAdapter extends BaseAdapter {
    private TextView replyTitleTv;
    private TextView replyContentTv;

    private ArrayList<ReplyInfo> replyList=new ArrayList<ReplyInfo>();

    public ReplyAdapter(ArrayList<ReplyInfo> arrayList){
        this.replyList=arrayList;
    }


    @Override
    public int getCount() {
        return replyList.size();
    }

    @Override
    public Object getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context=parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_reply_item,parent,false);
        }

        replyTitleTv = (TextView)convertView.findViewById(R.id.reply_id);
        replyContentTv = (TextView)convertView.findViewById(R.id.reply_content);



//       replyTitleTv.setText(replyList.get(position).getReplyId());
//       replyContentTv.setText(replyList.get(position).getReplyContent());

        replyTitleTv.setText(replyList.get(position).getUserName());
        replyContentTv.setText(replyList.get(position).getReplyContent());

        return convertView;
    }


}
