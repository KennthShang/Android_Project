package com.example.user.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpecialAdapter extends SimpleAdapter {
    private int[] colors;

    public SpecialAdapter(Context context, List<Map<String, Object>> items, int resource, String[] from, int[] to, int[] color) {
        super(context, items, resource, from, to);
        colors = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position;
        view.setBackgroundResource(colors[position]);
        ImageButton imgbt1 = (ImageButton) view.findViewById(R.id.Btn6);
        imgbt1.setBackgroundResource(colors[colorPos]);
        ImageButton imgbt2 = (ImageButton) view.findViewById(R.id.Btn7);
        imgbt2.setBackgroundResource(colors[colorPos]);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
        layout.setBackgroundResource(colors[colorPos]);

        //GradientDrawable myGrad = (GradientDrawable)view.getBackground();
        //myGrad.setColor(colors[position]);
        //Log.i("test", colors[position]+"");


        return view;
    }
}
