package com.example.daitoto.testapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.os.Environment;
import android.net.Uri;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ViewPager mViewPager;
    private ImageView mImageView;
    private ImageView iv_CameraImg;
    private static final int REQUEST_IMAGE_CAPTURE_THUMB = 1;
    private static final int REQUEST_IMAGE_CAPTURE_FULL = 2;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private static final String CAMERA_DIR = "/dcim/";
    private static final String albumName ="CameraSample";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calender:
                    mTextMessage.setText(R.string.title_calender);
                    button_calender_click();
                    return true;
                case R.id.navigation_society:
                    mTextMessage.setText(R.string.title_society);
                    button_society_click();
                    return true;
                case R.id.navigation_user:
                    mTextMessage.setText(R.string.title_user);
                    button_user_click();
                    return true;
            }
            return false;
        }
    };

    public void button_calender_click(){
        //TODO
    }

    public void button_society_click(){
        //TODO
    }

    public void button_user_click(){
        //TODO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_CameraImg = (ImageView) findViewById(R.id.camera);
        verifyStoragePermissions(MainActivity.this);
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
        final Dialog mdialog = new Dialog(MainActivity.this, R.style.photo_dialog);
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
        File file = new File(mPublicPhotoPath);
        Uri uri = Uri.fromFile(file);
        Log.i("toto", uri.getEncodedPath());
        iv_CameraImg.setImageURI(uri);

    }

}
