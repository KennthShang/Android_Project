package com.example.user.project;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by user on 2016/11/26.
 */
public class ButtonAdapter extends SimpleAdapter {

    private LayoutInflater mInflater;
    List<Map<String, Object>> ss;
    int colorFlag;
    int[] color = {R.color.origin, R.color.black, R.color.yellow, R.color.red};
    public ButtonAdapter(Context context, List<Map<String, Object>> items, int resource, String[] from, int[] to, int flag) {
        super(context, items, resource, from, to);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ss = items;
        colorFlag = flag;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item0, null);


        final Button btn4 = (Button) convertView.findViewById(R.id.Btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            int flag = 0;
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    btn4.setBackgroundResource(color[colorFlag]);
                    flag = 1;
                }
                else{
                    flag = 0;
                    btn4.setBackgroundResource(R.color.origin);
                }

            }
        });
        final Button btn5 = (Button) convertView.findViewById(R.id.Btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            int flag = 0;
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    btn5.setBackgroundResource(color[colorFlag]);
                    flag = 1;
                }
                else{
                    flag = 0;
                    btn5.setBackgroundResource(R.color.origin);
                }

            }
        });
        final TextView t = (TextView) convertView.findViewById(R.id.time);
        t.setText((String)ss.get(position).get("time"));
        return convertView;

    }

}
