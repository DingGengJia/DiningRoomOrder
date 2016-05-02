package logic;

import net.BusinessRequest;

/**
 * Created by gavin on 2/5/16.
 */
public class SaveUserMealRequest extends BusinessRequest {
    private String urlHead = "http://kolvin.cn/UserMeal/Save";

    String date;
    String sel;

    public SaveUserMealRequest(String date, String sel) {
        setId(BusinessRequest.REQEUST_ID_SAVE_USER_MEAL);
        setDate(date);
        setSel(sel);
        String url = urlHead;
        setUrl(url);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSel() {
        return sel;
    }

    public void setSel(String sel) {
        this.sel = sel;
    }
}
