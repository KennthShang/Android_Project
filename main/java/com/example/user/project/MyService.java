package com.example.administrator.project;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;

    private Thread thread;
    private int flag;
    private myDB md;
    public MyService() {
        flag=0;
        md=new myDB(this,DB_NAME,null,DB_VERSION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(flag==0) {
                        Thread.sleep(1000);
                        Bundle bundle = new Bundle();
                        Time time = new Time();
                        time.setToNow();
                        int y=time.year,m=time.month+1,d=time.monthDay,h=time.hour,min=time.minute;
                        String _name=md.getCurrentActivity(y,m,d,h,min);
                        bundle.putString("name", _name);
//                        String _time = ""+y+"/"+m+"/"+d+"   "+h+":"+min;
                        String _time;
                        if(min>=30) _time=""+(60-min);
                        else _time=""+(30-min);
                        bundle.putString("time", _time);
                        Intent intent = new Intent("refreshWidget");
                        Log.w("in RUN", "send");
                        intent.putExtras(bundle);
                        sendBroadcast(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy(){
        flag=1;
    }
}
