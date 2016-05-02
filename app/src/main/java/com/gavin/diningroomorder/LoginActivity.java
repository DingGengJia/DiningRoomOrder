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

/**
 * Created by gavinding on 16/4/4.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IBusinessDeleage {

    EditText mUserName;
    EditText mPassWord;
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = (EditText) findViewById(R.id.username);
        mPassWord = (EditText) findViewById(R.id.psd);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
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
                break;
        }
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
    public void onProcessFailed(BusinessRequest request) {
        Toast.makeText(LoginActivity.this, "连接超时，请检查网络连接", Toast.LENGTH_SHORT).show();
    }
}
