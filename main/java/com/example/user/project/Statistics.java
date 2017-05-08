package com.example.administrator.project;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Statistics extends Activity {
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;
    private myDB md = new myDB(this,DB_NAME,null,DB_VERSION);
    private ArrayList<Pie> pieArrayList = new ArrayList<>();
    private String[] arr = new String[5010];
    private int[] pre = new int[5010];
    int[] pieColor = new int[5010];
    int len,year,month,day;
    private PieView pieView;
    ImageButton tri_left,tri_right;
    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tv=(TextView)findViewById(R.id.sta_date);
        btn=(Button)findViewById(R.id.sta_back);
        pieView = (PieView) findViewById(R.id.PieView);
        tri_left=(ImageButton) findViewById(R.id.tri_left);
        tri_right=(ImageButton)findViewById(R.id.tri_right) ;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) ;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        int m=month+1;
        tv.setText(""+m+"月"+day+"日");
        System.out.println(year+"   "+month+"   "+day);
        show(year,m,day);
        setOnClick();

    }
    public void show(int year,int month,int day){
        pieArrayList = new ArrayList<>();
        List<Event> data = md.getDayActivity(year,month,day);
        Log.w("pie_datasize",""+data.size());
        if(data != null && !data.isEmpty()) {
            HashMap<String,Integer> cnt = new HashMap<String, Integer>();
            HashMap<String,Integer> color = new HashMap<String, Integer>();
            for(int i = 0; i < data.size(); i++) {
                String name = data.get(i).getName();
                if(cnt.containsKey(name)) {
                    int pre = cnt.get(name);
                    cnt.remove(name);
                    cnt.put(name,pre+1);
                } else {
                    cnt.put(name,1);
                    color.put(name,data.get(i).getColor());
                }
            }

            len = 0;
            Iterator<String> it = cnt.keySet().iterator();
            while(it.hasNext()) {
                String key = (String) it.next();
                arr[len] = key;
                pre[len] = cnt.get(key);
                pieColor[len] = color.get(key);
                len++;
            }
        }
        else {
            arr[0] = "lazy";
            pre[0] = 1;
            pieColor[0] = getResources().getColor(R.color.blue);
            len = 1;
        }

        for (int i = 0; i < len; i++) {
            Pie pie = new Pie(pre[i], arr[i], pieColor[i]);
            pieArrayList.add(pie);
        }
        pieView.SetPie(pieArrayList);
    }
    public void setOnClick(){
        tri_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,day);
                calendar.add(Calendar.DATE,1);
                year=calendar.get(Calendar.YEAR);month=calendar.get(Calendar.MONTH);day=calendar.get(Calendar.DAY_OF_MONTH);
                int m=month+1;
                tv.setText(""+m+"月"+day+"日");
                show(year,m,day);
            }
        });
        tri_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,day);
                calendar.add(Calendar.DATE,-1);
                year=calendar.get(Calendar.YEAR);month=calendar.get(Calendar.MONTH);day=calendar.get(Calendar.DAY_OF_MONTH);
                int m=month+1;
                tv.setText(""+m+"月"+day+"日");
                show(year,m,day);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Statistics.this.finish();
            }
        });
    }

//    public  void onResume(){
//
//    }
}
