package com.example.administrator.project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.format.Time;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private myDB md;
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        md=new myDB(context,"myDB",null,1);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Time time=new Time();time.setToNow();
        int y=time.year,m=time.month+1,d=time.monthDay,h=time.hour,min=time.minute,sec=time.second;
        if(sec!=0) return;
//        String str=""+y+"-"+m+"-"+d+"-"+h+"-"+min;
        String event_name;
        if(min==30){
            event_name=md.getCurrentActivity(y,m,d,h,0);
        }else
        {
            event_name=md.getCurrentActivity(y,m,d,h-1,30);
        }
        if(!event_name.equals("无    ")&& (min%30==0) ){
            int imgid=R.drawable.start_pic;
            long[] vibrates={0,500,200,500};
            Log.w("Notification","Do");
            Log.w("InNotification",event_name);
            Notification.Builder builder= new Notification.Builder(context);
            Intent bIntent = new Intent(context,MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context,0,bIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle("时段结束")
                    .setContentText(event_name)
                    .setTicker(event_name+"  ")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),imgid))
                    .setSmallIcon(imgid)
                    .setAutoCancel(true)
                    .setVibrate(vibrates)
//                    .setVibrate()
                    .setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification=builder.build();
            manager.notify(0,notification);
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
