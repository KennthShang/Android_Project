package com.example.administrator.project;

/**
 * Created by Administrator on 2016/11/27 0027.
 */


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2016/11/26.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<Event> list;

    public MyAdapter(Context context, List<Event> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list == null) return 0;
        else return list.size();
    }

    @Override
    public Object getItem(int i) {
        if(list == null) return null;
        else return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;

        if(view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.eventColor = (TextView) convertView.findViewById(R.id.event_color_item);
            viewHolder.eventName =(TextView) convertView.findViewById(R.id.event_name_item);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventColor.setBackgroundColor(list.get(i).getColor());
        viewHolder.eventName.setText(list.get(i).getName());

        return convertView;
    }

    private class ViewHolder {
        public TextView eventColor;
        public TextView eventName;
    }
}

