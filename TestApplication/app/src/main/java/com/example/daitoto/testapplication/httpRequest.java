package com.example.daitoto.testapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.baidu.ocr.sdk.utils.HttpsClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class httpRequest extends AsyncTask<String, Void, String> {
    public String token = "";
    public InputStream inputStream = null;
    private Context mcontext;
    public httpRequest(Context context){
        mcontext = context;
    }
    protected String doInBackground(String ... paths) {
        try {
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            if(conn.getResponseCode() == 200) {
//                InputStream is = conn.getInputStream();
//                byte[] bt = new byte[1024];
//                is.read(bt);
//                String msg = bt.toString();
//                Log.v("get", msg);
//                return msg;
//            }
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = null;

            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 35000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, 15000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 15000);
            HttpGet httpGet = new HttpGet(paths[0]);

            String retStr;
            try {
                response = httpClient.execute(httpGet);
                retStr = parseResponse(response);
                token = retStr;
                JSONObject tok = new JSONObject(token);
                tok = new JSONObject(tok.getString("data"));
                Log.v("get", tok.getString("wechat_token"));
                token = tok.getString("wechat_token");
                JSONObject req = new JSONObject();
                try {
                    req.accumulate("query", paths[1]);
                    req.accumulate("city", "北京");
                    req.accumulate("category", "remind");
                    req.accumulate("appid", "wxaaaaaaaaaaaaaaaa");
                    req.accumulate("uid", "123456");
                } catch (Exception er) {
                    er.printStackTrace();
                }
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/semantic/semproxy/search?access_token=" + token);
                String reqs = req.toString();
                Log.i("req", reqs);
                StringEntity se = new StringEntity(reqs, HTTP.UTF_8);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpClient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();

                // 10. convert inputstream to string
                if(inputStream != null) {
                    retStr = convertInputStreamToString(inputStream);
                    JSONObject respj = new JSONObject(retStr);
                    retStr = respj.getString("semantic");

                    respj = new JSONObject(retStr);
                    retStr = respj.getString("details");
                    Log.v("retStr", retStr);
                    respj = new JSONObject(retStr);
                    String event = respj.getString("event");
                    respj = new JSONObject(respj.getString("datetime"));
                    String date = respj.getString("date");
                    retStr = event + '\n' + date;
                }
                else
                    retStr = "Did not work!";

                Log.i("post", retStr);

            } catch (Exception e) {
                e.printStackTrace();
                httpClient.getConnectionManager().shutdown();
                return null;
            }
            httpClient.getConnectionManager().shutdown();
            return retStr;

        } catch (Exception er) {
            er.printStackTrace();
        }
        return "error";
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    protected void onPostExecute(String res){
        Log.v("excute", res);
        Intent intent = new Intent();
        intent.setClass(mcontext, AcuqActivity.class);
        intent.putExtra(AcuqActivity.RETURN_INFO, res);
        mcontext.startActivity(intent);
//        Activity.startActivity(intent);
//        return res;
    }
    private  static String parseResponse(HttpResponse response) throws Exception {
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            return null;
        HttpEntity entity = response.getEntity();
        String logstr = EntityUtils.toString(entity, "UTF-8");
        return logstr;
    }
}
