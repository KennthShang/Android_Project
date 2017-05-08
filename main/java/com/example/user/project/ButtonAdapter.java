package com.example.administrator.project;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by user on 2016/11/26.
 */
public class ButtonAdapter extends SimpleAdapter {

    private LayoutInflater mInflater;
    List<Map<String, Object>> ss;
    int Color;
    String Name;
    List<Integer> left=new ArrayList<>();
    List<Integer> right=new ArrayList<>();
    List<String> left_name=new ArrayList<>();
    List<String> right_name=new ArrayList<>();
//    int[] color = {R.color.origin, R.color.black, R.color.yellow, R.color.red};
    public ButtonAdapter(Context context, List<Map<String, Object>> items, int resource, String[] from, int[] to, Integer color,String name) {
        super(context, items, resource, from, to);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ss = items;
        Color=color;
        Name=name;
        for(int i=0;i<25;i++){
            left.add(R.color.origin);
            right.add(R.color.origin);
            left_name.add("");right_name.add("");
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item0, null);
        final Button btn4 = (Button) convertView.findViewById(R.id.Btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
//            int flag = 0;
            @Override
            public void onClick(View v) {
                if(left_name.get(position).equals("")){
                    if(Color==R.color.origin)
                        btn4.setBackgroundResource(Color);
                    else
                        btn4.setBackgroundColor(Color);
                    left.set(position,Color);
                    left_name.set(position,Name);
//                    Log.w("isOnclick",""+btn4.callOnClick());
//                    flag = 1;
                }
                else{
                    btn4.setBackgroundResource(R.color.origin);
                    left.set(position,R.color.origin);
                    left_name.set(position,"");
                }
            }
        });
        final Button btn5 = (Button) convertView.findViewById(R.id.Btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
//            int flag = 0;
            @Override
            public void onClick(View v) {
                if(right_name.get(position).equals("")){
                    if(Color==R.color.origin)
                        btn5.setBackgroundResource(Color);
                    else
                        btn5.setBackgroundColor(Color);
                    right.set(position,Color);
                    right_name.set(position,Name);
                }
                else{
                    btn5.setBackgroundResource(R.color.origin);
                    right.set(position,R.color.origin);
                    right_name.set(position,"");
                }
            }
        });
        final TextView t = (TextView) convertView.findViewById(R.id.time);
        t.setText((String)ss.get(position).get("time"));
        if(left.get(position).equals(R.color.origin))
            btn4.setBackgroundResource(left.get(position));
        else btn4.setBackgroundColor(left.get(position));
        if(right.get(position).equals(R.color.origin))
            btn5.setBackgroundResource(right.get(position));
        else btn5.setBackgroundColor(right.get(position));
        return convertView;
    }
    void setColor(Integer color,String name){
        Color=color;
        Name=name;
    }
    void onLoad(myDB md,int _y,int _m,int _d){
//        Time t = new Time();
//        t.setToNow(); // 取得系统时间。
        int y = _y;
        int m = _m;//t.month+1;
        int d = _d;//t.monthDay;
        md.delete(y,m,d);
        for(int i=0;i<24;i++){
            if(!left_name.get(i).equals("")){
                md.insert(left_name.get(i),left.get(i),y,m,d,i,0,"");
            }
            if(!right_name.get(i).equals("")){
                md.insert(right_name.get(i),right.get(i),y,m,d,i,30,"");
            }
        }
    }

    void setLists(List<String> name_left,List<Integer> color_left,List<String> name_right,List<Integer> color_right)
    {
        left_name=name_left;left=color_left;
        right_name=name_right;right=color_right;
    }
}