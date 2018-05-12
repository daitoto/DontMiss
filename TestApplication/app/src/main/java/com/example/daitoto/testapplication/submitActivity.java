package com.example.daitoto.testapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SubmitActivity extends AppCompatActivity {

    private ImageView img_view;
    private int photo_num = 0;
    private ImageButton add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        img_view = (ImageView) findViewById(R.id.imgview_1);
        add_btn = (ImageButton) findViewById(R.id.imgbtn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_photos();

            }
        });
//        img_view.setImageBitmap();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calender:
                    button_calender_click();
                    return true;

                case R.id.navigation_society:
                    button_society_click();
                    return true;

                case R.id.navigation_user:
                    button_user_click();
                    return true;
            }
            return false;
        }
    };
    public void button_calender_click(){
        finish();
        //TODO
    }

    public void button_society_click(){
    }

    public void button_user_click(){
        //TODO
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
        final Dialog mdialog = new Dialog(SubmitActivity.this, R.style.photo_dialog);
        mdialog.setContentView(View.inflate(SubmitActivity.this, R.layout.popup_window, null));
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
//                Toast.makeText(MainActivity.this, "Opening Album",Toast.LENGTH_SHORT).show();

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {

            Intent intent = null;
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
//                Toast.makeText(MainActivity.this, "Opening Camera",Toast.LENGTH_SHORT).show();
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
                Uri uri = Uri.fromFile(photoFile);
                intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
            }

        });
        mdialog.show();

    }

    String mPublicPhotoPath;
    private File createImageFile() throws IOException {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        Log.i("toto", "path:" + path.getAbsolutePath());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        Log.i("toto", path + "/" + imageFileName + ".jpg");
        File image = File.createTempFile(imageFileName,".jpg", path);
        mPublicPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("toto", " res:" + resultCode);
        if(resultCode != Activity.RESULT_OK) return;
        if(photo_num == 3) {
            return;
        }
        File file = new File(mPublicPhotoPath);
        Uri uri = Uri.fromFile(file);
        Log.i("toto", uri.getEncodedPath());
        img_view.setImageURI(uri);
        Log.v("toto", "submit");
        photo_num += 1;
        Log.v("submit", "photos");
        add_btn.setX(photo_num * 200);
        if(photo_num == 2)
            img_view = (ImageView) findViewById(R.id.imgview_3);
            add_btn.setVisibility(View.INVISIBLE);
        else
            img_view = (ImageView) findViewById(R.id.imgview_2);

    }
}
