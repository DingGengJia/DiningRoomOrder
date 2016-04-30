package net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.cookie.Cookie;
import logic.MealRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class NetworkManager implements AsyncHttpClientInterface {


    private static final String LOG_TAG = "NetworkManager";
    static NetworkManager networkManager;
    IBusinessDeleage mBaseLogicInterface;
    private final List<RequestHandle> requestHandles = new ArrayList<>();

    private AsyncHttpClient client = new AsyncHttpClient();

    public void sendGetRequst(BusinessRequest request, IBusinessDeleage deleage) {
        Log.d(LOG_TAG, "url=" + request.getUrl());

//        PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
//        client.setCookieStore(myCookieStore);

        addRequestHandle(executeGet(client,
                request,
                getRequestHeaders(),
                getRequestEntity(),
                getResponseHandler(request, deleage)));
    }

    public void sendPostRequst(BusinessRequest request, IBusinessDeleage deleage) {
        Log.d(LOG_TAG, "url=" + request.getUrl());

//        PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
//        client.setCookieStore(myCookieStore);

        addRequestHandle(executePost(client,
                request,
                getRequestHeaders(),
                getRequestEntity(),
                getResponseHandler(request, deleage)));
    }

    @Override
    public void addRequestHandle(RequestHandle handle) {

    }

    @Override
    public Header[] getRequestHeaders() {
        return new Header[0];
    }

    @Override
    public HttpEntity getRequestEntity() {
        return null;
    }

    @Override
    public ResponseHandlerInterface getResponseHandler(final BusinessRequest request, final IBusinessDeleage deleage) {
        if (request instanceof MealRequest) {
            String type = ((MealRequest) request).getType();
            Log.d("response==", "type =" + type);

        }
        return new AsyncHttpResponseHandler() {
            BusinessRequest mRequest = request;
            IBusinessDeleage mDeleage = deleage;

            @Override
            public void onStart() {
//                clearOutputs();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.d(LOG_TAG, "========onSuccess ");

                if (headers != null) {
                    for (Header h : headers) {
                        String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
                        Log.d(LOG_TAG, "header" + _h);
                    }
                }
                Log.d(LOG_TAG, "response"+ new String(response));

                if (mRequest instanceof MealRequest) {
                    String type = ((MealRequest) mRequest).getType();
                    Log.d(LOG_TAG, "response type =" + type);
                }

                //获取AsyncHttpClient中的CookieStore
//                PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
//                client.setCookieStore(myCookieStore);

//                Toast.makeText(mContext, "登录成功，cookie=" + getCookieText(), Toast.LENGTH_SHORT).show();

                mDeleage.onProcessSuccess(mRequest, statusCode, headers, response, mDeleage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.d(LOG_TAG, "========onFailure ");
                mDeleage.onProcessFailed();
            }
        };
    }

    private static Context mContext;

    public static NetworkManager getInstance(Context ctx) {
        if (networkManager == null) {
            networkManager = new NetworkManager();
            mContext = ctx;
            // Use the application's context so that memory leakage doesn't occur.
            //cookieStore = new PersistentCookieStore(ctx.getApplicationContext());

            // Set the new cookie store.
            //getAsyncHttpClient().setCookieStore(cookieStore);
        }
        return networkManager;
    }

    public RequestHandle executeGet(AsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        return null;
    }

    public RequestHandle executeGet(AsyncHttpClient client, BusinessRequest request, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        String URL = request.getUrl();
        return client.get(mContext, URL, headers, null, responseHandler);
    }

    public RequestHandle executePost(AsyncHttpClient client, BusinessRequest request, Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
        String URL = request.getUrl();
        return client.post(mContext, URL, headers, entity, null, responseHandler);
    }

    /**
     * 获取标准 Cookie
     */
    private String getCookieText() {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext);
        List<Cookie> cookies = myCookieStore.getCookies();
        Log.d(LOG_TAG, "cookies.size() = " + cookies.size());
//        Util.setCookies(cookies);
        for (Cookie cookie : cookies) {
            Log.d(LOG_TAG, "cookie info:" + cookie.getName() + " = " + cookie.getValue());
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        Log.d(LOG_TAG, "cookie=" + sb.toString());
        return sb.toString();
    }
}
