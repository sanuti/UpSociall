package com.example.sarthak.sanuti.Activities.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarthak.sanuti.Activities.adapters.ConnectionRecycler;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by SARTHAK on 3/24/2018.
 */

public class ConnectionRequest extends Fragment {
    String userpresent,userrequest;
    DatabaseReference reference;
    String conlist;
    ConnectionRecycler connectionRecycler;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.connectionrequest,container,false);
        recyclerView=view.findViewById(R.id.requestview);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.setOnFlingListener(null);
        //SnapHelper snapHelper = new PagerSnapHelper();
        //snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new CustomScrollListener());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        userpresent= SharePrefrences.readString(getActivity(),CONFIG.userid);
        reference= FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(userpresent).child("request");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               conlist=dataSnapshot.getValue().toString();
                Log.v("kaam",conlist);
                String jjf[]=conlist.split(",");
                Log.v("DD",String.valueOf(jjf.length));
               List<String> request=new ArrayList<>();
               connectionRecycler=new ConnectionRecycler(getActivity(),request);
                for(int i=0;i<jjf.length;i++)
                {
                    request.add(jjf[i]);
                }
                connectionRecycler=new ConnectionRecycler(getActivity(),request);
                recyclerView.setAdapter(connectionRecycler);
                connectionRecycler.notifyDataSetChanged();
                //String aj=request.toString().replace("[","").replace("]","");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;

    }


}
