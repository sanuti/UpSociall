package com.example.sarthak.sanuti.Activities.utils;

import android.content.Context;
import android.graphics.Typeface;



/**
 * Created by SARTHAK on 3/11/2018.
 */

public class TypeFaceUtils {
    public static  Typeface getMuseoSans_500(Context context) {
        String font_name = "MuseoSans_500.otf";
        return Typeface.createFromAsset(context.getAssets(), "font/" + font_name);


    }

  /*  public Typeface get_Museosans_100() {
        String font_name = "Museo 100.otf";r
        return Typeface.createFromAsset(this.BasegetAssets(), "fonts/" + font_name);


    }


    public Typeface get_Museosans_300() {
        String font_name = "Museo 300.otf";
        return Typeface.createFromAsset(this.getAssets(), "fonts/" + font_name);


    }


    public Typeface get_MuseoSans_900() {
        String font_name = "museo-sans-900.otf";
        return Typeface.createFromAsset(this.getAssets(), "fonts/" + font_name);


    }

    public static Typeface get_MuseoSans_700() {
        String font_name = "museo-sans-700.otf";
        return Typeface.createFromAsset(Base.getAssets(), "fonts/" + font_name);


    }*/
}
