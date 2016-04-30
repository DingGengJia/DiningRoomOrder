package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gavin on 9/4/16.
 */
public class ParseManager {
    static ParseManager parseManager;

    private final String PARAM_MEAL_KEY = "%s:%s";

    private Map<String, MealList> mAllDateMeal = new HashMap<>();

    public static ParseManager getInstance() {
        if(parseManager == null)
        {
            parseManager = new ParseManager();
        }

        return parseManager;
    }

    public void parseMealDayList(String date, String type, String jsonData) {
        String key = String.format(PARAM_MEAL_KEY, date, type);
        MealList mealList = new MealList(date, type, jsonData);
        mAllDateMeal.put(key, mealList);
    }

    public List<MealList> getMealByDate(String date) {
        List<MealList> mealLists = new ArrayList<>();
        for (int i : MealList.getMealType())
        {
            MealList mealList = getMealByDate(date, Integer.toString(i));
            if (mealList != null)
            {
                mealLists.add(mealList);
            }
        }
        return mealLists;
    }


    public MealList getMealByDate(String date, String type) {
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

    public String getTotalPriceByDate(String date)
    {
        int sum = 0;
        List<MealList> lists = getMealByDate(date);

        for (MealList list : lists)
        {
            for (Meal meal : list.getMealList())
            {
                if(Integer.valueOf(meal.getOrderCount()) > 0) {
                    sum += Integer.valueOf(meal.getOrderCount()) * Integer.valueOf(meal.getOriPrice());
                }
            }
        }

        return Integer.toString(sum);
    }
}
