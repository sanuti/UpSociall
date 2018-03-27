package com.example.sarthak.sanuti.Activities.db;

import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SARTHAK on 3/25/2018.
 */

public class GetConnection  {
    static String connected;
    static DatabaseReference reference;
    static String userrid[];
    public static String[] getConnection(String user_present)
    {
        reference= FirebaseDatabase.getInstance().getReference(CONFIG.connections_info).child(user_present);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Connection connection=dataSnapshot.getValue(Connection.class);
                connected=connection.getConnections();
               userrid=connected.split(",");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userrid;
    }
}
