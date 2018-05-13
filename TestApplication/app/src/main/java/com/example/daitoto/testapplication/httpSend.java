package com.example.daitoto.testapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class httpSend extends AsyncTask<String, Void, String> {


    protected String doInBackground(String ... paths) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = null;

            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 35000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 15000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 15000);
            HttpPost httpPost = new HttpPost(paths[0]);
            JSONObject req = new JSONObject();
            req.accumulate("name", paths[1]);
            req.accumulate("start_date", paths[2]);
            String reqs = req.toString();
            Log.i("req", reqs);
            StringEntity se = new StringEntity(reqs, HTTP.UTF_8);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Cookie", Variables.cookies);
            Log.v("cookies_semd", Variables.cookies);


            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpClient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            String retStr;
            if(inputStream != null) {
                retStr = convertInputStreamToString(inputStream);
            }
            else
                retStr = "NONE";
            Log.i("rrrr", retStr);
            return "sended";
        } catch (Exception er) {
            er.printStackTrace();
        }
        return "wrong";
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
