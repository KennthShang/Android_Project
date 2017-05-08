package com.example.administrator.project;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;
    private static myDB md;
    public static Set<Integer> ids=new HashSet<>();
    static String name="",time="";
    List<Integer> list;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        if (name.equals("")) {
            views.setTextViewText(R.id.appwidget_text, widgetText);
        }
        else {
            views.setTextViewText(R.id.appwidget_text,"当前:"+name+"       离结束:"+time+"分钟");
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        md= new myDB(context,DB_NAME,null,DB_VERSION);
        // There may be multiple widgets active, so update all of them

        Intent click=new Intent(context,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,0,click,0);
        RemoteViews rv=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
        rv.setOnClickPendingIntent(R.id.appwidget_text,pi);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            md.insertWidgetID(appWidgetId);
            ids.add(appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        if(md!=null) {
            list = md.getWidgetIDs();
            for (Integer i : list) {
                ids.add(i);
            }
        }
    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        md= new myDB(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    public void onReceive(Context context,Intent intent){
        super.onReceive(context,intent);
        if(intent.getAction().equals("refreshWidget")) {
            Log.w("OnReceive","YES");
            Bundle bundle=intent.getExtras();
            String _name=bundle.getString("name");
            String _time=bundle.getString("time");
            name=_name;time=_time;

            int IDs[]=new int[ids.size()],cur=0;
            Iterator<Integer> iterator=ids.iterator();
            while(iterator.hasNext()){
                IDs[cur++]=iterator.next();
            }
            if(cur>0)
                 Log.w("IDSIZE:",""+IDs[cur-1]);

            onUpdate(context, AppWidgetManager.getInstance(context), IDs);
            }
//            Time time=new Time();time.toString();

    }
}

