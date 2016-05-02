package net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import bean.MealList;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import logic.LoginRequest;
import logic.MealRequest;
import logic.SaveUserMealRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class BusinessManager implements IBusinessManager {

    private static final String LOG_TAG = "BusinessManager";
    static BusinessManager businessManager;
    static NetworkManager networkManager;
    static Context mContext;


    public static BusinessManager getInstance(Context ctx) {
        if (businessManager == null) {
            businessManager = new BusinessManager();
        }
        mContext = ctx;
        networkManager = NetworkManager.getInstance(mContext);

        return businessManager;
    }

    public BusinessManager() {
    }

    private void sendGetRequest(BusinessRequest request, HttpEntity entity, IBusinessDeleage deleage) {
        networkManager.sendGetRequst(request, entity, deleage);
    }

    private void sendPostRequest(BusinessRequest request, HttpEntity entity, IBusinessDeleage deleage) {
        networkManager.sendPostRequst(request, entity, deleage);
    }

    @Override
    public void requestLogin(String name, String psd, IBusinessDeleage delegate) {
        LoginRequest request = new LoginRequest(name, psd);
        sendPostRequest(request, null, delegate);
    }

    @Override
    public void requestMeal(String date, IBusinessDeleage delegate) {
        for (int i : MealList.getMealType()) {
            MealRequest request = new MealRequest(date, i);
            sendPostRequest(request, null, delegate);
        }
    }

    @Override
    public void requestSaveUserMeal(String date, String sel, IBusinessDeleage delegate) {
        //date:2016/5/3 0:00:00
        //sel:5846a5f4-7332-4606-a1bf-b61f275e6d02,3,1|d39dc5bc-4a34-44f0-923a-5008f60a49c9,3,1|8b704247-bfe1-4768-b45d-5b2ec55562cf,10,1|57bf1760-afee-4628-83bd-5f09157f793c,5,1
        Log.d(LOG_TAG, "date:" + date);
        Log.d(LOG_TAG, "sel:" + sel);
        SaveUserMealRequest request = new SaveUserMealRequest(date, sel);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sel", sel);
            jsonObject.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendPostRequest(request, entity, delegate);


//        String content = "sel=" + sel + "&date=" + date;
//        Log.d(LOG_TAG, "content:" + content);
//        try {
//            content = "sel=" + URLEncoder.encode(sel, "UTF-8") + "&date=" + URLEncoder.encode(date, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.d(LOG_TAG, "encoded content:" + content);
//        HttpEntity entity = null;
//        try {
//            entity = new StringEntity(content);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        sendPostRequest(request, entity, delegate);
    }
}
