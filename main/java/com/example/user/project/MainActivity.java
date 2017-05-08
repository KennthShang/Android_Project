package com.example.administrator.project;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;
    private myDB md = new myDB(this,DB_NAME,null,DB_VERSION);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        buttonAction();


        Intent intent=new Intent(this,MyService.class);
        startService(intent);

        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        y = t.year;
        m = t.month;
        d = t.monthDay;
        h = t.hour;

        data1.clear();
        resetCalendar();
        setText();
        reload();
//        tickToc();
        listViewAction();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    PopupMenu popupMenu;
    Menu menu;
    ListView lv0, lv1;                                          // lv0对应左边的ListView，lv1对应右边的ListView
    TextView month, day;                                        // 显示的月份和日期空间
    ImageButton bt0,bt1, bt2, bt3,bt4,tri_up,tri_down;                                  // bt0是左上角菜单键，bt2是右下角左边的清空，bt3是右下角的擦除,bt4是保存
    Button bt8;                                                  // bt8是全部事件类别，用来跳转页面
    int y, m, d, h;                                              // 年月日小时的整数型
    //myDB myHelper;
    final List<Map<String, Object>> data1 = new ArrayList<>();  // 右边ListView的data数据类型
    final List<Map<String, Object>> data2 = new ArrayList<>();  // 左边ListView的data数据类型
    private PopupWindow popupWindow;                           // 菜单类型（可以忽略）
    private ButtonAdapter simpleAdapter;                        // 左边ListView的适配器

    void findView() {
        lv0 = (ListView) findViewById(R.id.LV0);
        lv1 = (ListView) findViewById(R.id.LV1);

        bt1= (ImageButton)findViewById(R.id.Btn1);
        bt2 = (ImageButton) findViewById(R.id.Btn2);
        bt3 = (ImageButton) findViewById(R.id.Btn3);
        bt0 = (ImageButton) findViewById(R.id.Btn0);
        bt4 = (ImageButton) findViewById(R.id.saveBtn);
        month = (TextView) findViewById(R.id.month);
        day = (TextView) findViewById(R.id.day);
        tri_down=(ImageButton)findViewById(R.id.tri_down);
        tri_up=(ImageButton)findViewById(R.id.tri_up);
        bt8 = (Button) findViewById(R.id.Btn8);
    }                                       // 绑定函数
    void buttonAction() {
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "按钮被点击", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,EventList.class);
                MainActivity.this.startActivity(intent);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Statistics.class);
                startActivity(i);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalendar(R.color.origin);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleAdapter.onLoad(md,y,m+1,d);
                Toast.makeText(MainActivity.this,"已保存",Toast.LENGTH_SHORT).show();
            }
        });
        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupWindow_view = getLayoutInflater().inflate(R.layout.menu, null,false);
                popupWindow = new PopupWindow(popupWindow_view, 200, ActionBar.LayoutParams.MATCH_PARENT, true);
                popupWindow.setAnimationStyle(R.style.PopupAnimation);
                popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
                popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                        return false;
                    }
                });
                Button save=(Button)popupWindow_view.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        simpleAdapter.onLoad(md,y,m+1,d);
                    }
                });
            }
        });
        tri_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DAY_OF_MONTH,Integer.parseInt("1"));
                y=calendar.get(Calendar.YEAR);
                m=calendar.get(Calendar.MONTH);
                d=calendar.get(Calendar.DAY_OF_MONTH);
                resetCalendar();
                setText();
                reload();
            }
        });
        tri_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DATE,Integer.parseInt("-1"));
                y=calendar.get(Calendar.YEAR);
                m=calendar.get(Calendar.MONTH);
                d=calendar.get(Calendar.DAY_OF_MONTH);
                resetCalendar();
                setText();
                reload();
            }
        });

    }                                   // 按钮监听函数
    public void reload() {
        data1.clear();
        Map<String, Object> temp;
        List<Integer> color_arr=new ArrayList<>();
        List<Event> list=md.queryAllDifference(y,m+1,d);
        Set<Event> set=new HashSet<>();
        Log.w("list_size",""+list.size());
        for(Event e:list){
            if(!set.contains(e)) {
                temp = new LinkedHashMap<>();
                String name = e.getName();
                Integer color = e.getColor();
                Log.w("color", "" + color);
                temp.put("thing", name);
                data1.add(temp);
                color_arr.add(color);
                set.add(e);
            }
        }
        final SpecialAdapter specialAdapter = new SpecialAdapter(this, data1, R.layout.item1,
                new String[]{"thing", "color"}, new int[]{R.id.thing},color_arr);
        lv1.setAdapter(specialAdapter);

        List<String> Name_left=new ArrayList<>(),Name_right=new ArrayList<>();
        List<Integer> Color_left=new ArrayList<>(),Color_right=new ArrayList<>();
        for(int i=0;i<24;i++){
            Name_left.add("");Name_right.add("");
            Color_left.add(R.color.origin);Color_right.add(R.color.origin);
        }
        List<Event> list2=md.queryAll(y,m+1,d);
        for(Event e:list2){
            String time =e.getStartTime();
            int[] time_ = new int[5];
            Integer color = e.getColor();String name = e.getName();
            int len = time.length();
            int idx = 0;
            for(int i = 0; i < 5; i++) time_[i] = 0;
            for(int i = 0; i < len; i++) {
                if(time.charAt(i)!='-') time_[idx] = 10*time_[idx] + (time.charAt(i)-'0');
                else idx++;
            }
            if(time_[4]==30){
                Color_right.set(time_[3],color);
                Name_right.set(time_[3],name);
            }
            else{
                Color_left.set(time_[3],color);
                Name_left.set(time_[3],name);
            }
            simpleAdapter.setLists(Name_left,Color_left,Name_right,Color_right);
            lv0.setAdapter(simpleAdapter);
        }
    }                                 // 刷新listview函数

    public void setText() {
        month.setText(String.valueOf(m+1) + " 月");
        day.setText(String.valueOf(d) + " 日");
        Log.w("date",y+""+(m+1)+""+d+"");
    }                                // 设置日期函数
	
    public void setCalendar(Integer color) {
        data2.clear();
        for (int i = 0; i <= 24; i++) {
            if (i < 10) {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("time", "0" + i + ":00");
                data2.add(temp);
            } else {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("time", i + ":00");
                data2.add(temp);
            }
        }
        simpleAdapter = new ButtonAdapter(this, data2, R.layout.item0,
                new String[]{"time"}, new int[]{R.id.time}, color,"");
        lv0.setAdapter(simpleAdapter);
    }               // 设置左边的listview函数，colorFlag表示颜色

    public void resetCalendar() {
        data2.clear();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("time", "0" + i + ":00");
                data2.add(temp);
            } else {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("time", i + ":00");
                data2.add(temp);
            }
        }
        simpleAdapter = new ButtonAdapter(this, data2, R.layout.item0,
                new String[]{"time"}, new int[]{R.id.time}, R.color.origin,"");
        lv0.setAdapter(simpleAdapter);
    }                           // 重置左边的Listview函数
	
    public void listViewAction() {
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "按钮被点击", Toast.LENGTH_SHORT).show();
                Log.i("test", "click1");
                TextView textView=(TextView)(lv1.getChildAt(position).findViewById(R.id.thing));
                String thing_name=textView.getText().toString();
                Integer color=md.getColorByName(thing_name);
                simpleAdapter.setColor(color,thing_name);
            }
        });
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)(lv1.getChildAt(position).findViewById(R.id.thing));
                String thing_name=textView.getText().toString();
                md.delete(thing_name);		//这里还欠一个对话框
                reload();
                return true;
            }
        });
        Log.i("test", "click2");
    }                          // 右边ListView的监听函数，用来得到是第几个颜色被选中


//    public void tickToc(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    while(true) {
//                        Thread.sleep(1000);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("name", "随意");
//                        Time time = new Time();
//                        time.setToNow();
//                        String _time = time.toString();
//                        bundle.putString("time", _time);
//                        Intent intent = new Intent("refreshWidget");
//                        Log.w("in RUN", "send");
//                        intent.putExtras(bundle);
//                        sendBroadcast(intent);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Time time=new Time();time.setToNow();y=time.year;m=time.month;d=time.monthDay;
        reload();
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.user.project/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.example.user.project/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
}
