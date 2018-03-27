package com.example.sarthak.sanuti.Activities.db;

import android.util.Log;

import com.example.sarthak.sanuti.Activities.adapters.UserRecyclerSearch;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SARTHAK on 3/25/2018.
 */

public class SwipeStatus {
 public static void rejectUser(final String userRejectBy, final String userRejectdTo)
 {
     final DatabaseReference uou= FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(userRejectBy);
     uou.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             Connection coni;
             coni=dataSnapshot.getValue(Connection.class);
             final String reject=coni.getReject();
             if (!reject.equals("reject")) {

                         StringBuilder sb = new StringBuilder();
                         StringBuilder sbr = new StringBuilder();
                         sb.setLength(0);
                         sbr.setLength(0);
                         sb.append(reject).append(",").append(userRejectdTo);
                         String has = sb.toString();
                         Log.v("RejectHas", has);
                         Map<String, Object> update = new HashMap<>();
                         update.put("reject", has);
                         uou.updateChildren(update);
                         Log.v("REjectAfter", has);


             } else {

                         Map<String, Object> update = new HashMap<>();
                         update.put("reject",userRejectdTo);
                         uou.updateChildren(update);




             }

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
 }
 public static void userRequest(final String requestedBy, final String requestedTo)
 {
     final DatabaseReference db = FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(requestedTo);
     db.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             Connection con = dataSnapshot.getValue(Connection.class);
             final String conlist = con.getRequest();
             Log.v("conlist", conlist);
             if (!conlist.equals("request")) {


                         StringBuilder sb = new StringBuilder();
                         StringBuilder sbr = new StringBuilder();
                         sb.setLength(0);
                         sbr.setLength(0);
                         sb.append(conlist).append(",").append(requestedBy);
                         String has = sb.toString();
                         Log.v("Has", has);
                         Map<String, Object> update = new HashMap<>();
                         update.put("request", has);
                         db.updateChildren(update);
                         Log.v("After", has);


             } else {

                         Map<String, Object> update = new HashMap<>();
                         update.put("request", requestedBy);
                         db.updateChildren(update);


             }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
 }
}
