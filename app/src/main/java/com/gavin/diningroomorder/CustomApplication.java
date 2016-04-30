package com.gavin.diningroomorder;

import android.app.Application;

/**
 * Created by gavinding on 7/4/16.
 */
public class CustomApplication extends Application{

    private String sCookie;

    public String getsCookie() {
        return sCookie;
    }

    public void setsCookie(String sCookie) {
        this.sCookie = sCookie;
    }
}
