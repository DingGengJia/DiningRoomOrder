package net;

/**
 * Created by gavin on 10/4/16.
 */
public interface IBusinessManager {

    //登录
    public void requestLogin(String name ,String psd, IBusinessDeleage delegate);

    //meal
    public void requestMeal(String date, IBusinessDeleage delegate);
    //save meal

}
