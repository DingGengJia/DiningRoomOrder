package com.gavin.diningroomorder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import net.BusinessManager;
import net.BusinessRequest;
import net.IBusinessDeleage;

import java.util.Calendar;
import java.util.List;

import bean.Meal;
import bean.ParseManager;
import cz.msebera.android.httpclient.Header;
import logic.MealRequest;
import util.Util;
import widget.InnerListView;
import widget.NumberPickerView;

/**
 * Created by gavinding on 16/4/4.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBusinessDeleage, NumberPickerView.INumberPickerView {

    private static final String LOG_TAG = "MainActivity";
    Button btnPreDate, btnNextDate;
    TextView textDate, textTotalPrice;
    PopupWindow popupWindow;
    Calendar cal = Calendar.getInstance();

    ArrayAdapter arrayAdapter;
    Meal numberPickerMeal;
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPreDate = (Button) findViewById(R.id.pre);
        btnNextDate = (Button) findViewById(R.id.next);
        textTotalPrice = (TextView) findViewById(R.id.totalPrice);

        btnPreDate.setOnClickListener(this);
        btnNextDate.setOnClickListener(this);
        textTotalPrice.setOnClickListener(this);

        textDate = (TextView) findViewById(R.id.textView_date);
        textDate.setClickable(true);
        textDate.setFocusable(true);
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "select one day by DatePicker");
                DatePickerDialog dateDlg = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar tempCal = (Calendar) cal.clone();
                                tempCal.set(Calendar.YEAR, year);
                                tempCal.set(Calendar.MONTH, monthOfYear);
                                tempCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dealOnNewDay(tempCal);
                            }
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                dateDlg.show();
            }
        });

        onNewDay(Calendar.getInstance());
    }

    private void onNewDay(Calendar newDay) {
        updateDate(newDay);

        BusinessManager.getInstance(MainActivity.this).requestMeal(dateString, this);

        // TODO temp, to delte
//        String jsonData = new String("[{\"MealID\":\"40645517-aeb2-4c15-ab12-0e6d33d6101d\",\"DishID\":\"867242a3-e941-42aa-bc0d-8d8a1455e9e8\",\"DishName\":\"标准早餐\",\"OriPrice\":3,\"DishPrice\":3,\"DishLimitCount\":0,\"ResidueCount\":-1,\"OrderCount\":1},{\"MealID\":\"4237e174-de34-4cfb-9a93-41c5ecc0f602\",\"DishID\":\"a942df11-28d9-42f2-88ab-17a08c93f4a6\",\"DishName\":\"银鹭花生牛奶\",\"OriPrice\":3,\"DishPrice\":3,\"DishLimitCount\":0,\"ResidueCount\":-1,\"OrderCount\":1},{\"MealID\":\"35b1a1f1-0e4d-4c83-b2a2-96b0f0ed4e5e\",\"DishID\":\"2320b69a-ee2b-4df0-8894-5e95493a4a9f\",\"DishName\":\"茶叶蛋\",\"OriPrice\":2,\"DishPrice\":2,\"DishLimitCount\":0,\"ResidueCount\":-1,\"OrderCount\":0},{\"MealID\":\"ebee260d-f5c1-4803-9a1e-c38e78030833\",\"DishID\":\"2660172f-c970-4666-b83c-86069ac19bf3\",\"DishName\":\"优酸乳\",\"OriPrice\":2,\"DishPrice\":2,\"DishLimitCount\":0,\"ResidueCount\":-1,\"OrderCount\":0},{\"MealID\":\"9c7953f7-31b8-4e11-b48e-ea027ae3384b\",\"DishID\":\"67eac511-27af-4e8d-96ff-2ca3d453254c\",\"DishName\":\"晨光甜牛奶\",\"OriPrice\":3,\"DishPrice\":3,\"DishLimitCount\":0,\"ResidueCount\":-1,\"OrderCount\":0}]");
//        for (int i : MealList.getMealType()) {
//            displayMealData(dateString, i, jsonData);
//        }
        Log.d(LOG_TAG, "onNewDay " + cal.getTime());
    }

    @Override
    public void onClick(View v) {
        int day = 0;
        switch (v.getId()) {
            case R.id.pre:
                day = -1;
                nextDay(day);
                break;
            case R.id.next:
                day = 1;
                nextDay(day);
                break;
            case R.id.totalPrice:
                checkSaveData();
                break;
        }
    }

    private void nextDay(int day) {
        Calendar tempCal = (Calendar) cal.clone();
        tempCal.add(Calendar.DAY_OF_YEAR, day);
        dealOnNewDay(tempCal);
    }

    private void dealOnNewDay(Calendar newDay) {
        checkSaveData();
        onNewDay(newDay);
    }

    private void checkSaveData() {
        if (true == ParseManager.getInstance().isOrderCountChanged(dateString)) {
            Log.d(LOG_TAG, "go to next day with data changed");
            showCheckDialog();
        } else {
            Log.d(LOG_TAG, "go to next day with no data changed");
        }
    }

    private void showCheckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("订餐数据有变更，需要提交吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG, "confrim data change");
                dialog.dismiss();
                String selString = ParseManager.getInstance().getMealString(dateString);
                BusinessManager.getInstance(MainActivity.this).requestSaveUserMeal(Util.getDateTimeString(cal), selString, MainActivity.this);
                ParseManager.getInstance().clearDataChangeFlag(dateString);
                updateTotalPrice();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG, "cancel data change");
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void updateDate(Calendar newDay) {
        cal = newDay;
        dateString = Util.getDateString(cal);
        textDate.setText(Util.getDateWeekString(cal));
    }

    private int getViewIdByMealType(int type) {
        int id = 0;
        switch (type) {
            case 1:
                id = R.id.listView1;
                break;
            case 2:
                id = R.id.listView2;
                break;
            case 3:
                id = R.id.listView3;
                break;
            case 4:
                id = R.id.listView4;
                break;
            default:
                Log.e(LOG_TAG, "meal type(" + type + ") error");
        }

        return id;
    }

    @Override
    public void onProcessSuccess(BusinessRequest request, int statusCode, Header[] headers, byte[] response, IBusinessDeleage deleage) {
        switch (request.getId()) {
            case BusinessRequest.REQEUST_ID_GET_MEAL:
                Log.d(LOG_TAG, "process get meal success");
                MealRequest mealRequest = (MealRequest) request;
                String jsonData = new String(response);
                displayMealData(mealRequest.getDate(), mealRequest.getType(), jsonData);
                break;
            case BusinessRequest.REQEUST_ID_SAVE_USER_MEAL:
                Log.d(LOG_TAG, "process save user meal success");
                break;
        }
    }

    @Override
    public void onProcessFailed(BusinessRequest request, int statusCode, Header[] headers, byte[] response) {
        switch (request.getId()) {
            case BusinessRequest.REQEUST_ID_GET_MEAL:
                Log.d(LOG_TAG, "process get meal failed");
                Toast.makeText(MainActivity.this, "请求超时，请检查网络连接", Toast.LENGTH_SHORT).show();
                break;
            case BusinessRequest.REQEUST_ID_SAVE_USER_MEAL:
                Log.d(LOG_TAG, "process save user meal failed");
                Log.d(LOG_TAG, "statuscode:" + statusCode);
                Log.d(LOG_TAG, "resonse:" + new String(response));
                break;
        }
    }

    private void displayMealData(String date, int type, String jsonData) {
        ParseManager.getInstance().parseMealDayList(date, type, jsonData);

        final ArrayAdapter<Meal> adapter = new MealAdapter(date, type);
        InnerListView list = (InnerListView) findViewById(getViewIdByMealType(type));

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meal meal = adapter.getItem(position);
                arrayAdapter = adapter;
                numberPickerMeal = meal;
                showPopupWindow(view, meal);
            }
        });

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        Log.d(LOG_TAG, "update total price");
        textTotalPrice.setText(String.format("总价￥%s", ParseManager.getInstance().getTotalPriceByDate(dateString)));
        if (true == ParseManager.getInstance().isOrderCountChanged(dateString)) {
            textTotalPrice.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorTotalPriceChanged));
        } else {
            textTotalPrice.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorTotalPriceNormal));
        }
    }

    @Override
    public void submit(int count) {
        cancle();
        if (arrayAdapter != null && numberPickerMeal != null) {
            arrayAdapter.notifyDataSetChanged();
            numberPickerMeal.setOrderCount(count);
            updateTotalPrice();
        }
    }

    @Override
    public void cancle() {
        if (popupWindow != null)
            popupWindow.dismiss();
    }

    private class MealAdapter extends ArrayAdapter<Meal> {
        private String date = null;
        private int type = 0;
        List<Meal> mealList = null;

        public MealAdapter(String date, int type) {
            super(MainActivity.this, R.layout.item_view, (List<Meal>) ParseManager.getInstance().getMealByDate(date, type).getMealList());
            this.date = date;
            this.type = type;
            this.mealList = (List<Meal>) ParseManager.getInstance().getMealByDate(date, type).getMealList();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            if (null != mealList) {
                Meal currentMeal = mealList.get(position);

                TextView nameText = (TextView) itemView.findViewById(R.id.itemView_name);
                nameText.setText(currentMeal.getDishName());

                TextView costText = (TextView) itemView.findViewById(R.id.itemView_cost);
                costText.setText(String.format("￥%s", currentMeal.getDishPrice()));

                TextView countText = (TextView) itemView.findViewById(R.id.itemView_count);
                countText.setText(Integer.toString(currentMeal.getOrderCount()));
            }

            return itemView;
        }

    }

    private void showPopupWindow(View view, Meal meal) {
        NumberPickerView contentView = (NumberPickerView) LayoutInflater.from(this).inflate(
                R.layout.view_number_picker, null);
        contentView.setDelegate(this);
        contentView.setLastCount(meal.getOrderCount());
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorNumberPickerBackground));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
