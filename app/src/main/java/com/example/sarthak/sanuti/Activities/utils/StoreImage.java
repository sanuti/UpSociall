package com.example.sarthak.sanuti.Activities.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SARTHAK on 3/27/2018.
 */

public class StoreImage {
    public void storeImage(Bitmap bitmap,String folder_name, String image_name)
    {
        String root= Environment.getExternalStorageDirectory().getAbsolutePath();
        File mydir=new File(root+"/"+ CONFIG.Media_Directory+"/"+CONFIG.Image_Directory+"/"+folder_name);
        mydir.mkdirs();
        File file=new File(mydir,image_name);
        if(file.exists())
        {

        }
        else
        {
            try {
            FileOutputStream fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
