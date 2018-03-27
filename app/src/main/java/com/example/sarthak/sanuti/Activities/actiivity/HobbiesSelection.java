package com.example.sarthak.sanuti.Activities.actiivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.sarthak.sanuti.Activities.adapters.HobbiesButtonGrid;
import com.example.sarthak.sanuti.Activities.db.Likes;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HobbiesSelection extends AppCompatActivity {
    JSONObject response, profile_pic_data, profile_pic_url;
    GridView gridView;
    DatabaseReference reference;
    ListView lidst;
    String id;
    Button b1,b2,b3,b4;
    List<String> kok=new ArrayList<>();
ArrayAdapter<String> dsa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies_selection);
        // lidst=findViewById(R.id.list);
       // gridView=findViewById(R.id.dff);
        String hobby="Bikng,Riding,Bloging,Writing";
        id = SharePrefrences.readString(HobbiesSelection.this,CONFIG.userid);
        reference=FirebaseDatabase.getInstance().getReference("User_Info").child(id);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("interests",hobby);
        reference.updateChildren(childUpdates);

        //user.setHobbies(hobby);
        b1=findViewById(R.id.button7);

        reference= FirebaseDatabase.getInstance().getReference("User_Info");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(HobbiesSelection.this, "Id i s"+user.getUserId(), Toast.LENGTH_SHORT).show();
                //reference.child(user.getUserId()).setValue(user);
                SharePrefrences.addBoolean(HobbiesSelection.this, CONFIG.hobby_select,true);
                startActivity(new Intent(HobbiesSelection.this,HomeActivity.class));
                finish();
            }
        });
        // Add Buttons


       // String jsondata = intent.getStringExtra("uri");



        //Log.w("Jsondata", jsondata);
       // Toast.makeText(this,"your uri should be :  " +jsondata, Toast.LENGTH_SHORT).show();

           // Picasso.with(this).load(getIntent().getExtras().getString("uri")).into(user_picture);
    }


}
