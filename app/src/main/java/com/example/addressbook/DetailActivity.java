package com.example.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @ProjectName: AddressBook
 * @Package: com.example.addressbook
 * @QQ: 1025377230
 * @Author: Fonrye
 * @CreateDate: 2021/4/8 11:54
 * @Email: fonrye@163.com
 * @Version: 1.0
 */
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class DetailActivity extends Activity implements View.OnClickListener {

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private ImageView mImageHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Information");
        setupViews();

        TextView tvTitle = (TextView) this.findViewById(R.id.detail_title);
        final EditText edtNo = (EditText) this.findViewById(R.id.Et_no);
        final EditText edtName = (EditText) this.findViewById(R.id.Et_name);
        final EditText edtPNumber = (EditText) this.findViewById(R.id.Et_phone);

        Bundle bundle = getIntent().getExtras();
        final boolean addOrNot = bundle.getBoolean("AddorNot");
        if (addOrNot) {
            tvTitle.setText(getResources().getString(R.string.titleAdd));
            edtNo.setText("");
            edtName.setText("");
            edtPNumber.setText("");
        } else {
            tvTitle.setText(getResources().getString(R.string.titleModify));
            edtNo.setFocusable(false);
            edtNo.setText(bundle.getString("oldNo"));
            edtName.setText(bundle.getString("oldName"));
            edtPNumber.setText(bundle.getString("oldPNumber"));
        }

        Button btnConfirm = (Button) this.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNo = edtNo.getText().toString();
                String newName = edtName.getText().toString();
                String newPNumber = edtPNumber.getText().toString();

                /*if (!newNo.matches("^[1-9]\\d{7}$")
                        || !newPNumber.matches("^[1-9]\\d{10}$")
                        || newName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msgWarning),
                            Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Intent intent = new Intent();
                intent.putExtra("_newNo", newNo);
                intent.putExtra("_newName", newName);
                intent.putExtra("_newPNumber", newPNumber);
                int resultCode = 0;
                if (addOrNot)
                    resultCode = 1;
                else
                    resultCode = 2;

                DetailActivity.this.setResult(resultCode, intent);
                DetailActivity.this.finish();
            }
        });
    }

    private void setupViews() {
        mImageHeader = (ImageView) findViewById(R.id.image_header);
        final Button selectBtn1 = (Button) findViewById(R.id.btn_selectimage);
        final Button selectBtn2 = (Button) findViewById(R.id.btn_takephoto);
        selectBtn1.setOnClickListener(this);
        selectBtn2.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int resultCode = 0;
            DetailActivity.this.setResult(resultCode);
            DetailActivity.this.finish();
        }
        return true;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.menu_detail, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectimage:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
                break;
            case R.id.btn_takephoto:
                if (isSdcardExisting()) {
                    Intent cameraIntent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");//拍照
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                    cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
//                        Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！",
//                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private Uri getImageUri() {//获取路径
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    private boolean isSdcardExisting() {

        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void resizeImage(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            mImageHeader.setImageDrawable(drawable);
        }
    }
}
