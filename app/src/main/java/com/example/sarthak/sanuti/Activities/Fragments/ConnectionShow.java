package com.example.sarthak.sanuti.Activities.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sarthak.sanuti.Activities.adapters.ConnectionShowRecycler;
import com.example.sarthak.sanuti.Activities.db.Connection;
import com.example.sarthak.sanuti.Activities.db.GetConnection;
import com.example.sarthak.sanuti.Activities.db.GetUserById;
import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.CustomScrollListener;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SARTHAK on 3/25/2018.
 */

public class ConnectionShow extends Fragment{
    RecyclerView recyclerView;
    TextView tt;
    String user_present;
    //String userid[];
    List<User> users=new ArrayList<>();
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.connection_show_view,container,false);
        recyclerView=view.findViewById(R.id.connectionshowre);
        tt=view.findViewById(R.id.nochat);

        user_present= SharePrefrences.readString(getActivity(), CONFIG.userid);
       return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference=FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(user_present);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Connection connect=dataSnapshot.getValue(Connection.class);
                String connection=connect.getConnections();
                reference.removeEventListener(this);
                String[] userid=connection.split(",");
                for(int i=0;i<userid.length;i++)
                {
                  reference=FirebaseDatabase.getInstance().getReference(CONFIG.db_name).child(userid[i]);
                  reference.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          User user=dataSnapshot.getValue(User.class);
                          users.add(user);
                          RecyclerView.LayoutManager mLayoutManager =
                                  new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

                          recyclerView.setLayoutManager(mLayoutManager);
                          recyclerView.setItemAnimator(new DefaultItemAnimator());

                          //recyclerView.setOnFlingListener(null);
                          //SnapHelper snapHelper = new PagerSnapHelper();
                          //snapHelper.attachToRecyclerView(recyclerView);
                          recyclerView.addOnScrollListener(new CustomScrollListener());
                          try {
                              recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                          }
                          catch (Exception e)
                          {
                              e.printStackTrace();
                          }
                          ConnectionShowRecycler showRecycler = new ConnectionShowRecycler(users, getActivity());
                          recyclerView.setAdapter(showRecycler);
                          showRecycler.notifyDataSetChanged();
                            if(users.size()==0)
                                tt.setVisibility(View.VISIBLE);

                      }


                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    }

