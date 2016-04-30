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
    private String Type;

    private List<Meal> mMealList = new ArrayList<Meal>();

    public MealList(String date, String type, String jsonString) {
        if (jsonString == null) return;
        try {
            setDate(date);
            setType(type);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Meal meal = new Meal(jsonObject);
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
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

}
