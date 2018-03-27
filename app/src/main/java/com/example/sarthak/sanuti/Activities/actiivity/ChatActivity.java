package com.example.sarthak.sanuti.Activities.actiivity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sarthak.sanuti.Activities.adapters.Message;
import com.example.sarthak.sanuti.Activities.db.ChatSender;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    String chatid,chatname;
    String user_present;
    String[] aa;
    String id1,id2;
    String url;
    EditText e;
    double iid1,iid2;
    String message;
    String mainid;
    ListView l;
    String old_chat[]=new String[1];
    DatabaseReference reference;
    ArrayAdapter<String> jjo;
    StringBuilder sb=new StringBuilder();
    Button b;
    ImageView receiver;
    List<ChatSender> chatSenderList;
    ArrayList<String> jj=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        l=findViewById(R.id.cha);
        chatSenderList=new ArrayList<>();
        b=findViewById(R.id.button33);
        e=findViewById(R.id.editText2);
        toolbar=findViewById(R.id.toolchat);
        receiver=findViewById(R.id.recieverimage);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        setSupportActionBar(toolbar);
        url=getIntent().getStringExtra("url");
        Picasso.with(this).load(url).placeholder(R.drawable.icon_large).into(receiver);
        chatid=getIntent().getStringExtra("chatid");

        chatname=getIntent().getStringExtra("chatname");
        user_present= SharePrefrences.readString(this, CONFIG.userid);
        iid1=Double.parseDouble(user_present);
        iid2= Double.parseDouble(chatid);
        if(iid1>iid2)
        {
            mainid=String.valueOf(user_present)+String.valueOf(chatid);
        }
        else
        {
            mainid=String.valueOf(chatid)+String.valueOf(user_present);
        }
        aa= new String[]{chatid, user_present};
       id1= chatid+user_present;
        reference= FirebaseDatabase.getInstance().getReference(CONFIG.chat_info).child(mainid);
        //Toast.makeText(this, "hash is "+id, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, mainid, Toast.LENGTH_LONG).show();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }
    @Override
    protected void onResume() {
        super.onResume();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message= String.valueOf(e.getText());
                if(TextUtils.isEmpty(message))
                {
                    Toast.makeText(ChatActivity.this, "Put message first", Toast.LENGTH_SHORT).show();
                }
                else {
                    sb.append(message);
                    String send = sb.toString();
                    String ids = reference.push().getKey();
                    Date currentTime = Calendar.getInstance().getTime();
                    DateFormat df = new SimpleDateFormat(" HH:mm");
                    String reportDate = df.format(currentTime);
                    ChatSender sender=new ChatSender(mainid,reportDate,send,user_present);
                    reference.child(ids).setValue(sender);
                    //Toast.makeText(ChatApplication.this, send, Toast.LENGTH_SHORT).show();
                    sb.setLength(0);
                    e.setText("");
                    message=null;

                    //Toast.makeText(ChatApplicatio.this, "Message :  "+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //Toast.makeText(ChatActivity.this, "null", Toast.LENGTH_SHORT).show();

                }
                else {
                    chatSenderList.clear();
                    for(DataSnapshot arrr:dataSnapshot.getChildren())
                    {
                        ChatSender chatSender= arrr.getValue(ChatSender.class);
                        chatSenderList.add(chatSender);
                    }
                    Message message=new Message(ChatActivity.this,chatSenderList,user_present);
                    l.setAdapter(message);
                   /* String id=reference.push().getKey();
                    String add = dataSnapshot.getValue().toString();
                    if (add.equals("")) {
                        Toast.makeText(ChatApplication.this, "Empty is null", Toast.LENGTH_SHORT).show();
                    } else {
                        // String add = dataSnapshot.getValue().toString();
                        jj.add(add);
                        old_chat[0] = add;
                        jjo = new ArrayAdapter<String>(ChatApplication.this, android.R.layout.simple_spinner_item, jj);
                        l.setAdapter(jjo);

                    }*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
