package bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by gavin on 9/4/16.
 */
public class ParseManager {
    private static final String LOG_TAG = "ParseManager";
    static ParseManager parseManager;

    private final String PARAM_MEAL_KEY = "%s:%s";

    private Map<String, MealList> mAllDateMeal = new HashMap<>();

    public static ParseManager getInstance() {
        if (parseManager == null) {
            parseManager = new ParseManager();
        }

        return parseManager;
    }

    public void parseMealDayList(String date, int type, String jsonData) {
        String key = String.format(PARAM_MEAL_KEY, date, type);
        MealList mealList = new MealList(date, type, jsonData);
        mAllDateMeal.put(key, mealList);
    }

    public List<MealList> getMealByDate(String date) {
        List<MealList> mealLists = new ArrayList<>();
        for (int i : MealList.getMealType()) {
            MealList mealList = getMealByDate(date, i);
            if (mealList != null) {
                mealLists.add(mealList);
            }
        }
        return mealLists;
    }


    public MealList getMealByDate(String date, int type) {
        String paramKey = String.format(PARAM_MEAL_KEY, date, type);

        Iterator iter = mAllDateMeal.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if (key != null && key.equals(paramKey)) {
                return (MealList) entry.getValue();
            }
        }
        return null;
    }

    public String getTotalPriceByDate(String date) {
        int sum = 0;
        List<MealList> lists = getMealByDate(date);

        for (MealList list : lists) {
            for (Meal meal : list.getMealList()) {
                if (Integer.valueOf(meal.getOrderCount()) > 0) {
                    sum += meal.getOrderCount() * meal.getOriPrice();
                }
            }
        }

        return Integer.toString(sum);
    }

    public boolean isOrderCountChanged(String date) {
        List<MealList> lists = getMealByDate(date);

        for (MealList list : lists) {
            for (Meal meal : list.getMealList()) {
                if (true == meal.isDataChanged()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearDataChangeFlag(String date) {
        List<MealList> lists = getMealByDate(date);

        for (MealList list : lists) {
            for (Meal meal : list.getMealList()) {
                meal.clearDataChangeFlag();
            }
        }
    }

    public String getMealString(String date) {
        String mealString = "";
        List<String> strList = new LinkedList<>();
        List<MealList> lists = getMealByDate(date);

        for (MealList list : lists) {
            for (Meal meal : list.getMealList()) {
                if (meal.getOrderCount() > 0) {
                    mealString = String.format("%s,%s,%s", meal.getMealID(), (int) meal.getDishPrice(), meal.getOrderCount());
                    strList.add(mealString);
                }
            }
        }

        mealString = "";
        for (int i = 0; i < strList.size(); ++i) {
            String str = strList.get(i);
            Log.d(LOG_TAG, "str:" + str);
            mealString = mealString + str;
            if (i < strList.size() - 1) {
                mealString = mealString + "|";
            }
        }

        return mealString;
    }

    public boolean isPriceEnough(String date)
    {
        List<MealList> mealLists = new ArrayList<>();
        for (int i : MealList.getMealType()) {
            MealList mealList = getMealByDate(date, i);
            if (mealList != null) {
                if(false == mealList.isPirceEnough())
                {
                    return false;
                }
            }
        }

        return true;
    }
}
