//package app.myapp.restuantadmin.ServerApi;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import app.myapp.restuantadmin.MainActivity;
//import app.myapp.restuantadmin.R;
//
//public class MyReceiver extends BroadcastReceiver {
//    int i;
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//
//
//        // get the bundles in the message
//        final Bundle bundle = intent.getExtras();
//        // check the action equal to the action we fire in broadcast,
//        if   (   intent.getAction().equalsIgnoreCase("com.example.Broadcast"))
//        //read the data from the intent
//
//           i =1;
//            Notification.Builder builder=new Notification.Builder(context)
//                    .setContentTitle("message")
//                    .setContentText(bundle.getString("msg"))
//
//                .setVibrate(new long[]{500,1000,500,1000,500})
//                .setSmallIcon(R.drawable.ic_home)
//                .setDefaults(Notification.DEFAULT_ALL);
//
//        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent intent1=new Intent(context, MainActivity.class);
//
//        TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
//
//        taskStackBuilder.addNextIntent(intent);
//
//        taskStackBuilder.addParentStack(MainActivity.class);
//
//        PendingIntent pendingIntent=taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentIntent(pendingIntent);
//
//        builder.addAction(R.drawable.ic_home,"read",pendingIntent);
//
//        manager.notify(i,builder.build());
//        i++;
//
//    }
//
//
//
//    }
//
