package com.example.sarthak.sanuti.Activities.actiivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sarthak.sanuti.Activities.adapters.HobbyRecycler;
import com.example.sarthak.sanuti.Activities.asynchronous.DownloadImage;
import com.example.sarthak.sanuti.Activities.db.Connection;
import com.example.sarthak.sanuti.Activities.db.Databasehelper;
import com.example.sarthak.sanuti.Activities.db.Likes;
import com.example.sarthak.sanuti.Activities.db.Picture;
import com.example.sarthak.sanuti.Activities.db.User;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    CallbackManager mcallbackManager;
    private FirebaseAuth mAuth;

    ProgressDialog progress;
    StringBuilder sb = new StringBuilder();
    Likes likes1;
    public static final String TAG = "FACEBOOK";
    DatabaseReference reference;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                }, 10);
                return;
            }
            else
            {
               // storeImage();
            }
        } else {
            //storeImage();
        }
        getSupportActionBar().hide();
        mcallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
       // dh=new Databasehelper(this);
        final LoginButton loginButton = findViewById(R.id.login_button);
      //  reference = FirebaseDatabase.getInstance().getReference("User_Info");
        loginButton.setReadPermissions("public_profile", "email", "user_location", "user_birthday", "user_likes", "user_work_history", "user_education_history", "user_photos");
        loginButton.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                progress.setMessage("Getting You IN");
                progress.setCancelable(false);
                progress.show();

                FirebaseUser user = mAuth.getCurrentUser();

                loginButton.setVisibility(View.INVISIBLE);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                final User user = new User();
                                //Log.d(response.getJSONObject());
                                object = response.getJSONObject();

                                user.setUserId(object.optString("id"));
                                user.setDob(object.optString("birthday"));
                                user.setEmail(object.optString("email"));
                                user.setFirstName(object.optString("first_name"));
                                user.setLastName(object.optString("last_name"));
                                user.setGender(object.optString("gender"));
                                final String propic = "https://graph.facebook.com/" + object.optString("id") + "/picture?width=400&height=400";
                                user.setPic1(propic);
                                new downloadpic().execute("https://firebasestorage.googleapis.com/v0/b/sanuti-77939.appspot.com/o/download%20(2).jpg?alt=media&token=36d291a3-e798-45cf-ab9a-220b6e2f601b");
                               // new DownloadImage().execute(propic,CONFIG.Profile_Pic,"pic_1.jpg");
                                user.setPic2("null");
                                user.setPic3("null");
                                user.setPic4("null");
                                user.setPic5("null");
                                user.setPic6("null");
                                user.setLati("null");
                                user.setLongi("null");
                                user.setGentoshow("both");
                                user.setAbout_me("Click here to update about yourself");
                                SharePrefrences.addString(getApplicationContext(), CONFIG.userid,user.getUserId());
                                // final String likes=object.optString("likes");


                                // user.setPic("https://graph.facebook.com/" + object.optString("id") + "/picture?width=300&height=300");
                                if (object.optJSONObject("location") != null)
                                    user.setLocation(object.optJSONObject("location").optString("name"));
                                else
                                    user.setLocation("Click to set your Location");
                                if (object.optJSONArray("work") != null)
                                    user.setWork_info(getWork(object.optJSONArray("work")));
                                else
                                    user.setWork_info("Update Your Work");
                                user.setTrust_score("0");
                                String Dob = object.optString("birthday");
                                if (object.optJSONArray("education") != null) {

                                    user.setEdu_details(getEducation(object.optJSONArray("education")));

                                } else {
                                    user.setEdu_details("Click to update Education");
                                }



                                new GraphRequest(
                                        loginResult.getAccessToken(),
                                        "/" + user.getUserId() + "/likes",
                                        null,
                                        HttpMethod.GET,
                                        new GraphRequest.Callback() {
                                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                                JSONObject jsonObject = response.getJSONObject();
                                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                                                    //Likes like = new Likes();
                                                    //like.setId((long) (count + 1));
                                                    //like.setLike_name(jsonObject1.optString("name"));
                                                    //likes.add(jsonObject1.optString("name"));
                                                    //Toast.makeText(LoginActivity.this, like.getLike_name(), Toast.LENGTH_SHORT).show();
                                                    String name = jsonObject1.optString("name");
                                                    //likes.add(name);
                                                    if (i < jsonArray.length() - 1)
                                                        sb.append(name).append(",");
                                                    else
                                                        sb.append(name);


                                                }
                                                String likkk = sb.toString();
                                                user.setLikes(likkk);
                                                String id = user.getUserId();
                                                user.setInterests("null");
                                                reference=FirebaseDatabase.getInstance().getReference(CONFIG.db_name).child(user.getUserId());
                                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists())
                                                        {
                                                            Log.v("cersei","Exist moving");
                                                            User user1=new User();
                                                            user1=dataSnapshot.getValue(User.class);
                                                            write_db(user1);
                                                            Intent intent = new Intent(LoginActivity.this, HobbiesSelection.class);
                                                            SharePrefrences.addBoolean(LoginActivity.this,CONFIG.sucess_login,true);
                                                            SharePrefrences.addString(LoginActivity.this,CONFIG.gender,"both");
                                                            //intent.putExtra("id", id);
                                                            intent.putExtra("gg", user);
                                                            startActivity(intent);
                                                            finish();

                                                            progress.dismiss();
                                                        }
                                                        else
                                                        {
                                                            Log.v("jamie","Not found");
                                                            DatabaseReference db2=FirebaseDatabase.getInstance().getReference(CONFIG.db_name);

                                                            db2.child(user.getUserId()).setValue(user);
                                                            write_db(user);
                                                            //dh.getWritableDatabase();
                                                            // dh.insert_student(user.getUserId(),user.getFirstName(),user.getLastName(),user.getDob(),user.getEdu_details(),user.getEmail(),user.getInterests(),"0.000000",user.getLikes(),user.getLocation(),"0.000000",user.getTrust_score(),user.getWork_info(),user.getAbout_me(),user.getConnections(),user.getHold(),user.getRejected(),user.getGentoshow(),user.getRequest());
                                                            //Toast.makeText(LoginActivity.this, "Likes is "+likkk, Toast.LENGTH_SHORT).show();
                                                            //reference.child(user.getUserId()).child("Likes").setValue(likes1);
                                                            DatabaseReference io;
                                                            io=FirebaseDatabase.getInstance().getReference(CONFIG.connections_info);
                                                            Connection connection=new Connection();
                                                            connection.setConnections("conn");
                                                            connection.setHold("hold");
                                                            connection.setId(user.getUserId());
                                                            connection.setReject("reject");
                                                            connection.setRequest("request");
                                                            io.child(connection.getId()).setValue(connection);
                                               Intent intent = new Intent(LoginActivity.this, HobbiesSelection.class);
                                                SharePrefrences.addBoolean(LoginActivity.this,CONFIG.sucess_login,true);
                                                SharePrefrences.addString(LoginActivity.this,CONFIG.gender,"both");
                                                //intent.putExtra("id", id);
                                                //intent.putExtra("gg", user);
                                                startActivity(intent);
                                                finish();

                                                            progress.dismiss();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });


                                            }
                                        }
                                ).executeAsync();

                                // Application code

                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,about,birthday,picture.width(700).height(700),email,first_name,gender,last_name,location,middle_name,work,education,albums");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


// ...


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK1
        try {
            mcallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    class downloadpic extends AsyncTask<String,Void,Void>
    {
        String url;
        @Override
        protected Void doInBackground(String... strings) {
            url=strings[0];
            URL ur= null;
            try {
                ur = new URL(url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) ur.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream ss=httpURLConnection.getInputStream();
                bmp= BitmapFactory.decodeStream(ss);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 10);
                    return;
                }
                else
                {
                    storeImage();
                }
            } else {
                storeImage();
            }
        }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try{
                   // storeImage();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
            }
        }
    }
public void storeImage()
{
    String root= Environment.getExternalStorageDirectory().getAbsolutePath();
    File myDir=new File(root+"/UpSociall/profile_pic");
    SharePrefrences.addString(LoginActivity.this, CONFIG.path,root+"/UpSociall/profile_pic");
    Log.v("Haivng directory", String.valueOf(myDir));
    myDir.mkdirs();


    String name="pic1.jpg";
    File file=new File(myDir,name);
    file.mkdirs();
    if (file.exists()) file.delete();
    try {
        FileOutputStream out=new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG,50,out);
        out.flush();
        out.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    catch (Exception e)
    {

    }
}


    private void updateUI(FirebaseUser user) {


        //Toast.makeText(this, profile.getClass(), Toast.LENGTH_SHORT).show();

        // Toast.makeText(this, "You are logged in as facebook", Toast.LENGTH_SHORT).show();
       /* Intent intent=new Intent(Mai.this,AccountActivity.class);
        startActivity(intent);
        finish();*/
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        try {

            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());

                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        catch (Exception e)
        {

        }
    }

    private String getEducation(JSONArray jsonArray) {
        String education[];

        String[] arr = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            arr[i] = jsonArray.optJSONObject(i).optJSONObject("school").optString("name");
        }
        education = Arrays.toString(arr).replace("[", "").replace("]", "").split(",");

        return Arrays.toString(arr).replace("[", "").replace("]", "");
    }

    private String getWork(JSONArray jsonArray) {
        try {
            JSONObject jsonObject = jsonArray.optJSONObject(0);
            //Pref.putString(Config.work_info, jsonObject.optJSONObject("employer").optString("name"));
            return jsonObject.optJSONObject("employer").optString("name");
        } catch (NullPointerException e) {
            //Logger.d("No Work");
            return "";
        }

    }
    public void write_db(User user)
    {

        Databasehelper dh=new Databasehelper(LoginActivity.this);

        dh.getWritableDatabase();
        dh.insert_student(user.getUserId(),user.getFirstName(),user.getLastName(),user.getDob(),user.getEdu_details(),user.getEmail(),user.getInterests(),user.getLati(),user.getLikes(),user.getLocation(),user.getLongi(),user.getTrust_score(),user.getWork_info(),user.getAbout_me(),user.getConnections(),user.getHold(),user.getRejected(),user.getGentoshow(),user.getRequest());
       // dh.close();
    }
}
