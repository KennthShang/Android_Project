package com.example.user.project;


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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        buttonAction();

        data1.clear();
        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("thing", "姓名");
        data1.add(temp);
        temp = new LinkedHashMap<>();
        temp.put("thing", "打打打");
        data1.add(temp);
        temp = new LinkedHashMap<>();
        temp.put("thing", "去去去");
        data1.add(temp);
        final SpecialAdapter simpleAdapter = new SpecialAdapter(this, data1, R.layout.item1,
                new String[]{"thing", "color"}, new int[]{R.id.thing}, new int[]{R.color.black, R.color.yellow, R.color.red});
        lv1.setAdapter(simpleAdapter);


        setText();
        resetCalendar();
        listViewAction();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    PopupMenu popupMenu;
    Menu menu;
    ListView lv0, lv1;                                          // lv0对应左边的ListView，lv1对应右边的ListView
    TextView month, day;                                        // 显示的月份和日期空间
    ImageButton bt0, bt2, bt3;                                  // bt0是左上角菜单键，bt2是右下角左边的清空，bt3是右下角的擦除
    Button bt8;                                                  // bt8是全部事件类别，用来跳转页面
    int y, m, d, h;                                              // 年月日小时的整数型
    //myDB myHelper;
    final List<Map<String, Object>> data1 = new ArrayList<>();  // 右边ListView的data数据类型
    final List<Map<String, Object>> data2 = new ArrayList<>();  // 左边ListView的data数据类型
    private PopupWindow popupWindow;                           // 菜单类型（可以忽略）

    void findView() {
        lv0 = (ListView) findViewById(R.id.LV0);
        lv1 = (ListView) findViewById(R.id.LV1);

        bt2 = (ImageButton) findViewById(R.id.Btn2);
        bt3 = (ImageButton) findViewById(R.id.Btn3);
        bt0 = (ImageButton) findViewById(R.id.Btn0);

        month = (TextView) findViewById(R.id.month);
        day = (TextView) findViewById(R.id.day);

        bt8 = (Button) findViewById(R.id.Btn8);
    }                                       // 绑定函数
    void buttonAction() {
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "按钮被点击", Toast.LENGTH_SHORT).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCalendar();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalendar(1);
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
            }
        });
    }                                   // 按钮监听函数
    public void reload() {

    }                                 // 刷新右边的listview函数
    public void setText() {
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        y = t.year;
        m = t.month;
        d = t.monthDay;
        h = t.hour;
        month.setText(String.valueOf(m) + " 月");
        day.setText(String.valueOf(d) + " 日");
    }                                // 设置日期函数
    public void setCalendar(int colorFlag) {
        colorFlag += 1;
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
        final ButtonAdapter simpleAdapter = new ButtonAdapter(this, data2, R.layout.item0,
                new String[]{"time"}, new int[]{R.id.time}, colorFlag);
        lv0.setAdapter(simpleAdapter);

    }               // 设置左边的listview函数，colorFlag表示颜色
    public void resetCalendar() {
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
        final ButtonAdapter simpleAdapter = new ButtonAdapter(this, data2, R.layout.item0,
                new String[]{"time"}, new int[]{R.id.time}, 0);
        lv0.setAdapter(simpleAdapter);
    }                           // 重置左边的Listview函数
    public void listViewAction() {

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "按钮被点击", Toast.LENGTH_SHORT).show();
                Log.i("test", "click1");
                setCalendar(position);
            }
        });
        Log.i("test", "click2");


    }                          // 右边ListView的监听函数，用来得到是第几个颜色被选中


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.project/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.project/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
