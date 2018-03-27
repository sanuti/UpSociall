package com.example.sarthak.sanuti.Activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarthak.sanuti.Activities.db.Connection;
import com.example.sarthak.sanuti.Activities.db.ConnectionStatus;
import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SARTHAK on 3/24/2018.
 */

public class ConnectionRecycler extends RecyclerView.Adapter<ConnectionRecycler.MyViewHolder> {
    Context context;
    List<String> id;
    DatabaseReference reference;
    String ids;
    String user_present;
    public ConnectionRecycler(Context context, List<String> id) {
        this.context = context;
        this.id = id;


    }

    @Override
    public ConnectionRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.connectionrequestrecycler,parent,false);
        context=parent.getContext();
        Log.v("sofid",String.valueOf(id.size()));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ConnectionRecycler.MyViewHolder holder, int position) {
        ids=id.get(position);
        user_present=SharePrefrences.readString(context,CONFIG.userid);
        reference=FirebaseDatabase.getInstance().getReference(CONFIG.db_name).child(ids);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    holder.tname.setText(user.getFirstName() + "  " + user.getLastName());
                    holder.tgender.setText(user.getGender());
                    Picasso.with(context).load(user.getPic1()).placeholder(R.drawable.wave).into(holder.pro);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    holder.tname.setVisibility(View.INVISIBLE);
                    holder.tgender.setVisibility(View.INVISIBLE);
                    holder.pro.setVisibility(View.INVISIBLE);
                    holder.accept.setVisibility(View.INVISIBLE);
                    holder.disaccept.setVisibility(View.INVISIBLE);
                    holder.te.setVisibility(View.VISIBLE);
                    holder.tdistance.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionStatus.accept_connection(user_present,ids,context);
                ConnectionStatus.accept_connection(ids,user_present,context);
            }
        });
        holder.disaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionStatus.defuse_connection(user_present,ids,context);
                ConnectionStatus.defuse_connection(ids,user_present,context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView pro,accept,disaccept;
        TextView tname,tgender,tdistance,te;
        public MyViewHolder(View itemView) {
            super(itemView);
            pro=itemView.findViewById(R.id.userimgrequest);
            accept=itemView.findViewById(R.id.acceptrequest);
            disaccept=itemView.findViewById(R.id.rejectrequest);
            tname= itemView.findViewById(R.id.userrequest);
            tgender=itemView.findViewById(R.id.usergenrequest);
            tdistance=itemView.findViewById(R.id.userdisrequest);
            te=itemView.findViewById(R.id.ff);
        }
    }
}
