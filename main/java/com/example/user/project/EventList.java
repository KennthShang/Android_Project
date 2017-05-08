package com.example.administrator.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EventList extends AppCompatActivity {
    private List<Event> data;
    private MyAdapter myAdapter;
    private ListView listView;
    private Button addBtn, toGraphBtn,back;
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;
    private ImageButton tri_left,tri_right;
    private TextView date;
    private myDB md;
    int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Time time=new Time();time.setToNow();
        y=time.year;m=time.month;d=time.monthDay;
        md = new myDB(this,DB_NAME,null,DB_VERSION);
        bindView();
        setButton();
        int month=m+1;
        date.setText(month+"月"+d+"日");
        initListView(y,month,d);
    }

    private void bindView() {
        listView = (ListView) findViewById(R.id.event_list);
        addBtn = (Button) findViewById(R.id.add_button_event_list);
        toGraphBtn = (Button) findViewById(R.id.graph_event_list);
        tri_left=(ImageButton)findViewById(R.id.el_tri_left);
        tri_right=(ImageButton)findViewById(R.id.el_tri_right);
        date=(TextView)findViewById(R.id.el_tv);
        back=(Button)findViewById(R.id.el_back);
    }

    private void setButton() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventList.this,AddEvent.class);
                EventList.this.startActivity(intent);
            }
        });

        toGraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventList.this,Statistics.class);
                EventList.this.startActivity(intent);
            }
        });
        tri_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DATE,-1);
                y=calendar.get(Calendar.YEAR);m=calendar.get(Calendar.MONTH);d=calendar.get(Calendar.DAY_OF_MONTH);
                int month=m+1;
                date.setText(month+"月"+d+"日");

                initListView(y,m+1,d);
            }
        });
        tri_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DATE,1);
                y=calendar.get(Calendar.YEAR);m=calendar.get(Calendar.MONTH);d=calendar.get(Calendar.DAY_OF_MONTH);
                int month=m+1;
                date.setText(month+"月"+d+"日");
                initListView(y,m+1,d);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventList.this.finish();
            }
        });
    }

    private void initListView(int y,int m,int d) {
        data = md.queryAllDifference(y,m,d);
        myAdapter = new MyAdapter(EventList.this,data);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        initListView(y,m+1,d);
        super.onResume();
    }
}

