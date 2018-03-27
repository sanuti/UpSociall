package com.example.sarthak.sanuti.Activities.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarthak.sanuti.Activities.adapters.HobbyRecycler;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by SARTHAK on 3/15/2018.
 */

public class UserFragment extends Fragment {
    View view;
    GridView gridView;
    TextView more,like,less;
      RecyclerView recyclerView;
      ImageView imageView;
      String userid,url;
      DatabaseReference reference;
      int count=4;
      RadioGroup rgp;
      String wder;
      RadioButton male,female,both;
      int selected;
      TextView loc,edu,gender,work,abt,intrestss,likess;
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
      HobbyRecycler hobbyRecycler;
    String hobby[]={"#Fitness","#Yoga/Meditatio","#Blogging","#Nature","#Shopping","#Techonology","#Television","#Relationship","#Foddy"};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path=SharePrefrences.readString(getActivity(),"path");


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_info_fragment,container,false);
       recyclerView=view.findViewById(R.id.hobbie_recycler);
       like=view.findViewById(R.id.morelikes);
       less=view.findViewById(R.id.lesslike);
       imageView=view.findViewById(R.id.profile_pic);
        imageView=view.findViewById(R.id.profile_pic);
        loc=view.findViewById(R.id.locvvv);
        work=view.findViewById(R.id.workText);
        edu=view.findViewById(R.id.highest_education);
        abt=view.findViewById(R.id.personText);
        gender=view.findViewById(R.id.gender);
        intrestss=view.findViewById(R.id.inuserintrest);
        likess=view.findViewById(R.id.inuserlikes);
        rgp=view.findViewById(R.id.radioSex);
        male=view.findViewById(R.id.radioBoth);
        female=view.findViewById(R.id.radioFemale);
        both=view.findViewById(R.id.radioMale);
        //rr=view.findViewById(selected);
        wder=SharePrefrences.readString(getActivity(),CONFIG.gender);
        if(wder.equals("male"))
            male.setChecked(true);
        if(wder.equals("female"))
            female.setChecked(true);
        if(wder.equals("both"))
            both.setChecked(true);
         rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 switch (i)
                 {
                     case R.id.radioMale:
                     {
                         Log.v("Both","both");
                         wder="both";
                         SharePrefrences.addString(getActivity(),CONFIG.gender,wder);

                         break;
                     }
                     case R.id.radioFemale:
                     {
                         Log.v("FeMale","female");
                         wder="female";
                         SharePrefrences.addString(getActivity(),CONFIG.gender,wder);

                         break;
                     }
                     case R.id.radioBoth:
                     {

                         Log.v("Male","male");
                         wder="male";
                         SharePrefrences.addString(getActivity(),CONFIG.gender,wder);

                         break;
                     }
                 }
             }
         });
        //Picasso.with(getActivity()).load(url).into(imageView);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=hobby.length;

                hobbyRecycler=new HobbyRecycler(hobby,count);
                recyclerView.setAdapter(hobbyRecycler);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                like.setVisibility(View.INVISIBLE);
                less.setVisibility(View.VISIBLE);
                count=4;
            }
        });
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hobbyRecycler=new HobbyRecycler(hobby,count);
                recyclerView.setAdapter(hobbyRecycler);
                less.setVisibility(View.INVISIBLE);
                like.setVisibility(View.VISIBLE);
            }
        });
        hobbyRecycler=new HobbyRecycler(hobby,count);


        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(hobbyRecycler);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hobbyRecycler);
        hobbyRecycler.notifyDataSetChanged();*/

        String path=SharePrefrences.readString(getActivity(), CONFIG.path);
//       Log.v("patttt",path);
        try {
            File f=new File(path, "pic1.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        //Picasso.with(getActivity()).load(String.valueOf(reference.child("pic1"))).into(imageView);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid= SharePrefrences.readString(getActivity(),"userid");
        //Log.v("USer id from shared prefrences is : ",userid);

        reference=FirebaseDatabase.getInstance().getReference().child("User_Info").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                            User user=new User();
                            user=dataSnapshot.getValue(User.class);
                            url=user.getPic1();
                            loc.setText(user.getLocation());
                            edu.setText(user.getEdu_details());
                            gender.setText(user.getGender());
                            abt.setText(user.getAbout_me());
                            work.setText(user.getWork_info());
                            likess.setText(user.getLikes());
                            intrestss.setText(user.getInterests());
                    // User artist=ar.getValue(User.class);
                       // Picasso.with(getActivity()).load(url).into(imageView);
                   // Toast.makeText(getActivity(), "rl is : "+String.valueOf(reference.child("pic1")), Toast.LENGTH_SHORT).show();
                    reference.removeEventListener(this);

                }

                    //url=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
