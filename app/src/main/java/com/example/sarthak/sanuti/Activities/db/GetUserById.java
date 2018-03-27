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

public class GetUserById {
     User user;
     public User getUser(String id)
    {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(CONFIG.db_name).child(id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user =dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user;
    }
}
