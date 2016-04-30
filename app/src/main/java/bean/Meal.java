package bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gavin on 9/4/16.
 */
public class Meal {

    private String MealID = "0";
    private String DishID = "0";          // ID
    private String DishName = "0";        // 菜名
    private String OriPrice = "0";        // 价格
    private String DishPrice = "0";
    private String DishLimitCount = "0";  // 限制份数
    private String ResidueCount = "0";    // 剩余份数
    private String OrderCount = "0";

    private String Type = "";
    private String Date = "";


    public Meal(JSONObject jsonData) {
        try {
            MealID = jsonData.getString("MealID");
            DishID = jsonData.getString("DishID");
            DishName = jsonData.getString("DishName");
            OriPrice = jsonData.getString("OriPrice");
            DishPrice = jsonData.getString("DishPrice");
            DishLimitCount = jsonData.getString("DishLimitCount");
            ResidueCount = jsonData.getString("ResidueCount");
            OrderCount = jsonData.getString("OrderCount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMealID() {
        return MealID;
    }

    public void setMealID(String mealID) {
        this.MealID = mealID;
    }

    public String getDishID() {
        return DishID;
    }

    public void setDishID(String dishID) {
        DishID = dishID;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getOriPrice() {
        return OriPrice;
    }

    public void setOriPrice(String oriPrice) {
        OriPrice = oriPrice;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getDishLimitCount() {
        return DishLimitCount;
    }

    public void setDishLimitCount(String dishLimitCount) {
        DishLimitCount = dishLimitCount;
    }

    public String getResidueCount() {
        return ResidueCount;
    }

    public void setResidueCount(String residueCount) {
        ResidueCount = residueCount;
    }

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String orderCount) {
        OrderCount = orderCount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
