package com.example.sarthak.sanuti.Activities.db;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SARTHAK on 3/25/2018.
 */

public class ConnectionStatus {
      public static void accept_connection(String user_present, final String user_to,Context context)
    {

        try {
            new Databasehelper(context).insert_reject(user_to);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.v("By",user_present);
        final DatabaseReference jio=FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(user_present);
        jio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Connection con=dataSnapshot.getValue(Connection.class);
                String connection=con.getConnections();
                String request=con.getRequest();
                String[] conarray=connection.split(",");
                if(!connection.equals("conn")) {
                    if (new ConnectionStatus().matching_string(user_to, conarray))
                    {
                       // Log.v("df", "df")
                    }
                    else{
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sbr = new StringBuilder();
                    sb.setLength(0);
                    sbr.setLength(0);
                    sb.append(connection).append(",").append(user_to);
                    String has = sb.toString();
                    Log.v("NeW Connections are", has);
                    Map<String, Object> update = new HashMap<>();
                    update.put("connections", has);
                    jio.updateChildren(update);
                }
                }
                else
                {
                    Map<String, Object> update = new HashMap<>();
                    update.put("connections", user_to);
                    jio.updateChildren(update);
                }
                String rr[]= request.split(",");
                List<String> vio=new ArrayList<>();
                for(int i=0;i<rr.length;i++)
                {
                    vio.add(rr[i]);

                }
                for(int i=0;i<vio.size();i++)
                {
                    String ji=vio.get(i);
                    if(ji.equals(user_to))
                    {
                        vio.remove(i);
                    }
                }
                int size=vio.size();
                if(size>0)
                {
                    String aj=vio.toString().replace("[","").replace("]","");
                    Map<String, Object> update = new HashMap<>();
                    update.put("request", aj);
                    jio.updateChildren(update);
                }
                else
                {
                    Map<String, Object> update = new HashMap<>();
                    update.put("request", "request");
                    jio.updateChildren(update);
                }
                jio.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public static void defuse_connection(String user_present, final String user_to,Context context)
    {
        try {
            new Databasehelper(context).insert_reject(user_to);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        final DatabaseReference jio=FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(user_present);
        jio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Connection con=dataSnapshot.getValue(Connection.class);
                String connection=con.getConnections();
                String request=con.getRequest();
                String rr[]= request.split(",");
                List<String> vio=new ArrayList<>();
                for(int i=0;i<rr.length;i++)
                {
                    vio.add(rr[i]);

                }
                for(int i=0;i<vio.size();i++)
                {
                    String ji=vio.get(i);
                    if(ji.equals(user_to))
                    {
                        vio.remove(i);
                    }
                }
                int size=vio.size();
                if(size>0)
                {
                    Log.v("WOrkingnot","WORKING NOT");
                    String aj=vio.toString().replace("[","").replace("]","");
                    Map<String, Object> update = new HashMap<>();
                    update.put("request", user_to);
                    jio.updateChildren(update);
                }
                else
                {
                    Log.v("WOrking","WORKIN");
                    Map<String, Object> update = new HashMap<>();
                    update.put("request", "request");
                    jio.updateChildren(update);
                }
                jio.removeEventListener(this);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
public boolean matching_string(String user_to,String[] conarray)
{
    int i=0;
    for(i=0;i<conarray.length;i++)
    {
        if(conarray[i].equals(user_to))
        {
            return true;
        }
        else
            return false;
    }
    return false;
}

}
