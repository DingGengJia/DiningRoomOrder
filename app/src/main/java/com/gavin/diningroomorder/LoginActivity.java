package com.gavin.diningroomorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.BusinessManager;
import net.BusinessRequest;
import net.IBusinessDeleage;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import cz.msebera.android.httpclient.Header;
import util.HttpUtil;

/**
 * Created by gavinding on 16/4/4.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IBusinessDeleage {

    HttpUtil httpUtil;
    EditText mUserName;
    EditText mPassWord;
    Button mLogin;

    //http://kolvin.cn/Home/GetRealName?name=admin&_=1459775891901
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = (EditText) findViewById(R.id.username);
        mPassWord = (EditText) findViewById(R.id.psd);
        mLogin = (Button) findViewById(R.id.login);

        mLogin.setOnClickListener(this);
//        httpUtil = new HttpUtil();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String userName = mUserName.getText().toString();
                String psd = mPassWord.getText().toString();
//                httpUtil.HttpGetURL(url, handler);
                BusinessManager.getInstance(this).requestLogin(userName, psd, this);
                break;
        }
    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            String m = (String) msg.obj;
//            try {
//                JSONObject object = new JSONObject(m);
//                int statusCode = object.getInt("statusCode");
//
//                if (HttpURLConnection.HTTP_OK == statusCode) {
//                    PreferenceUtil.setCookie(getApplication(), httpUtil.getCookie());
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    };


    @Override
    public void onProcessSuccess(BusinessRequest request, int statusCode, Header[] headers, byte[] response, IBusinessDeleage deleage) {
        try {
            JSONObject object = new JSONObject(new String(response));
            int iStatusCode = object.getInt("statusCode");
            if (HttpURLConnection.HTTP_OK == iStatusCode) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProcessFailed() {
        Toast.makeText(LoginActivity.this, "连接超时，请检查网络连接", Toast.LENGTH_SHORT).show();
    }
}
