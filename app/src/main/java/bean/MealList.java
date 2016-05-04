package bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 9/4/16.
 */
public class MealList {

    //    public enum MealTypeEnum {Breakfast, Lunch, Dinner, NightSnake}
    public final static int[] mealType = {1, 2, 3, 4}; //早，中，晚，宵夜

    private String Date;
    private int Type;

    private List<Meal> mMealList = new ArrayList<Meal>();
    private static int iMinPrice = 7;

    public MealList(String date, int type, String jsonString) {
        if (jsonString == null) return;
        try {
            setDate(date);
            setType(type);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Meal meal = new Meal(jsonObject);
                meal.setDate(date);
                meal.setType(type);
                mMealList.add(meal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        this.Type = type;
    }

    public List<Meal> getMealList() {
        return mMealList;
    }

    public void setMealList(List<Meal> mMealList) {
        this.mMealList = mMealList;
    }

    public static int[] getMealType() {
        return mealType;
    }

    // 午餐、晚餐不能少于7元
    public boolean isPirceEnough()
    {
        if(Type != 2 && Type != 3)
        {
            return true;
        }

        int iTotalPrice = 0;
        for(Meal meal : mMealList)
        {
            iTotalPrice += meal.getOrderCount() * meal.getDishPrice();
        }

        if(iTotalPrice >= iMinPrice || iTotalPrice == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
