package com.example.sarthak.sanuti.Activities.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sarthak.sanuti.R;

import org.w3c.dom.Text;

/**
 * Created by SARTHAK on 3/14/2018.
 */

public class HobbiesButtonGrid extends BaseAdapter {
    TextView b;
    private Context mContext;
   private String[] hobby;
   private Button[] button;

    public HobbiesButtonGrid(Context mContext, String[] hobby) {
        this.mContext = mContext;
        this.hobby=hobby;
    }

    @Override
    public int getCount() {
        return hobby.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.hobbiesbutton,null);
        b=view.findViewById(R.id.textView2);
       // b.setBackground(ContextCompat.getDrawable(mContext, R.drawable.outer_circle_clicked));
       // b.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
        b.setText(hobby[i]);
        return view;
    }
}
