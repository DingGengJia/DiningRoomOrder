package util;

import android.content.Context;
import android.util.Log;

import com.gavin.diningroomorder.CustomApplication;

/**
 * Created by gavinding on 7/4/16.
 */
public class PreferenceUtil {

    public static void setCookie(Context ctx, String cookie) {
        Log.d("setSession", "cookies =" + cookie);
        ((CustomApplication) ctx).setsCookie(cookie);
    }

    public static String getCookie(Context ctx) {
        return ((CustomApplication) ctx).getsCookie();
    }

}
