package com.example.daitoto.testapplication;

import android.content.Entity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PostJson {
    static public JSONObject post(String s_url, JSONObject obj) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        //String encoderJson = URLEncoder.encode(obj.toString(), HTTP.UTF_8);
        String encoderJson = obj.toString();
        HttpPost httpPost = new HttpPost(s_url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

        StringEntity se = new StringEntity(encoderJson, HTTP.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(se);
        String response = new doPost().execute(httpPost).get();
        JSONObject ret = parseResponse(response);
        return ret;
    }

    static public JSONObject get(String s_url, JSONObject obj) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Iterator<String> itr = obj.keys();
        while(itr.hasNext()) {
            String key = itr.next();
            params.add(new BasicNameValuePair(key, obj.getString(key)));
        }
        //要传递的参数.　
        String url = s_url + URLEncodedUtils.format(params, HTTP.UTF_8);
        HttpGet get = new HttpGet(url);
        String response =  new doGet().execute(get).get();
        JSONObject ret = parseResponse(response);

        return ret;
    }

    static private JSONObject parseResponse(String str) throws Exception {
        JSONObject json = new JSONObject(str);
        return json;
    }
}
