package com.example.daitoto.testapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.Menu;
import android.view.Window;
import android.view.View;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ListPopupWindow;
import android.widget.AdapterView;
import android.app.Dialog;
import android.widget.Button;
import android.app.Fragment;
import android.view.WindowManager;
import android.view.Gravity;
import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id. btn_add:
                add_photos();
                break;

            default:
                break;

        }
        return true;
    }

    private  void add_photos()
    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final Dialog mdialog = new Dialog(MainActivity.this);
        mdialog.setContentView(View.inflate(MainActivity.this, R.layout.popup_window, null));
        // 弹出对话框
        Window window = mdialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.y = 20;
        window.setContentView(R.layout.popup_window);
        final Button cancel = (Button) window.findViewById(R.id.btn_cancel);
        final Button album = (Button) window.findViewById((R.id.btn_album));
        final Button camera = (Button) window.findViewById((R.id.btn_camera));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                photoActivity();
                mdialog.dismiss();
                Toast.makeText(MainActivity.this, "Opening Album",Toast.LENGTH_SHORT).show();

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takePhoto();
                mdialog.dismiss();
                Toast.makeText(MainActivity.this, "Opening Camera",Toast.LENGTH_SHORT).show();

            }
        });
        mdialog.show();

    }

}
