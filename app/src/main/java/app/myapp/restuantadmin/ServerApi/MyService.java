package app.myapp.restuantadmin.ServerApi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MyService extends Service {

    SqLiteData sqLiteData=new SqLiteData(this);
   public static boolean tf=true;
    public MyService() {
    }


    @Override
    public void onCreate() {


        Toast.makeText(getBaseContext(),"onCreate",Toast.LENGTH_SHORT).show();



        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       // Toast.makeText(getBaseContext(),"onStartCommand2",Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (tf)

                    try {
                        Thread.sleep(5000);
                       getNoty();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }).start();




        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"onDestroy() ",Toast.LENGTH_SHORT).show();
        super.onDestroy();

    }

    private void getNoty(){


        ArrayList<String> arrayList=sqLiteData.getData();


        try {

            ShortcutBadger.applyCount(getBaseContext(),arrayList.size());

        }catch (Exception e){

        }






    }
}