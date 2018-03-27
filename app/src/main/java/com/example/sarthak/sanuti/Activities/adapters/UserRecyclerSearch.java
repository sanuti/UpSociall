package com.example.sarthak.sanuti.Activities.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SARTHAK on 3/20/2018.
 */

public class UserRecyclerSearch extends RecyclerView.Adapter<UserRecyclerSearch.MyViewHolder> {
      private List<User> uu=new ArrayList<>();
        Context context;

    public UserRecyclerSearch() {
    }

    User user;
    public UserRecyclerSearch(List<User> uu,Context context) {
        this.uu = uu;
        this.context=context;
    }

    @Override
    public UserRecyclerSearch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_user_filter,parent,false);
        context=parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserRecyclerSearch.MyViewHolder holder, int position) {
       user=uu.get(position);
      //Picasso.with(context) .load(user.getPic1()).into(holder.img);
       /* try {
            URL url = new URL(user.getPic1());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            // connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            holder.img.setImageBitmap(myBitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }*/
       /*new jain().execute();
       Bitmap mm=new jain().getBitmap();
       holder.img.setImageBitmap(mm);*/
       holder.img.setImageBitmap(user.getBmp());
    }

    @Override
    public int getItemCount() {
        return uu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.user_search);
        }
    }
    public void getUserOnSwipe(int value, List<User> uss)
    {
       User uudd= uss.get(value);

    }

}
