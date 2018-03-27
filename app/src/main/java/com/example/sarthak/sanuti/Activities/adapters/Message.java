package com.example.sarthak.sanuti.Activities.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sarthak.sanuti.Activities.db.ChatSender;
import com.example.sarthak.sanuti.R;

import java.util.List;

/**
 * Created by SARTHAK on 3/8/2018.
 */

public class Message extends ArrayAdapter<ChatSender> {
    private Activity context;
    ViewGroup.LayoutParams layoutParams;
    private String userid;
    String name;
    private List<ChatSender> senders;
    public Message(@NonNull Activity context, List<ChatSender> senders, String name) {
        super(context, R.layout.chatshowing_layout,senders);
        this.context=  context;
        this.senders=senders;
        this.name=name;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView mess;
        TextView tem;
        View list;
        ChatSender senr=senders.get(position);
        userid=senr.getuserid();
       if(userid.equals(name)) {
           LayoutInflater inflater=context.getLayoutInflater();
           list=inflater.inflate(R.layout.chatshowing_layout,null,true);
           mess=list.findViewById(R.id.mess);
           tem=list.findViewById(R.id.tim);

        }
        else
       {
           LayoutInflater inflater=context.getLayoutInflater();
           list=inflater.inflate(R.layout.chatreci,null,true);
           mess=list.findViewById(R.id.messr);
           tem=list.findViewById(R.id.timr);
       }
        mess.setText(senr.getMessage());
        tem.setText(senr.getTime());
         return list;
    }
}
