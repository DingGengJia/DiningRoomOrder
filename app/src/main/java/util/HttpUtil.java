package util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gavinding on 16/4/4.
 */
public class HttpUtil {
    String resultData = "";
    URL url = null;
    HttpURLConnection urlConn = null;
    boolean isPost = true;
    String mCookieStr = "";

    public void HttpURLConnection_Get(String URL, Handler handler) {
        try {
            isPost = false;
            //通过openConnection 连接
            URL url = new URL(URL);
            urlConn = (HttpURLConnection) url.openConnection();
            //设置输入和输出流
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            //关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            resultData = GetHttpConnectTimeoutJsonString();
            Message mg = Message.obtain();
            mg.obj = resultData;
            handler.sendMessage(mg);
            e.printStackTrace();
        }
    }

    public void HttpURLConnection_Post(String URL_Post, Handler handler, String content) {
        try {
            //通过openConnection 连接
            URL url = new URL(URL_Post);
            urlConn = (HttpURLConnection) url.openConnection();
            //设置输入和输出流
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);

            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false);
            // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Cookie", mCookieStr);
            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            urlConn.connect();

            //DataOutputStream流
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
            //要上传的参数
//            String content = "par=" + URLEncoder.encode("ylx_Post+中正", "UTF-8");
            //将要上传的内容写入流中
            out.writeBytes(content);
            //刷新、关闭
            out.flush();
            out.close();

        } catch (Exception e) {
            resultData = GetHttpConnectTimeoutJsonString();
            Message mg = Message.obtain();
            mg.obj = resultData;
            handler.sendMessage(mg);
            e.printStackTrace();
        }
    }

    public void HttpGetURL(final String url, final Handler handler) {
        new Thread() {
            public void run() {

                try {
                    resultData = "";
                    Log.d("HttpGetURL", "url = " + url);

                    //Get方式
                    HttpURLConnection_Get(url, handler);
                    //Post方式
//                    HttpURLConnection_Post();

                    InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String inputLine = null;
                    while (((inputLine = buffer.readLine()) != null)) {
                        resultData += inputLine + "\n";
                    }
                    Log.d("HttpGetURL", "result = " + resultData);
                    Log.d("HttpGetURL", "headerFields" + urlConn.getHeaderFields().toString());
                    in.close();

                    mCookieStr = urlConn.getHeaderField("Set-Cookie");

                } catch (Exception e) {
                    resultData = GetHttpConnectTimeoutJsonString();
                    e.printStackTrace();
                } finally {
                    try {
                        //关闭连接
                        if (isPost) {
                            urlConn.disconnect();
                        }
                        Message mg = Message.obtain();
                        mg.obj = resultData;
                        handler.sendMessage(mg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void HttpPostURL(final String url, final Handler handler, final String content) {
        new Thread() {
            public void run() {

                try {
                    Log.d("HttpPostURL", "url = " + url);

                    //Get方式
//                    HttpURLConnection_Get(url,handler);
                    //Post方式
                    HttpURLConnection_Post(url, handler, content);

                    InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String inputLine = null;
                    while (((inputLine = buffer.readLine()) != null)) {
                        resultData += inputLine + "\n";
                    }
                    Log.d("HttpPostURL", "result = " + resultData);
                    in.close();

                    mCookieStr = urlConn.getHeaderField("Set-Cookie");

                } catch (Exception e) {
                    resultData = GetHttpConnectTimeoutJsonString();
                    e.printStackTrace();
                } finally {
                    try {
                        //关闭连接
                        if (isPost) {
                            urlConn.disconnect();
                        }
                        Message mg = Message.obtain();
                        mg.obj = resultData;
                        handler.sendMessage(mg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private String GetHttpConnectTimeoutJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("statusCode", ErrorCode.HTTP_CONNECT_TIMEOUT);
            object.put("message", "连接超时，请检查网络连接");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String getCookie() {
        return mCookieStr;
    }

    public void setCookie(String cookieStr) {
        mCookieStr = cookieStr;
    }
}
