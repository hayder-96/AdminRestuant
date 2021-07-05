package app.myapp.restuantadmin.ServerApi;

import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Serverapi {


    public static final String ROOT_URL = "https://warm-escarpment-42253.herokuapp.com/api/";
    public static final String REGISTER_URL = ROOT_URL + "Register";
    public static final String LOGIN_URL = ROOT_URL + "Login";

    public static final String MAINMENU = ROOT_URL + "food";
    public static final String MAINMENU_UPDATE = ROOT_URL + "updatefood";

    public static final String PARTFOOD = ROOT_URL + "foodpart";
    public static final String MENUPART_UPDATE = ROOT_URL + "updatepart";
    public static final String GETPART = ROOT_URL + "getpartfood/";







    public static final String GETREQUEST_NO = ROOT_URL + "getfooduserrnot";
    public static final String GETREQUEST_YES= ROOT_URL + "getfooduseryes";
    public static final String GETREQUEST_FOOD_USER= ROOT_URL + "getfu/";
    public static final String UPDATE_DELIVERY= ROOT_URL + "upuser/";

    public static final String DELETE_USER= ROOT_URL + "deleteuser/";





    public static final String GETNOTY_NO= ROOT_URL + "getnoty";



    public static final String UPDATE_NOTY= ROOT_URL + "upnotyy/";



    public static final String INSERT_NOTY= ROOT_URL + "usernoty";





    public static final String OPCL= ROOT_URL + "opcl/";

    public static final String GET_OPCL= ROOT_URL + "getopcl";









}


