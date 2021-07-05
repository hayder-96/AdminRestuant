package app.myapp.restuantadmin.ServerApi;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.R;
import app.myapp.restuantadmin.VollyLibary.VollyLib;
import app.myapp.restuantadmin.VollySinglistion.VollySing;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Notifaction extends Service {



    SqLiteData sqLiteData=new SqLiteData(this);


    @Override
    public void onCreate()
    {

        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true)

                    try {

                        Thread.sleep(5000);
                        dataMessage();
                    }catch (Exception e){

                    }
            }
        }).start();

            return START_STICKY;
        }


    @Override
    public void onDestroy()
    {
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"IBinder",Toast.LENGTH_SHORT).show();
        return null;
    }

    
    public void ShowNotificationn(String name,int id)
    {

        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setContentTitle(name)
                    .setContentText("لديك طلب")
                    .setVibrate(new long[]{500, 1000, 500, 1000, 500})
                    .setSmallIcon(R.drawable.ic_home)
                    .setDefaults(Notification.DEFAULT_ALL);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            taskStackBuilder.addParentStack(MainActivity.class);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.addAction(R.drawable.ic_home, "مشاهدته", pendingIntent);
            notificationManager.notify(new Random().nextInt(), builder.build());



            insert(id);
            upDate(id);

    }








    private void dataMessage() {

        final String token = VollySing.getInstanse(this).getToken().getToken();

        ArrayList<InformUser> arrayList1 = new ArrayList<>();



        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GETNOTY_NO, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);
                        InformUser item_menu_user = new InformUser();
                        item_menu_user.setId(screen.getInt("id"));
                        item_menu_user.setName(screen.getString("name"));
                        item_menu_user.setLatitude(screen.getString("latidtude"));
                        item_menu_user.setLongtude(screen.getString("longtude"));
                        item_menu_user.setDelivery(screen.getString("delivery"));


                        arrayList1.add(item_menu_user);


                    }


                    for (InformUser i:arrayList1) {

                        sqLiteData.insertData(i.getName());
                        ShowNotificationn(i.getName(),i.getId());

                    }








                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                map.put("Authorization", "Bearer  " + token);
                return map;
            }

        };
        VollyLib.getInstance(this).addRequest(jsonObjectRequest);


    }

















    private void upDate(int id) {


        final String token = VollySing.getInstanse(this).getToken().getToken();







        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("noty","yes");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Serverapi.UPDATE_NOTY+id, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getBaseContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();

                    } else {

                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                        //  progressDialog.dismiss();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                map.put("Authorization", "Bearer " + token);
                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);


    }

















    private void insert(int i) {


        final String token = VollySing.getInstanse(this).getToken().getToken();







        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("noty","yes");
            jsonObject.put("user_id",i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Serverapi.INSERT_NOTY, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getBaseContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();

                    } else {

                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
                        //  progressDialog.dismiss();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                map.put("Accept", "application/json");
                map.put("Authorization", "Bearer " + token);
                return map;
            }

        };
        VollyLib.getInstance(getBaseContext()).addRequest(jsonObjectRequest);


    }




}
