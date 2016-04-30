package com.gavin.diningroomorder;

/**
 * Created by gavin on 9/4/16.
 */
public class User {
    private String mUserName = "";
    private String mWorkNum = "";
    private String mRealName = "";

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String mRealName) {
        this.mRealName = mRealName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getWorkNum() {
        return mWorkNum;
    }

    public void setWorkNum(String mWorkNum) {
        this.mWorkNum = mWorkNum;
    }

    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
