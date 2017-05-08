package com.example.administrator.project;

/**
 * Created by Administrator on 2016/11/27 0027.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class myDB extends SQLiteOpenHelper{
    private static final String DB_NAME = "myDB";
    private static final String TABLE_NAME = "event";
    private static final int DB_VERSION = 1;
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_COLOR = "color";
    private static final String ATTRIBUTE_START_TIME = "startTime";
    private static final String ATTRIBUTE_COMMENT = "comment";
    private static final String NOT_SET = "not-set";
    private static final String WIDGET_TABLE = "WidgetIDS";
    public myDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY, "
                + ATTRIBUTE_NAME + " TEXT, "
                + ATTRIBUTE_COLOR + " INTEGER, "
                + ATTRIBUTE_START_TIME + " TEXT, "
                + ATTRIBUTE_COMMENT + " TEXT)";
        String create_widget_id = "create table if not exists WidgetIDS ( _id integer  )";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(create_widget_id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private int[] time = new int[5];
    private void strToTime(String str) {
        int len = str.length();
        int idx = 0;
        for(int i = 0; i < 5; i++) time[i] = 0;
        for(int i = 0; i < len; i++) {
            if(str.charAt(i)!='-') time[idx] = 10*time[idx] + (str.charAt(i)-'0');
            else idx++;
        }
    }

    private boolean sameDay(int _year, int _month, int _day, String date) {
        if(date.equals(NOT_SET)) return false;
        strToTime(date);
        if(time[0]==_year && time[1]==_month && time[2]==_day) return true;
        else return false;
    }

    private boolean sameActivity(int _year, int _month, int _day, int _hour, int _min, String date) {
        if(date.equals(NOT_SET)) return false;
        strToTime(date);
        if(time[0]==_year && time[1]==_month && time[2]==_day && time[3]==_hour && time[4]==_min) return true;
        else return false;
    }

    public void insert(Event event) {
        String name = event.getName();
        int color = event.getColor();
        String startTime = event.getStartTime();
        String comment = event.getComment();

        ContentValues cv = new ContentValues();
        cv.put(ATTRIBUTE_NAME,name);
        cv.put(ATTRIBUTE_COLOR,color);
        cv.put(ATTRIBUTE_START_TIME,startTime);
        cv.put(ATTRIBUTE_COMMENT,comment);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public void insert(String _name, int _color, int _year, int _month, int _day, int _hour, int _min, String _comment) {
        String _startTime = _year+"-"+_month+"-"+_day+"-"+_hour+"-"+_min;

        ContentValues cv = new ContentValues();
        cv.put(ATTRIBUTE_NAME,_name);
        cv.put(ATTRIBUTE_COLOR,_color);
        cv.put(ATTRIBUTE_START_TIME,_startTime);
        cv.put(ATTRIBUTE_COMMENT,_comment);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

//    public void insertByList(List<String> nameLeft,List<String> nameRight,List<Integer> )

    public void delete(Event event) {
        String name = event.getName();
        String startTime = event.getStartTime();

        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ATTRIBUTE_NAME + " = ? AND " + ATTRIBUTE_START_TIME + " = ?";
        String[] whereArgs = new String[]{name,startTime};
        db.delete(TABLE_NAME,whereClause,whereArgs);
        db.close();
    }
    public void delete(int _year,int _month,int _day){
        String _Time = _year+"-"+_month+"-"+_day;
        SQLiteDatabase db = getWritableDatabase();
        String str="delete from "+TABLE_NAME+" where "+ATTRIBUTE_START_TIME +" like '"+_Time+"_%'";
//        String whereClause =ATTRIBUTE_START_TIME + " = ?";
//        String[] whereArgs = new String[]{_Time+"%"};
//        db.delete(TABLE_NAME,whereClause,whereArgs);
        db.execSQL(str);
        db.close();
    }
    public void delete(String _name){
        SQLiteDatabase db=getWritableDatabase();
        String str="delete from "+TABLE_NAME+" where "+ATTRIBUTE_NAME +" is '"+_name+"'";
        db.execSQL(str);
        db.close();
    }

    public int canInsert(Event event) {
        SQLiteDatabase db = getReadableDatabase();
        String name = event.getName();

        String time=event.getStartTime();
        Log.w("canInsert:",time);
        String whereClause = ATTRIBUTE_NAME + " = ? and "+ATTRIBUTE_START_TIME+" = ?";
        String[] whereArgs = new String[] {name,time};
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                whereClause,whereArgs,null,null,null);
        cursor.moveToFirst();
        if(cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return -1; // 优先返回名字重复，-1
        }

        String color = event.getColor()+"";
        whereClause = ATTRIBUTE_COLOR + " = ? and "+ATTRIBUTE_START_TIME+" = ?";
        whereArgs = new String[] {color,time};
        cursor = db.query(TABLE_NAME,
                new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                whereClause,whereArgs,null,null,null);
        cursor.moveToFirst();
        if(cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return 0; // 0表示返回颜色重复
        }
        else {
            cursor.close();
            db.close();
            return 1; // 1表示可以插入
        }
    }

    public List<Event> queryAll() {
        List<Event> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                null,null,null,null,null);
        while(cursor.moveToNext()) {
            Event event = new Event();
            event.setName(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR)));
            event.setStartTime(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_START_TIME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_COMMENT)));
            data.add(event);
        }
        cursor.close();
        db.close();
        return data;
    }

    public List<Event> queryAll(int _year,int _month,int _day) {
        List<Event> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str=""+_year+"-"+_month+"-"+_day;
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                ATTRIBUTE_START_TIME+" like ?",new String[]{str+"_%"},null,null,null);
        while(cursor.moveToNext()) {
            Event event = new Event();
            event.setName(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR)));
            event.setStartTime(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_START_TIME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_COMMENT)));
            data.add(event);
        }
        cursor.close();
        db.close();
        return data;
    }

    public List<Event> queryAllDifference(int _year,int _month,int _day) {
        List<Event> data = new ArrayList<>();
        HashSet<String> vis = new HashSet<>(); // 用于判重
        String str=""+_year+"-"+_month+"-"+_day;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                ATTRIBUTE_START_TIME+" like ?",new String[]{str+'%'},null,null,null);
        while(cursor.moveToNext()) {
            Event event = new Event();
            event.setName(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR)));
            event.setStartTime(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_START_TIME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_COMMENT)));
            if(vis.add(event.getName())) {
                data.add(event);
            }
        }
        cursor.close();
        db.close();
        return data;
    }

    public List<Event> getDayActivity(int _year, int _month, int _day) {
        List<Event> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str=_year+"-"+_month+"-"+_day;
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                ATTRIBUTE_START_TIME+" like ?",new String[]{str+"_%"},null,null,null);
        while(cursor.moveToNext()) {
            Event event = new Event();
            event.setName(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR)));
            event.setStartTime(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_START_TIME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_COMMENT)));
            if(sameDay(_year,_month,_day,event.getStartTime()))
                data.add(event);
        }
        cursor.close();
        db.close();
        return data;
    }

    public int getColor(int _year, int _month, int _day, int _hour, int _min) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                null,null,null,null,null);
        while(cursor.moveToNext()) {
            Event event = new Event();
            event.setName(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME)));
            event.setColor(cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR)));
            event.setStartTime(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_START_TIME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(ATTRIBUTE_COMMENT)));
            if(sameActivity(_year,_month,_day,_hour,_min,event.getStartTime()))  return event.getColor();
        }
        cursor.close();
        db.close();
        return R.color.blue;    // 默认颜色
    }

    public Integer getColorByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                null,null,null,null,null);
        String name_in_DB;
        Integer color;
        while(cursor.moveToNext()) {
            name_in_DB=cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME));
            color=cursor.getInt(cursor.getColumnIndex(ATTRIBUTE_COLOR));
            if(name.equals(name_in_DB)){
                return color;
            }
        }
        return R.color.origin;
    }

    public List<Integer> getWidgetIDs(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(WIDGET_TABLE,new String[]{"_id"},null,null,null,null,null,null);
        List<Integer> list=new ArrayList<>();
        while(cursor.moveToNext()){
            Integer id=cursor.getInt(cursor.getColumnIndex("_id"));
            list.add(id);
        }
        db.close();
        return list;
    }
    public void insertWidgetID(int id){
        SQLiteDatabase database=getWritableDatabase();
        String str="insert into "+WIDGET_TABLE+" values( "+ id + ")";
        database.execSQL(str);
        database.close();
    }
    public String getCurrentActivity(int _y,int _m,int _d,int _h,int _min){
        String str,rtn="";
        if(_min<30){
            str=""+_y+"-"+_m+"-"+_d+"-"+_h+"-"+'0';
        }
        else str=""+_y+"-"+_m+"-"+_d+"-"+_h+"-30";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME
                ,new String[]{ATTRIBUTE_NAME,ATTRIBUTE_COLOR,ATTRIBUTE_START_TIME,ATTRIBUTE_COMMENT},
                ATTRIBUTE_START_TIME + " like? ",new String[]{str+"%"},null,null,null);
        if(cursor==null) Log.w("TAG","no result for :"+ str);
        while(cursor.moveToNext()) {
            Log.w("TAG","result :"+ str+" is : "+rtn);
            rtn=cursor.getString(cursor.getColumnIndex(ATTRIBUTE_NAME));
        }
        Log.w("TAG","result :"+ str+" is : "+rtn);
        if(rtn.equals("")) rtn="无    ";
        cursor.close();
        db.close();
        return rtn;
    }
}