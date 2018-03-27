package com.example.sarthak.sanuti.Activities.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.example.sarthak.sanuti.R;

/**
 * Created by SARTHAK on 3/12/2018.
 */

public class SharePrefrences {

    final static String PREFS_NAME = "UpSociall";
    final static String DEFAULT=null;

    public static void addString(Context context, String key, String value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static void addBoolean(Context context,String key,boolean value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean readBoolean(Context context,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key,false);

    }
    public static String readString(Context context,String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPref.getString(key,DEFAULT);
    }
}
