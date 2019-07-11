package com.tony0326.abba4u_project;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tony0326.abba4u_project.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.tony0326.abba4u_project.staticData.MainBitmap;
import static com.tony0326.abba4u_project.staticData.cut;
import static com.tony0326.abba4u_project.staticData.cutMode;
import static com.tony0326.abba4u_project.staticData.sv;

public class SomeView extends View implements View.OnTouchListener {
    final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound = soundPool.load(getContext(), R.raw.cut_sound,1);
    final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    Canvas c;
    private Paint paint;
    public static List<Point> points;
    int DIST = 2;
    boolean flgPathDraw = true;

    Point mfirstpoint = null;
    boolean bfirstpoint = false;

    Point mlastpoint = null;

    Bitmap bitmap=null;
    byte[] byteArray;

    Context mContext;
    int height;
    int width;

    public SomeView(Context c) {
        super(c);
        mContext=c;
        sv=this;
        if(MainBitmap!=null)
            getMainBitmapImage();

    }

    public SomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);

        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(bitmap!=null){
            c = canvas;
            canvas.drawBitmap(bitmap, 0, 0, null);

            Log.i("위치",cutMode+"");
            if (cutMode) {
                Path path = new Path();
                boolean first = true;
                for (int i = 0; i < points.size(); i += 2) {
                    Point point = points.get(i);
                    if (first) {
                        first = false;
                        path.moveTo(point.x, point.y);
                        cut = true;
                    } else if (i < points.size() - 1) {
                        Point next = points.get(i + 1);
                        path.quadTo(point.x, point.y, next.x, next.y);
                    } else {
                        mlastpoint = points.get(i);
                        path.lineTo(point.x, point.y);
                    }
                }
                canvas.drawPath(path, paint);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        if(bitmap!=null)
        {
            if (cutMode) {
                Point point = new Point();
                point.x = (int) event.getX();
                point.y = (int) event.getY();

                soundPool.play(sound,1,1,0,0,0);
                vibrator.vibrate(10);

                if (flgPathDraw) {

                    if (bfirstpoint) {

                        if (comparepoint(mfirstpoint, point)) {
                            // points.add(point);
                            points.add(mfirstpoint);
                            flgPathDraw = false;
                        } else {
                            points.add(point);
                        }
                    } else {
                        points.add(point);
                    }

                    if (!(bfirstpoint)) {

                        mfirstpoint = point;
                        bfirstpoint = true;
                    }
                }

                invalidate();
                Log.e("Hi  ==>", "Size: " + point.x + " " + point.y);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("Action up*******~~~>>>>", "called");
                    mlastpoint = point;
                    if (flgPathDraw) {
                        if (points.size() > 12) {
                            if (!comparepoint(mfirstpoint, mlastpoint)) {
                                flgPathDraw = false;
                                points.add(mfirstpoint);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void getMainBitmapImage(){
        if(MainBitmap!=null)
        {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;

            Bitmap original = MainBitmap;

            float scale = (float) ((width / (float) original.getWidth()));

            int image_w = (int) (original.getWidth() * scale);
            int image_h = (int) (original.getHeight() * scale);

            bitmap = Bitmap.createScaledBitmap(original, image_w, image_h, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();


            setFocusable(true);
            setFocusableInTouchMode(true);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
            paint.setStrokeWidth(5);
            paint.setColor(Color.RED);

            this.setOnTouchListener(this);
            points = new ArrayList<Point>();

            bfirstpoint = false;
        }
    }

    private boolean comparepoint(Point first, Point current) {
        int left_range_x = (int) (current.x - 3);
        int left_range_y = (int) (current.y - 3);

        int right_range_x = (int) (current.x + 3);
        int right_range_y = (int) (current.y + 3);

        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {
            if (points.size() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public void showcropdialog(final int requestCode) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent;
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        intent = new Intent(mContext, CropActivity.class);
                        intent.putExtra("crop", true);
                        intent.putExtra("image", byteArray);
                        intent.putExtra("requestCode",requestCode);
                        ((Activity) mContext).finish();
                        mContext.startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        intent = new Intent(mContext, CropActivity.class);
                        intent.putExtra("crop", false);
                        intent.putExtra("image", byteArray);
                        intent.putExtra("requestCode",requestCode);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();
                        bfirstpoint = false;
                        break;
                        case DialogInterface.BUTTON_NEUTRAL:
                            dialog.dismiss();
                }
            }
        };
        if(bitmap!=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("선택 영역의 어느 부분을 자르시겠습니까?")
                    .setPositiveButton("안쪽", dialogClickListener)
                    .setNegativeButton("바깥쪽", dialogClickListener)
                    .setNeutralButton("취소",dialogClickListener).show()
                    .setCancelable(false);
        }

    }
}


class Point {
    float x, y;

    @Override
    public String toString() {
        return x + ", " + y;
    }
}