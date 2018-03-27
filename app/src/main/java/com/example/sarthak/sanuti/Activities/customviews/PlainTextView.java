package com.example.sarthak.sanuti.Activities.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.sarthak.sanuti.Activities.utils.TypeFaceUtils;

/**
 * Created by SARTHAK on 3/11/2018.
 */

public class PlainTextView extends android.support.v7.widget.AppCompatTextView

{
    public PlainTextView(Context context) {
        super(context);
        this.setTypeface(TypeFaceUtils.getMuseoSans_500(context));
    }

    public PlainTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(TypeFaceUtils.getMuseoSans_500(context));
    }

    public PlainTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(TypeFaceUtils.getMuseoSans_500(context));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
