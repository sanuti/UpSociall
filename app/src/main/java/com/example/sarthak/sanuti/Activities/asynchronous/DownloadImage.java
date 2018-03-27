package com.example.sarthak.sanuti.Activities.asynchronous;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.StoreImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SARTHAK on 3/27/2018.
 */

public class DownloadImage extends AsyncTask<String,Void,Void> {
    String url,folder_name,image_name;
    Bitmap bitmap;
    Image image;
    @Override
    protected Void doInBackground(String... strings) {
        url=strings[0];
        folder_name=strings[1];
        image_name=strings[2];
        Log.v("UR",url);
        try {
            URL urlto=new URL("https://firebasestorage.googleapis.com/v0/b/sanuti-77939.appspot.com/o/download%20(2).jpg?alt=media&token=36d291a3-e798-45cf-ab9a-220b6e2f601b");
            HttpURLConnection httpURLConnection= (HttpURLConnection) urlto.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream is=httpURLConnection.getInputStream();
            bitmap= BitmapFactory.decodeStream(is);


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
        new StoreImage().storeImage(bitmap,folder_name,image_name);

    }
}
