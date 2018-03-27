package com.example.sarthak.sanuti.Activities.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sarthak.sanuti.Activities.adapters.UserRecyclerSearch;
import com.example.sarthak.sanuti.Activities.db.Connection;
import com.example.sarthak.sanuti.Activities.db.Databasehelper;
import com.example.sarthak.sanuti.Activities.db.SwipeStatus;
import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.CustomScrollListener;
import com.example.sarthak.sanuti.Activities.utils.LatLang;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * Created by SARTHAK on 3/15/2018.
 */

public class FindPeopleFragments extends Fragment {
    RecyclerView recyclerView;
    String lati;
    String longi;
    DatabaseReference fuzzy;
    UserRecyclerSearch search;
    String userdd;
    String un;
    Bitmap myBitmap;
    List<User> uu=new ArrayList<>();    //To hold the user which is at the distance
    List<String> ids=new ArrayList<>(); //to hold the id of the user which is the distance apaart

    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.findpeoplefragments,container,false);
        recyclerView=view.findViewById(R.id.people_recycler);
        userdd=SharePrefrences.readString(getActivity(),CONFIG.userid);
        un=SharePrefrences.readString(getActivity(),CONFIG.gender);
        lati=new Databasehelper(getActivity()).list_data(7);
        longi=new Databasehelper(getActivity()).list_data(10);
        Log.v("error",lati);
        if(lati==null)
            lati="0.00000";
        if(longi.equals(null))
            longi="0.00000";
        Log.v("Databse :  ",lati);
        new jai().execute(lati,longi);
        //Toast.makeText(getActivity(), "Ending adapter" , Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    class jai extends AsyncTask<String,Void,Void>
    {


        @Override
        protected Void doInBackground(final String... strings) {
            userdd= SharePrefrences.readString(getActivity(), CONFIG.userid);

            reference= FirebaseDatabase.getInstance().getReference(CONFIG.latlang_db_name);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        Log.v("DataSnapshot : ","DataSnapshot exist");
                        //ids.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren())
                        {

                            final LatLang latLang=snapshot.getValue(LatLang.class);
                            if(userdd.equals(latLang.getId()))
                            {

                            }
                            else
                            {
                                double lati2= Double.parseDouble(latLang.getLati());
                                double longi2=Double.parseDouble(latLang.getLongi());
                                Location loc1 = new Location("");
                                String lati1=strings[0];
                                String longi1=strings[1];
                                loc1.setLatitude(Double.parseDouble(lati1));
                                loc1.setLongitude(Double.parseDouble(longi1));

                                Location loc2 = new Location("");
                                loc2.setLatitude(lati2);
                                loc2.setLongitude(longi2);
                                Log.v("User is:",userdd);
                                Log.v("Id is",latLang.getId());
                                ids.add(latLang.getId());

                                //Log.v("Size is kkk :",String.valueOf(ids.size()));
                                float distanceInMeters = loc1.distanceTo(loc2)/1000;
                                    fuzzy=FirebaseDatabase.getInstance().getReference(CONFIG.db_name).child(latLang.getId());
                                    fuzzy.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            try {
                                                final User user = dataSnapshot.getValue(User.class);
                                                //Toast.makeText(getActivity(), "Adapter loading", Toast.LENGTH_SHORT).show();
                                                Log.v("User is :  ", String.valueOf(user));
                                                int i = new Databasehelper(getActivity()).match_reject(user.getUserId());
                                                if (i != 1){ Log.v("reject", "reject");
                                                String gen = user.getGender();
                                                Log.v("UNNN", un);
                                                if (gen.equals("")) {
                                                    gen = "both";
                                                }
                                                if (un.equals(user.getGender()) || un.equals("both") && !user.equals(null)) {
                                                    Log.v("UNNN", un);
                                                    try {
                                                        URL url = new URL(user.getPic1());
                                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                                        connection.setDoInput(true);
                                                        // connection.connect();
                                                        InputStream input = connection.getInputStream();
                                                        myBitmap = BitmapFactory.decodeStream(input);
                                                        // ByteArrayOutputStream out = new ByteArrayOutputStream();


                                                    } catch (IOException e) {
                                                        //e.printStackTrace();
                                                    }
                                                    user.setBmp(myBitmap);
                                                    uu.add(user);
                                                }
                                                if (uu.size() > 0) {
                                                    Log.v("Size:,", String.valueOf(uu.size()));
                                                    search = new UserRecyclerSearch(uu, getActivity());


                                                    //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
                                                    Log.v("Adapter", "adapter");
                                                    RecyclerView.LayoutManager mLayoutManager =
                                                            new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                                                    recyclerView.setLayoutManager(mLayoutManager);
                                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                                    recyclerView.setOnFlingListener(null);
                                                    SnapHelper snapHelper = new PagerSnapHelper();
                                                    snapHelper.attachToRecyclerView(recyclerView);
                                                    recyclerView.addOnScrollListener(new CustomScrollListener());
                                                    recyclerView.setAdapter(search);
                                                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP) {
                                                        @Override
                                                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                                            final int i = viewHolder.getAdapterPosition();


                                                            User jj;
                                                            jj = uu.get(i);
                                                            new Databasehelper(getActivity()).insert_reject(jj.getUserId());
                                                            Log.v("UPID",jj.getUserId());
                                                            final String req = jj.getUserId();
                                                            SwipeStatus.userRequest(userdd,req);
                                                            SwipeStatus.rejectUser(userdd,req);
                                                            SwipeStatus.rejectUser(req,userdd);
                                                            try {
                                                                uu.remove(i);
                                                                UserRecyclerSearch search1=new UserRecyclerSearch(uu,getActivity());
                                                                recyclerView.setAdapter(search1);
                                                            }
                                                            catch (Exception e)
                                                            {
                                                                Log.e("ERROR","REMOVING");
                                                            }

                                                            Log.v("ITEM", String.valueOf(i));

                                                        }

                                                    };
                                                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                                                    itemTouchHelper.attachToRecyclerView(recyclerView);

                                                    ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {
                                                        @Override
                                                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                                                            int id = recyclerView.getId();
                                                            Log.v("OnDown", String.valueOf(id));
                                                            return true;
                                                        }

                                                        @Override
                                                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                                             final int i = viewHolder.getAdapterPosition();
                                                            User j;
                                                            j = uu.get(i);
                                                            final String req=j.getUserId();
                                                            Log.v("DOWNID",j.getUserId());
                                                            new Databasehelper(getActivity()).insert_reject(j.getUserId());
                                                            SwipeStatus.rejectUser(userdd,req);
                                                            SwipeStatus.rejectUser(req,userdd);
                                                            if(i>=0)
                                                                try {
                                                                    uu.remove(i);
                                                                    UserRecyclerSearch search1=new UserRecyclerSearch(uu,getActivity());
                                                                    recyclerView.setAdapter(search1);
                                                                }
                                                                catch (Exception e)
                                                                {
                                                                    Log.e("ERROR","REMOVING");
                                                                }

                                                        }
                                                    };
                                                    ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback2);
                                                    itemTouchHelper2.attachToRecyclerView(recyclerView);
                                                    recyclerView.setAdapter(search);
                                                    search.notifyDataSetChanged();

                                                } else {
                                                    //Toast.makeText(getActivity(), "No user around you", Toast.LENGTH_SHORT).show();
                                                }
                                                fuzzy.removeEventListener(this);
                                            }
                                            else
                                                {
                                                    Log.v("REJECTED","dfs");
                                                }
                                                }

                                            catch(Exception e)
                                                {
                                                    Log.v("Getting erroer", String.valueOf(e));
                                                }

                                            }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });






                                Log.v("DIstance with first",String.valueOf(distanceInMeters));
                                //search.notifyDataSetChanged();
                                reference.removeEventListener(this);

                            }
                        }
                        reference.removeEventListener(this);
                    }


                    else
                    {
                        Log.v("DataSnapshot : ","DataSnapshot not exist");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

}
