package net;

import android.content.Context;

import bean.MealList;
import logic.LoginRequest;
import logic.MealRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class BusinessManager implements IBusinessManager {

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

    private void sendGetRequest(BusinessRequest request, IBusinessDeleage deleage) {
        networkManager.sendGetRequst(request, deleage);
    }

    private void sendPostRequest(BusinessRequest request, IBusinessDeleage deleage) {
        networkManager.sendPostRequst(request, deleage);
    }

    @Override
    public void requestLogin(String name, String psd, IBusinessDeleage delegate) {
        LoginRequest request = new LoginRequest(name,psd);
        sendPostRequest(request, delegate);
    }

    @Override
    public void requestMeal(String date, IBusinessDeleage delegate) {
        for (int i : MealList.getMealType())
        {
            MealRequest request = new MealRequest(date, i);
            sendPostRequest(request, delegate);
        }
    }

}
