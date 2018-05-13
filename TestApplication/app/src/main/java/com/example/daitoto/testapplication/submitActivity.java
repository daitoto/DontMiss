package com.example.daitoto.testapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.LinearLayout;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.Word;
import com.baidu.ocr.sdk.exception.OCRError;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;

import com.example.daitoto.testapplication.httpRequest;

import org.json.JSONObject;

public class SubmitActivity extends AppCompatActivity {

    private ImageView img_view;
    private int photo_num = 0;
    private ImageButton add_btn;
    private String[] file_paths = new String[4];
    private int flag_done = 0;
    private int type_radio = 0;
    private String[] sb = new String[4];

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
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

    private void GetReminder(String res){
        httpRequest htget = new httpRequest(SubmitActivity.this);
        htget.execute("http://13.58.195.209:8080/get_wechat_token", res);
        String token = htget.token;
        Log.i("GetReminder", token);

//        try{
//            return  new JSONObject(resp);
//        } catch (Exception er)
//        {
//            er.printStackTrace();
//        }
    }

//    public void Acu(String res){
//        Intent intent = new Intent();
//        intent.setClass(SubmitActivity.this, AcuqActivity.class);
//        intent.putExtra(AcuqActivity.RETURN_INFO, res);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Log.i("init", "initing");
        verifyStoragePermissions(SubmitActivity.this);
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
                Log.v("baidu", token);
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                error.printStackTrace();
            }
        }, getApplicationContext(), "rNy1WWx02WfnvL4oN3NoQDlH", "Pd13xYGX1G8Ot70Zbm3EtaMfFZYYGuDS");
        img_view = (ImageView) findViewById(R.id.imgview_1);
        add_btn = (ImageButton) findViewById(R.id.imgbtn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_photos();

            }
        });
        final Button sub_btn = (Button) findViewById(R.id.btn_sub);
        final RadioGroup radgroup = (RadioGroup) findViewById(R.id.radio_g);
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < radgroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);
                    if (rd.isChecked()) {
                        type_radio = i;
                        break;
                    }
                }
                switch (type_radio)
                {
                    case 0:
                        //sj

                        String[] res = sb[0].split("\n");
                        String ans = res[0];
                        for(int i = 0; i < res.length; i += 1){
                            if (res[i].length() > ans.length())
                                    ans = res[i];
                        }
                        Log.v("ans", ans);
                        GetReminder(ans);
//                        Log.v("res", str);
                        break;

                    case 1:
                        //sw

                        //TODO
                        break;

                    case 2:
                        //hzp
                        //TODO
                        break;

                    default:
                        break;
                }
                Log.v("sb", sb[0] + sb[1] + sb[2] + String.valueOf(type_radio));
//                sendmsg();
            }
        });

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
        Intent intent = new Intent();
        intent.setClass(SubmitActivity.this, MainActivity.class);
        startActivity(intent);
        //TODO
    }

    public void button_society_click(){
        //TODO
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
                Toast.makeText(SubmitActivity.this, "Opening Album",Toast.LENGTH_SHORT).show();
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                }
//                catch (IOException ex){
//                    ex.printStackTrace();
//                }
//                Uri uri = Uri.fromFile(photoFile);

                Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intentToPickPic.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intentToPickPic, 1);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {

            Intent intent = null;
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
                Toast.makeText(SubmitActivity.this, "Opening Camera",Toast.LENGTH_SHORT).show();
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

    private String parseFilePath(Uri uri) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("toto", " res:" + resultCode);
        Uri uri;
        switch (requestCode) {
            case 0:
                if (resultCode != Activity.RESULT_OK) return;
                if (photo_num == 3) {
                    return;
                }
                File file = new File(mPublicPhotoPath);
                uri = Uri.fromFile(file);
                file_paths[photo_num] = mPublicPhotoPath;
                Log.i("toto", uri.getEncodedPath());
                img_view.setImageURI(uri);
                Log.v("toto", "submit");
                photo_num += 1;
                Log.v("submit", "photos");
                add_btn.setX(photo_num * 200);
                if (photo_num == 3)
                    add_btn.setVisibility(View.INVISIBLE);
                if (photo_num == 2) {
                    img_view = (ImageView) findViewById(R.id.imgview_3);
                } else
                    img_view = (ImageView) findViewById(R.id.imgview_2);
                sumbit(mPublicPhotoPath);
                break;

            case 1:
                if (resultCode != Activity.RESULT_OK) return;
                if (photo_num == 3) {
                    return;
                }
                uri = data.getData();
                file_paths[photo_num] = parseFilePath(uri);
                Log.i("album", parseFilePath(uri));
                img_view.setImageURI(uri);
                photo_num += 1;
                add_btn.setX(photo_num * 200);
                if (photo_num == 3)
                    add_btn.setVisibility(View.INVISIBLE);
                if (photo_num == 2) {
                    img_view = (ImageView) findViewById(R.id.imgview_3);
                } else
                    img_view = (ImageView) findViewById(R.id.imgview_2);
                sumbit(file_paths[photo_num - 1]);
                break;
        }
    }

    public void sumbit(String filePath){

        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(false);
        param.setImageFile(new File(filePath));

        OCR.getInstance().recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                // 调用成功，返回GeneralResult对象
                for (WordSimple wordSimple : result.getWordList()) {
                    // wordSimple不包含位置信息
                    WordSimple word = wordSimple;
                   sb[flag_done] += word.getWords() + "\n";
                }
                Log.v("sb", sb[flag_done]);
//                Log.v("signal", String.valueOf(type_radio));
                flag_done = flag_done + 1;
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                error.printStackTrace();
            }
        });
    }
}
