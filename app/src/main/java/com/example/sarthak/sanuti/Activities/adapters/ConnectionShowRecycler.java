package com.example.sarthak.sanuti.Activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarthak.sanuti.Activities.actiivity.ChatActivity;
import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SARTHAK on 3/25/2018.
 */

public class ConnectionShowRecycler extends RecyclerView.Adapter<ConnectionShowRecycler.MyViewHolder>
{
     List<User> users;
    Context context;
    User user;

    public ConnectionShowRecycler(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public ConnectionShowRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_connection,parent,false);
        context=parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConnectionShowRecycler.MyViewHolder holder, int position) {
    user=users.get(position);
        Log.v("suer",String.valueOf(user));
        try {
            holder.txt.setText(user.getFirstName() + "  " + user.getLastName());
            Picasso.with(context).load(user.getPic1()).placeholder(R.drawable.icon_large).into(holder.img);
        }
        catch (Exception e)
        {
            holder.txt.setVisibility(View.INVISIBLE);
            holder.img.setVisibility(View.INVISIBLE);
            holder.txt2.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt,txt2;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.connectimage);
            txt=itemView.findViewById(R.id.connectname);
            txt2=itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, user.getFirstName(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context, ChatActivity.class);
                    intent.putExtra("chatid",user.getUserId());
                    intent.putExtra("url",user.getPic1());
                    intent.putExtra("chatname",user.getFirstName()+" "+user.getLastName());
                    context.startActivity(intent);
                }
            });

        }

    }
}
