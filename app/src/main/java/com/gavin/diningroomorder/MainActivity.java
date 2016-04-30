package com.gavin.diningroomorder;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.BusinessManager;
import net.BusinessRequest;
import net.IBusinessDeleage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import bean.Meal;
import bean.ParseManager;
import cz.msebera.android.httpclient.Header;
import logic.MealRequest;

/**
 * Created by gavinding on 16/4/4.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBusinessDeleage {

    private static final String LOG_TAG = "MainActivity";
//    HttpUtil httpUtil;
    Button BtnPreDate, BtnNextDate;
//    WebView mWebView;
//    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mWebView = (WebView) findViewById(R.id.webview);
//        mWebView.loadUrl("http://kolvin.cn");
        BtnPreDate = (Button) findViewById(R.id.pre);
        BtnNextDate = (Button) findViewById(R.id.next);
//        mTextView = (TextView) findViewById(R.id.content);
//
        BtnPreDate.setOnClickListener(this);
        BtnNextDate.setOnClickListener(this);
//        mWebView = (WebView) findViewById(R.id.webview);
//        httpUtil = new HttpUtil();
//        httpUtil.setCookie(PreferenceUtil.getCookie(getApplication()));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());

        //http://kolvin.cn/Meal/GetMealByDateAndType?date=2016-04-11&type=2&_=1460045964215
        //http://kolvin.cn/Meal/GetMyMeal?date=2016-04-07&type=1
//        Log.d(LOG_TAG, "url = " + url);
//        httpUtil.HttpPostURL(url, handler, "");
//        mWebView.loadUrl(url);
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });


        BusinessManager.getInstance(this).requestMeal(strDate, this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String m = (String) msg.obj;
            Log.d(LOG_TAG, "url = " + m);

            //mResult.setText(m);
//            try {
            // JSONObject object = new JSONObject(m);
            // String statusCode = object.getString("statusCode");

            //if ("200".equals(statusCode)) {
//                   // Toast.makeText(MainActivity.this, m, Toast.LENGTH_LONG).show();
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("确认")
//                    .setMessage(m)
//                    .setPositiveButton("是", null)
//                    .setNegativeButton("否", null)
//                    .show();                //}
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener mSendClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pre:
                BusinessManager.getInstance(this).requestMeal("2016-04-12", this);
                break;
            case R.id.next:
                BusinessManager.getInstance(this).requestMeal("2016-04-13", this);
                break;
        }
    }

    private int getViewIdByMealType(String type)
    {
        int id = 0;
        switch (type)
        {
            case "1":
                id = R.id.listView1;
                break;
            case "2":
                id = R.id.listView2;
                break;
            case "3":
                id = R.id.listView3;
                break;
            case "4":
                id = R.id.listView4;
                break;
            default:
                Log.e(LOG_TAG, "meal type(" + type +") error");
        }

        return id;
    }

    @Override
    public void onProcessSuccess(BusinessRequest request, int statusCode, Header[] headers, byte[] response, IBusinessDeleage deleage) {

        MealRequest mealRequest = (MealRequest)request;
        String jsonData = new String(response);
        ParseManager.getInstance().parseMealDayList(mealRequest.getDate(), mealRequest.getType(), jsonData);

        ArrayAdapter<Meal> adapter = new MealAdapter(mealRequest.getDate(), mealRequest.getType());
        ListView list = (ListView) findViewById(getViewIdByMealType(mealRequest.getType()));;
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Meal clickedMeal = mealList.get(position);
//                clickedMeal.setOrderCount(clickedMeal.getOrderCount() + 1);
//
//                TextView countText = (TextView) view.findViewById(R.id.textView_count);
//                countText.setText(clickedMeal.getOrderCount());
//
//                Toast.makeText(getBaseContext(), clickedMeal.getOrderCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProcessFailed() {
        Toast.makeText(MainActivity.this, "请求超时，请检查网络连接", Toast.LENGTH_SHORT).show();
    }

    private class MealAdapter extends ArrayAdapter<Meal> {
        private String date = null;
        private String type = null;
        List<Meal> mealList = null;
        public MealAdapter(String date, String type) {
            super(MainActivity.this, R.layout.item_view, (List<Meal>) ParseManager.getInstance().getMealByDate(date, type).getMealList());
            this.date = date;
            this.type = type;
            this.mealList = (List<Meal>)ParseManager.getInstance().getMealByDate(date, type).getMealList();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            if(null != mealList) {
                Meal currentMeal = mealList.get(position);

                TextView nameText = (TextView) itemView.findViewById(R.id.itemView_name);
                nameText.setText(currentMeal.getDishName());

                TextView costText = (TextView) itemView.findViewById(R.id.itemView_cost);
                costText.setText(String.format("￥%s", currentMeal.getDishPrice()));

                TextView countText = (TextView) itemView.findViewById(R.id.itemView_count);
                countText.setText(currentMeal.getOrderCount());
            }

            itemView.setBackgroundColor(getColorByMealType(this.type));
            return itemView;
        }

    }

    private int getColorByMealType(String type) {
        int color = 0;
        switch (type)
        {
            case "1":
                color = Color.BLUE;
                break;
            case "2":
                color = Color.RED;
                break;
            case "3":
                color = Color.GREEN;
                break;
            case "4":
                color = Color.YELLOW;
                break;
            default:
                Log.e(LOG_TAG, "meal type(" + type +") error");
        }

        return color;
    }
}
