package bean;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gavin on 9/4/16.
 */
public class Meal {

    private static final String LOG_TAG = "Meal";
    private String MealID = "0";
    private String DishID = "0";        // ID
    private String DishName = "0";      // 菜名
    private double OriPrice = 0;        // 价格
    private double DishPrice = 0;
    private int DishLimitCount = 0;     // 限制份数
    private int ResidueCount = 0;       // 剩余份数
    private int OrderCount = 0;
    private int SourceOrderCount = 0;

    private int Type = 0;
    private String Date = "";
    private boolean isChanged = false;


    public Meal(JSONObject jsonData) {
        try {
            MealID = jsonData.getString("MealID");
            DishID = jsonData.getString("DishID");
            DishName = jsonData.getString("DishName");
            OriPrice = jsonData.getDouble("OriPrice");
            DishPrice = jsonData.getDouble("DishPrice");
            DishLimitCount = jsonData.getInt("DishLimitCount");
            ResidueCount = jsonData.getInt("ResidueCount");
            OrderCount = jsonData.getInt("OrderCount");
            SourceOrderCount = OrderCount;
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

    public double getOriPrice() {
        return OriPrice;
    }

    public void setOriPrice(double oriPrice) {
        OriPrice = oriPrice;
    }

    public double getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(double dishPrice) {
        DishPrice = dishPrice;
    }

    public int getDishLimitCount() {
        return DishLimitCount;
    }

    public void setDishLimitCount(int dishLimitCount) {
        DishLimitCount = dishLimitCount;
    }

    public int getResidueCount() {
        return ResidueCount;
    }

    public void setResidueCount(int residueCount) {
        ResidueCount = residueCount;
    }

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int orderCount) {
        Log.d(LOG_TAG, "data changed");
        OrderCount = orderCount;
        if(SourceOrderCount == OrderCount)
        {
            isChanged = false;
        }
        else {
            isChanged = true;
        }
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        this.Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void clearDataChangeFlag() {
        isChanged = false;
        SourceOrderCount = OrderCount;
    }

    public boolean isDataChanged() {
        return isChanged;
    }
}
