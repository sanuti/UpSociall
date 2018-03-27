package com.example.sarthak.sanuti.Activities.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SARTHAK on 3/18/2018.
 */

public class Databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="UpSociall";
    Context context;
    public static final String TABLE_NAME="user_info";
    public static final String USER_REJECTED="user_rejected";//to store the data of the request;

    public Databasehelper(Context context) {
        super(context,DATABASE_NAME,null, 2);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(ID TEXT PRIMARY KEY UNIQUE,NAME TEXT,LASTNAME TEXT,DOB TEXT,EDU_DETAILS TEXT,EMAIL TEXT,INTEREST TEXT,LATI TEXT,LIKE TEXT,LOCATION TEXT,LONGI TEXT,TRUST_SCORE TEXT,WORK_INFO TEXT,ABOUT_ME TEXT,CONNECTIONS TEXT,HOLD TEXT,REJECTED TEXT,GENTOSHOW TEXT,REQUEST TEXT);");
        sqLiteDatabase.execSQL("create table " + USER_REJECTED + "(ID TEXT PRIMARY KEY UNIQUE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context, "In on upgrade", Toast.LENGTH_SHORT).show();
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.close();
    }


    public void insert_student(String ID,String NAME,String LASTNAME,String DOB,String EDU_DETAILS ,String EMAIL ,String INTEREST ,String LATI ,String LIKE ,String LOCATION ,String LONGI ,String TRUST_SCORE ,String WORK_INFO ,String ABOUT_ME,String CONNECTIONS,String HOLD,String REJECTED,String GENTOSHOW,String REQUEST)
    {
        ContentValues values=new ContentValues();
        values.put("ID",ID);
        values.put("NAME",NAME);
        values.put("LASTNAME",LASTNAME);
        values.put("DOB",DOB);
        values.put("EDU_DETAILS",EDU_DETAILS);
        values.put("EMAIL",EMAIL);
        values.put("INTEREST",INTEREST);
        values.put("LATI","00000.00000");
        values.put("LIKE",LIKE);
        values.put("LOCATION",LOCATION);
        values.put("LONGI","0000.00000");
        values.put("TRUST_SCORE",TRUST_SCORE);
        values.put("WORK_INFO",WORK_INFO);
        values.put("ABOUT_ME",ABOUT_ME);
        values.put("CONNECTIONS",CONNECTIONS);
        values.put("HOLD",HOLD);
        values.put("REJECTED",REJECTED);
        values.put("GENTOSHOW",GENTOSHOW);
        values.put("REQUEST",REQUEST);
        this.getWritableDatabase().insertOrThrow(TABLE_NAME,"",values);
         this.close();
    }
    public void insert_reject(String id)
    {
        ContentValues cv=new ContentValues();
        cv.put("ID",id);
        this.getWritableDatabase().insertOrThrow(USER_REJECTED,"",cv);
    }
    public int match_reject(String id)
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT ID FROM '"+USER_REJECTED+"' where ID=?",new String[]{id});
        if(cursor.moveToNext())
        {
            return 1;
        }
        return 0;
    }
    public void delete_data(String where)
    {
        this.getWritableDatabase().delete(TABLE_NAME,"ID='"+where+"'",null);
        this.close();
    }
    public void update_student(String tochange,String change,String userid)
    {
        this.getWritableDatabase().execSQL("UPDATE '"+TABLE_NAME+"' SET '"+tochange+"'='"+change+"' WHERE ID='"+userid+"' ");
        this.close();
    }
    public String list_data(int value)
    {
        Cursor cursor=this.getReadableDatabase().rawQuery("SELECT *FROM '"+TABLE_NAME+"'",null);
        while (cursor.moveToNext())
        {
            return cursor.getString(value);

        }
        this.close();
        return null;
    }
    public void drop_table(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

}
