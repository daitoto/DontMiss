package com.example.daitoto.testapplication;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class doPost extends AsyncTask<HttpPost, Void, String> {
    public String doInBackground(HttpPost ...httpPosts) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;

        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 35000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 15000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 15000);

        String retStr;
        try {
            response = httpClient.execute(httpPosts[0]);
            retStr = parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            httpClient.getConnectionManager().shutdown();
            return null;
        }
        httpClient.getConnectionManager().shutdown();
        return retStr;
    }

    private String parseResponse(HttpResponse response) throws Exception {
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            return null;
        HttpEntity entity = response.getEntity();
        String logstr = EntityUtils.toString(entity, "UTF-8");
        return logstr;
    }

}
