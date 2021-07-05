package app.myapp.restuantadmin.VollySinglistion;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import app.myapp.restuantadmin.Model.User;


public class VollySing {

    private static final String SHARED_TOKEN="shared_token";
    private static final String NAME_SHARED="name_shared";
    private static final String EMAIL_SHARED="email_shared";
    private static final String KEY_ID="key_id";
    private static final String TOKEN_SHARED="token";


    private static VollySing vollySing_instanse;
    private static Context context;

    public VollySing(Context context) {
        this.context=context;
    }

    public static synchronized VollySing getInstanse(Context context){
        if (vollySing_instanse==null){
            vollySing_instanse=new VollySing(context);
        }

        return vollySing_instanse;
    }

    public void saveUser(User user){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_TOKEN,Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(TOKEN_SHARED,user.getToken());
        editor.apply();
    }
    public boolean is_Login(){

        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_TOKEN,Context.MODE_PRIVATE);

        return sharedPreferences.getString(TOKEN_SHARED,null)!=null;
    }
    public User getToken(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_TOKEN,Context.MODE_PRIVATE);

        return new User(sharedPreferences.getString(TOKEN_SHARED,null));

    }
    public void user_Logout(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_TOKEN,Context.MODE_PRIVATE);

        Editor editor = sharedPreferences.edit();
        editor.clear();
         editor.apply();

    }
}
