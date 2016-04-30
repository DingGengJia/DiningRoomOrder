package logic;

import net.BusinessRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class MealRequest extends BusinessRequest {
    private String urlHead = "http://kolvin.cn/UserMeal/GetMyMeal";

    private String date;
    private String type;

    public MealRequest(String date, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
