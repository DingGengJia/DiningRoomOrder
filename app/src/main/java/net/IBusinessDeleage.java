package net;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gavin on 10/4/16.
 */
public interface IBusinessDeleage {

    public void onProcessSuccess(BusinessRequest request, int statusCode, Header[] headers, byte[] response, IBusinessDeleage deleage);


    public void onProcessFailed(BusinessRequest request, int statusCode, Header[] headers, byte[] response);

}
