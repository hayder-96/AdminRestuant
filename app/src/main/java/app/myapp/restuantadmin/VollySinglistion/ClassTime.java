package app.myapp.restuantadmin.VollySinglistion;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.myapp.restuantadmin.MainActivity;
import app.myapp.restuantadmin.Model.InformUser;
import app.myapp.restuantadmin.ServerApi.Serverapi;
import app.myapp.restuantadmin.VollyLibary.VollyLib;

public  class ClassTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
         int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user


        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        Toast.makeText(getActivity(),currentTime+"/",Toast.LENGTH_SHORT).show();

        String s=hourOfDay+":"+minute;

        SimpleDateFormat currentTim = new SimpleDateFormat("HH:mm");

        try {
            Date date=currentTim.parse(s);
            SimpleDateFormat currentTi = new SimpleDateFormat("HH:mm",Locale.getDefault());

           String ss=currentTi.format(date);
           upDate();
            dataMessage(ss);

            Toast.makeText(getActivity(),ss+"//",Toast.LENGTH_SHORT).show();
            if (currentTime.equals(ss)){
                Toast.makeText(getActivity(),"yes yes yes",Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }



    private void upDate(int id,String open) {


        final String token = VollySing.getInstanse(getActivity()).getToken().getToken();







        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("open",open);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Serverapi.OPCL+id, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();

                    } else {

                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
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
        VollyLib.getInstance(getActivity()).addRequest(jsonObjectRequest);


    }




















    private void dataMessage(String o) {






        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Serverapi.GET_OPCL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {


                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject screen = jsonArray.getJSONObject(i);

                        int id=screen.getInt("id");
                        Toast.makeText(getActivity(),id+"idid",Toast.LENGTH_SHORT).show();
                        upDate(id,o);
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

                return map;
            }

        };
        VollyLib.getInstance(getActivity()).addRequest(jsonObjectRequest);


    }


    private void upDate() {


        final String token = VollySing.getInstanse(getActivity()).getToken().getToken();







        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("open","07:00");

        } catch (Exception e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Serverapi.OPCL+4, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    if (response.getBoolean("success")) {
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();

                        //progressDialog.dismiss();

                    } else {

                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
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
        VollyLib.getInstance(getActivity()).addRequest(jsonObjectRequest);


    }
}

