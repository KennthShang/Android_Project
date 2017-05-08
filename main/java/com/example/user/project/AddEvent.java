package com.example.administrator.project;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddEvent extends AppCompatActivity {
    private int btnSize;
    private int[] colorId = new int[20];
    private Button confirmBtn, backBtn;
    private Button[] colorBtn = new Button[20];
    private EditText nameEditText, timeEditText;
    private int colorChoose;
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;
    private myDB md = new myDB(this,DB_NAME,null,DB_VERSION);
    private String NOT_SET = "not-set";
    private ImageButton tri_left,tri_right;
    private TextView tv;
    int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        colorChoose = R.color.blue;
        bindView();
        setColorButton();
        setOhterButton();
        Time time=new Time();time.setToNow();
        y=time.year;m=time.month;d=time.monthDay;
    }

    private void bindView() {
        backBtn = (Button) findViewById(R.id.color_back);
        confirmBtn = (Button) findViewById(R.id.color_confirm);
        nameEditText = (EditText) findViewById(R.id.event_name_add);
        timeEditText = (EditText) findViewById(R.id.time_add);
        tv=(TextView)findViewById(R.id.color_tv);
        tri_left=(ImageButton)findViewById(R.id.color_tri_left);
        tri_right=(ImageButton)findViewById(R.id.color_tri_right);
        int[] btnId = {R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,
                R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.ten,
                R.id.eleven,R.id.twelve,R.id.thirten,R.id.fourteen,R.id.fifteen,
                R.id.sixteen};
        btnSize = btnId.length;
        for(int i = 0; i < btnSize; i++) {
            colorBtn[i] = (Button) findViewById(btnId[i]);
        }
    }

    private void setColorButton() {
        for(int i = 0; i < btnSize; i++) {
            final int finalI = i;
            colorBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorBtn[finalI].setText("picked");
                    for(int j=0;j<btnSize;j++){
                        if(finalI!=j){
                            colorBtn[j].setText("");
                        }
                    }
                    switch(finalI) {
                        case 0:
                            colorChoose = R.color.red_blue1;
                            break;
                        case 1:
                            colorChoose = R.color.red_green1;
                            break;
                        case 2:
                            colorChoose = R.color.green_blue1;
                            break;
                        case 3:
                            colorChoose = R.color.red_green3;
                            break;
                        case 4:
                            colorChoose = R.color.green_blue3;
                            break;
                        case 5:
                            colorChoose = R.color.red_blue3;
                            break;
                        case 6:
                            colorChoose = R.color.red_blue2;
                            break;
                        case 7:
                            colorChoose = R.color.red_green2;
                            break;
                        case 8:
                            colorChoose = R.color.green_blue2;
                            break;
                        case 9:
                            colorChoose = R.color.red_green5;
                            break;
                        case 10:
                            colorChoose = R.color.green_blue5;
                            break;
                        case 11:
                            colorChoose = R.color.red_blue5;
                            break;
                        case 12:
                            colorChoose = R.color.red_blue4;
                            break;
                        case 13:
                            colorChoose = R.color.red_green4;
                            break;
                        case 14:
                            colorChoose = R.color.green_blue6;
                            break;
                        case 15:
                            colorChoose = R.color.green_blue5;
                            break;
                    }
                }
            });
        }
    }

    private void setOhterButton() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = nameEditText.getText().toString();
                if(TextUtils.isEmpty(eventName)) {
                    Toast.makeText(AddEvent.this,"名字不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    int month=m+1;
                    String _Time = y+"-"+month+"-"+d;
                    Event event = new Event(eventName,getResources().getColor(colorChoose),_Time,NOT_SET);
                    int returnCode = md.canInsert(event);
                    if (returnCode == -1) {
                        Toast.makeText(AddEvent.this,"名字重复，请检查",Toast.LENGTH_SHORT).show();
                    } else if (returnCode == 0) {
                        Toast.makeText(AddEvent.this,"颜色重复，请检查",Toast.LENGTH_SHORT).show();
                    } else {
                        md.insert(event);
                        AddEvent.this.finish();
                    }
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEvent.this.finish();
            }
        });
        tri_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DATE,-1);
                y=calendar.get(Calendar.YEAR);m=calendar.get(Calendar.MONTH);d=calendar.get(Calendar.DAY_OF_MONTH);
                setText();
            }
        });
        tri_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(y,m,d);
                calendar.add(Calendar.DATE,1);
                y=calendar.get(Calendar.YEAR);m=calendar.get(Calendar.MONTH);d=calendar.get(Calendar.DAY_OF_MONTH);
                setText();
            }
        });
    }
    public void setText(){
        int month=m+1;
        tv.setText(""+month+"月"+d+"日");
    }
}

