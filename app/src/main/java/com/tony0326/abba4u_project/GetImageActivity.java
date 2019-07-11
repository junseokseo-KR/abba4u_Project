/*
    Student : 1544021 서준석 - 갤러리에서 사진 불러오기 구현
 */
package com.tony0326.abba4u_project;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.tony0326.abba4u_project.Module.Fragment.CollageFragment;
import com.tony0326.abba4u_project.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.xiaopo.flying.sticker.DrawableSticker;

import java.io.File;
import java.util.List;

public class GetImageActivity extends Activity {
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    Button btnMode;
    Drawable img;
    private FloatingActionButton btnCanvasReset, btnCut;
    private boolean isOpen = false;
    private Animation fab_open, fab_close;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getsticker_layout);
        tedPermission();
        staticData.cutMode = false;
        staticData.cut = false;

        btnMode = findViewById(R.id.btnMode);
        btnCanvasReset = findViewById(R.id.btnCanvasReset);
        btnCut = findViewById(R.id.btnCut);

        btnMode.setText("줌,이동");
        img = getApplicationContext().getDrawable(R.drawable.zoom);
        btnMode.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(staticData.cutMode);
            }
        });

        btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staticData.cut == true) {
                    staticData.sv.showcropdialog(703);
                }else if(staticData.cut == false){
                    Toast toast = Toast.makeText(getApplicationContext(), "선택된 영역이 없습니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });

        btnCanvasReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "준비중 입니다.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });

        //애니메이션 정의
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
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

    public void changeMode(boolean mode){
        if (!mode){
            btnMode.setText("자르기");
            img = getApplicationContext().getDrawable(R.drawable.lasso);
        }else{
            btnMode.setText("줌,이동");
            img = getApplicationContext().getDrawable(R.drawable.zoom);
        }
        call_anim();
        staticData.cutMode = !staticData.cutMode;
        btnMode.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);
    }

    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(),options);
        staticData.MainBitmap=originBm;
        staticData.sv.getMainBitmapImage();
    }

    public void addImage(View v) {
        if(staticData.MainBitmap!=null){
            finish();
            CollageFragment.stickerView.addSticker(new DrawableSticker(new BitmapDrawable(staticData.MainBitmap)));
            staticData.MainBitmap=null;
        }else
        {
            Toast.makeText(getApplicationContext(),"이미지가 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    private void call_anim(){
        if (isOpen){
            btnCanvasReset.startAnimation(fab_close);
            btnCut.startAnimation(fab_close);

            btnCanvasReset.setClickable(false);
            btnCut.setClickable(false);
            isOpen = false;
        }else{
            btnCanvasReset.startAnimation(fab_open);
            btnCut.startAnimation(fab_open);

            btnCanvasReset.setClickable(true);
            btnCut.setClickable(true);
            isOpen = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        staticData.CutBitmap=null;
        staticData.MainBitmap=null;
    }
}
