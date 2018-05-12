package com.example.daitoto.testapplication;

import android.util.Log;
import com.example.daitoto.testapplication.PostJson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


public class UserStatus {
    private String user_name;
    private boolean isLoggedin;

    public UserStatus() {
        user_name = null;
        isLoggedin = false;
    }

    public String get_username() {
        return user_name;
    }

    public boolean is_loggedin() {
        return isLoggedin;
    }

    public int register(String u_n, String pwd, String pwd_confirm) {
        JSONObject postinfo = new JSONObject();
        try {
            if (!pwd.equals(pwd_confirm)) {
                Log.v("wuhan", "passwords don't match!");
                return -1;
            }
            postinfo.put("username", u_n);
            postinfo.put("passwd", pwd);
            JSONObject retJson = PostJson.post("http://" + Variables.server_addr + ":" + Variables.server_port, postinfo);
            if (retJson == null) {
                isLoggedin = false;
                return -1;
            }
            if (!retJson.getString("code").equals("200")) {
                isLoggedin = false;
                return -1;
            }
            String retData = retJson.getString("data");
            JSONObject innerJson = new JSONObject(retData);

            if (innerJson.getString("status").equals("new")) {
                Log.v("wuhan", "a new user registered!");
                return 1;
            } else if (innerJson.getString("status").equals("old")) {
                Log.v("wuhan", "an old user logged in!");
                return 2;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            isLoggedin = false;
            return -1;
        }
    }

    public int login(String u_n, String pwd) {
        JSONObject postinfo = new JSONObject();
        try {
            postinfo.put("username", u_n);
            postinfo.put("passwd", pwd);
            JSONObject retJson = PostJson.post("http://" + Variables.server_addr + ":" + Variables.server_port, postinfo
            );
            if (retJson == null) {
                isLoggedin = false;
                return -1;
            }
            if (!retJson.getString("code").equals("200")) {
                isLoggedin = false;
                return -1;
            }
            String retData = retJson.getString("data");
            JSONObject innerJson = new JSONObject(retData);

            if (innerJson.getString("status").equals("new")) {
                Log.v("wuhan", "a new user registered!");
                return 2;
            } else if (innerJson.getString("status").equals("old")) {
                Log.v("wuhan", "an old user logged in!");
                return 1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            isLoggedin = false;
            return -1;
        }
    }
}

