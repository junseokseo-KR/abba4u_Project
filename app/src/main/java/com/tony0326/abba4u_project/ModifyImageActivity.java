package com.tony0326.abba4u_project;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.tony0326.abba4u_project.Module.Fragment.CollageFragment;
import com.tony0326.abba4u_project.R;
import com.xiaopo.flying.sticker.DrawableSticker;
import static com.tony0326.abba4u_project.staticData.CutBitmap;
import static com.tony0326.abba4u_project.staticData.MainBitmap;
import static com.tony0326.abba4u_project.staticData.SelectBitmap;
import static com.tony0326.abba4u_project.staticData.cut;
import static com.tony0326.abba4u_project.staticData.cutMode;
import static com.tony0326.abba4u_project.staticData.sv;

public class ModifyImageActivity extends Activity {
    Button btnMode;
    Drawable img;
    private FloatingActionButton btnCanvasReset, btnCut;
    private boolean isOpen = false;
    private Animation fab_open, fab_close;

    @Override
    protected void onStart() {

        super.onStart();
        if(SelectBitmap!=null)
        {
            SelectBitmap = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifysticker_layout);
        if(SelectBitmap!=null)
        {
            MainBitmap=SelectBitmap;
            sv.getMainBitmapImage();
        }
        cutMode = false;
        cut = false;

        btnMode = findViewById(R.id.btnMode_Modify);
        btnCanvasReset = findViewById(R.id.btnCanvasReset_Modify);
        btnCut = findViewById(R.id.btnCut_Modify);


        btnMode.setText("줌,이동");
        img = getApplicationContext().getDrawable(R.drawable.zoom);
        btnMode.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(cutMode);
            }
        });

        btnCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cut == true) {
                    sv.showcropdialog(704);
                }else if(cut == false){
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



    public void modifyImage(View v) {
        if (CutBitmap != null) {
            finish();
            CollageFragment.stickerView.replace(new DrawableSticker(new BitmapDrawable(CutBitmap)));
        } else if(MainBitmap!=null){
            finish();
            CollageFragment.stickerView.replace(new DrawableSticker(new BitmapDrawable(MainBitmap)));
        }else
        {
            Toast.makeText(getApplicationContext(),"이미지가 없습니다.",Toast.LENGTH_SHORT).show();
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
        cutMode = !cutMode;
        btnMode.setCompoundDrawablesWithIntrinsicBounds(null,img,null,null);
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
        CutBitmap=null;
        MainBitmap=null;
    }
}
