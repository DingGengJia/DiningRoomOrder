package logic;

import net.BusinessRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class MealRequest extends BusinessRequest {
    private String urlHead = "http://kolvin.cn/UserMeal/GetMyMeal";

    private String date;
    private int type;

    public MealRequest(String date, int type) {
        setDate(date);
        setType(type);
        String url = urlHead + "?date=" + date + "&type=" + type;
        setUrl(url);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
