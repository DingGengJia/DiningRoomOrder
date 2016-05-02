package net;

/**
 * Created by gavin on 10/4/16.
 */
public class BusinessRequest {
    public final static int REQEUST_ID_LOGIN = 0;               // 登录
    public final static int REQEUST_ID_GET_MEAL = 1;            // 查询菜谱
    public final static int REQEUST_ID_SAVE_USER_MEAL = 2;       // 保存订单

    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
