/*
    Student : 1544021 서준석 - 갤러리에서 사진 불러오기 구현
 */
package com.example.abba4u_project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.abba4u_project.Module.Fragment.CollageFragment;
import com.github.chrisbanes.photoview.PhotoView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.xiaopo.flying.sticker.DrawableSticker;

import java.io.File;
import java.util.List;

import static com.example.abba4u_project.staticData.CutBitmap;
import static com.example.abba4u_project.staticData.MainBitmap;

public class GetImageActivity extends Activity {
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    PhotoView photoView;
    Bitmap sendBitmap;
    @Override
    protected void onStart() {

        super.onStart();
        if(CutBitmap!=null)
        {
            photoView.setImageDrawable(new BitmapDrawable(CutBitmap));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);
        tedPermission();
        photoView = findViewById(R.id.photoview);
    }
    private void tedPermission(){
        PermissionListener permissionListener =  new PermissionListener(){

            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.with(this).setPermissionListener(permissionListener).setRationaleMessage(R.string.permission_rational).setDeniedMessage(R.string.permission_deny).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA).check();
    }
    public void goToAlbum(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }
    public void goToCut(View v){
        Intent intent = new Intent(getApplicationContext(), LassoCutActivity.class);
        // sendBitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();

        //TODO : 비트맵 널값 체크!!
        if(MainBitmap != null){
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),R.string.NoSettingImg,Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_FROM_ALBUM) {
            if (data != null) {
                Uri photoUri = data.getData();
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = new File(cursor.getString(column_index));
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                setImage();
            }
        }
    }

    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(),options);
        MainBitmap=originBm;
        photoView.setImageBitmap(originBm);
    }

    public void addImage(View v) {


        if (CutBitmap != null) {
            finish();

            CollageFragment.stickerView.addSticker(new DrawableSticker(new BitmapDrawable(CutBitmap)));
        } else if(MainBitmap!=null){
            finish();
            CollageFragment.stickerView.addSticker(new DrawableSticker(new BitmapDrawable(MainBitmap)));
        }else
        {
            Toast.makeText(getApplicationContext(),"이미지가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CutBitmap=null;
        MainBitmap=null;
    }

}
