package com.example.sarthak.sanuti.Activities.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sarthak.sanuti.R;

/**
 * Created by SARTHAK on 3/16/2018.
 */

public class HobbyRecycler extends RecyclerView.Adapter<HobbyRecycler.MyViewHolder> {
    String array[];
    String hobby[]={"#Fitness","#Yoga/Meditatio","#Blogging","#Nature","#Shopping","#Techonology"};
    int count;
    public HobbyRecycler(String[] array,int count) {
        this.array = array;
        this.count=count;
        array=new String[count];
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buttonrecycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         holder.button.setText(array[position]);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView button;

        public MyViewHolder(View view) {
            super(view);
            button=view.findViewById(R.id.button3);
        }
    }
}
