package com.example.abba4u_project.Module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.abba4u_project.R;

import static com.example.abba4u_project.Module.staticData.inputstate;

public class CropActivity extends AppCompatActivity {

    ImageView compositeImageView;
    boolean crop;
    boolean d=true;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        d=false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        d = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crop = extras.getBoolean("crop");
        }

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        int widthOfscreen = 0;
        int heightOfScreen = 0;

        DisplayMetrics dm = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        } catch (Exception ex) {
        }
        widthOfscreen = dm.widthPixels;
        heightOfScreen = dm.heightPixels;

        compositeImageView = (ImageView) findViewById(R.id.imageview);


        final Bitmap resultingImage = Bitmap.createBitmap(widthOfscreen, heightOfScreen, bitmap.getConfig());
        // Bitmap resultingImage = Bitmap.createBitmap(500, 500, bitmap.getConfig());

        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        for (int i = 0; i < SomeView.points.size(); i++) {
            path.lineTo(SomeView.points.get(i).x, SomeView.points.get(i).y);
        }
        canvas.drawPath(path, paint);
        if (crop) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        }

        canvas.drawBitmap(bitmap, 0, 0, paint);
        compositeImageView.setImageBitmap(resultingImage);
        inputstate=true;

        new Thread(){
            @Override
            public void run(){
                SizeCutting sc=new SizeCutting(resultingImage);
                while(d&&sc.th.isAlive())
                {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!d)
                {
                    sc.SizeCutting_kill();
                }
                finish();
            }
        }.start();
    }
}