package com.gavin.diningroomorder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import net.BusinessManager;
import net.BusinessRequest;
import net.IBusinessDeleage;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gavinding on 16/4/4.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IBusinessDeleage {

    private static final String LOG_TAG = "LoginActivity";
    private static final String PREFS_NAME = "prefsname";       // 偏好设置名称
    public static final String USER_NAME = "username"; //用户名标记
    public static final String USER_PASSWORD = "password"; //用户名标记
    private static final String DEFAULT_USERNAME = "am30467"; //默认用户名

    EditText mUserName;
    EditText mPassWord;
    Button mLogin;
    CheckBox mSave;
    private SharedPreferences mSettings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText) findViewById(R.id.username);
        mPassWord = (EditText) findViewById(R.id.psd);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mSave = (CheckBox) findViewById(R.id.checkBox_save);

        mSettings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (getUserName() != null) {
            mUserName.setText(getUserName()); //设置用户名
        }
        if (getPassword() != null && getPassword() != "") {
            mSave.setChecked(true); //勾选记住密码
            mPassWord.setText(getPassword());
        }
    }

    private String getPassword() {
        return mSettings.getString(USER_PASSWORD, "");
    }

    // 保存用户名
    private void saveUserName(String username) {
        SharedPreferences.Editor editor = mSettings.edit();// 获取编辑器
        editor.putString(USER_NAME, username);
        editor.commit(); //保存数据
        //editor.clear();//清除数据
    }

    // 获取保存的用户名
    private String getUserName() {
        return mSettings.getString(USER_NAME, DEFAULT_USERNAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String userName = mUserName.getText().toString();
                String psd = mPassWord.getText().toString();
                BusinessManager.getInstance(this).requestLogin(userName, psd, this);

                // TODO temp to del
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

                saveUserName(mUserName.getText().toString());

                if (mSave.isChecked()) {
                    savePassword(mPassWord.getText().toString());
                } else {
                    savePassword("");
                }
                break;
        }
    }

    private void savePassword(String password) {
        SharedPreferences.Editor editor = mSettings.edit();// 获取编辑器
        editor.putString(USER_PASSWORD, password);
        editor.commit(); //保存数据
    }

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
    public void onProcessFailed(BusinessRequest request, int statusCode, Header[] headers, byte[] response) {
        Toast.makeText(LoginActivity.this, "连接超时，请检查网络连接", Toast.LENGTH_SHORT).show();
    }
}
