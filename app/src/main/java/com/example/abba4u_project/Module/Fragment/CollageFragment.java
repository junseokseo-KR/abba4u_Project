/*
    Student : 1544021 서준석 - 스티커뷰와 포토뷰 라이브러리를 이용하여 콜라주 프레그먼트 구현
 */
package com.example.abba4u_project.Module.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.abba4u_project.CustomTextSticker;
import com.example.abba4u_project.GetImageActivity;
import com.example.abba4u_project.GetTextStickerActivity;
import com.example.abba4u_project.ModifyImageActivity;
import com.example.abba4u_project.ModifyTextStickerActivity;
import com.example.abba4u_project.Module.Dialog.RemoveAllDIalog;
import com.example.abba4u_project.Module.Dialog.SaveDialog;
import com.example.abba4u_project.Module.Dialog.SelectDialog;
import com.example.abba4u_project.Module.Listener.RemoveAllDialogListener;
import com.example.abba4u_project.Module.Listener.SaveDialogListener;
import com.example.abba4u_project.Module.Listener.SelectDialogListener;
import com.example.abba4u_project.R;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerIconEvent;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.example.abba4u_project.staticData.SelectBitmap;
import static com.example.abba4u_project.staticData.SelectTextSticker;
import static com.example.abba4u_project.staticData.selectType;

public class CollageFragment extends Fragment implements View.OnClickListener {
    public CollageFragment() {
    }

    public static StickerView stickerView;
    CustomTextSticker sticker;
    private FloatingActionButton btnRemoveAll, btnLoad, btnSave,btnScreenShot, btnMain;
    private boolean isOpen = false;
    private Animation fab_open, fab_close, fab_main_rotate_open,fab_main_rotate_close;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //애니메이션 정의
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fab_main_rotate_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_main_rotate_open);
        fab_main_rotate_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_main_rotate_close);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.collage_fragment, container, false);
        stickerView = (StickerView) layout.findViewById(R.id.sticker_view);
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(),
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());
        BitmapStickerIcon modifyIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(getActivity(), R.drawable.baseline_brush_white_18dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        modifyIcon.setIconEvent(new ModifyIconEvent());


        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, modifyIcon));

        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        sticker = new CustomTextSticker(getActivity());

        sticker.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.sticker_transparent_background));
        sticker.setText("텍스트 입력");
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {
                    SelectTextSticker = (CustomTextSticker) sticker;
                    selectType = "text";
                } else {
                    SelectBitmap = ((BitmapDrawable) stickerView.getCurrentSticker().getDrawable()).getBitmap();
                    selectType = "img";
                }
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                stickerAdd();
            }
        });
        setBtn(layout);
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("코드", Integer.toString(requestCode));
        if (data != null) {
            switch (requestCode) {
                case 629:
                    makeTextSticker(stickerView, data.getStringExtra("textValue"), data.getIntExtra("textColor", 0xff000000));
                    break;
                case 630:
                    SelectTextSticker.setText(data.getStringExtra("textValue"));
                    SelectTextSticker.setTextColor(data.getIntExtra("textColor", 0xff000000));
                    SelectTextSticker.resizeText();
                    break;
            }
        }
    }

    public void stickerRemoveAll(View view) {
        if (stickerView.getStickerCount() != 0) {
            RemoveAllDIalog dIalog = new RemoveAllDIalog();
            dIalog.setDialogListener(new RemoveAllDialogListener() {
                @Override
                public void onPositiveClicked() {
                    stickerView.removeAllStickers();
                }
            });
            dIalog.show(getActivity().getSupportFragmentManager(), "removeAll");
        } else {
            Toast toast = Toast.makeText(getContext(), "초기화할 스티커가 없습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void stickerModify() {
        if (SelectBitmap == null && SelectTextSticker == null) {
            Toast.makeText(getActivity(), "선택된 스티커가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent;
            if (selectType.equals("text")) {
                intent = new Intent(getActivity().getApplicationContext(), ModifyTextStickerActivity.class);
                intent.putExtra("textValue", SelectTextSticker.getText().toString());
                intent.putExtra("textColor", String.valueOf(SelectTextSticker.getTextColor()));
                startActivityForResult(intent, 630);
                //630 : 텍스트 이미지 수정 리퀘스트 코드
            } else if (selectType.equals("img")) {
                intent = new Intent(getActivity().getApplicationContext(), ModifyImageActivity.class);
                startActivity(intent);
            }
        }
    }

    private void makeTextSticker(StickerView view, String txtValue, int txtColor) {
        TextSticker addTextSticker = new CustomTextSticker(getContext());
        addTextSticker.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.sticker_transparent_background));
        addTextSticker.setText(txtValue);
        addTextSticker.setTextColor(txtColor);
        addTextSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        addTextSticker.resizeText();
        view.addSticker(addTextSticker);
    }

    public void stickerLoad(View view) {
        SelectDialog dialog = new SelectDialog();
        dialog.setDialogListener(new SelectDialogListener() {
            Intent intent;
            @Override
            public void onImageStickerClicked() {
                intent = new Intent(getActivity().getApplicationContext(), GetImageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTextStickerClicked() {
                intent = new Intent(getActivity().getApplicationContext(), GetTextStickerActivity.class);
                startActivityForResult(intent, 629);
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(), "stickerLoad");
    }


    public void stickerAdd() {
        if (SelectBitmap == null && sticker == null) {
            Toast.makeText(getActivity(), "선택된 스티커가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            if (stickerView.getCurrentSticker() instanceof TextSticker) {
                Toast.makeText(getContext(), "준비중 입니다.", Toast.LENGTH_SHORT).show();
            } else if (stickerView.getCurrentSticker() instanceof Sticker) {
                stickerView.addSticker(new DrawableSticker(stickerView.getCurrentSticker().getDrawable()));
            }
        }
    }

    public void SaveImage(View v) {
        //결과를 저장하는 비트맵
        if (stickerView.getStickerCount() != 0) {
            SaveDialog dialog = new SaveDialog();
            dialog.setDialogListener(new SaveDialogListener() {
                @Override
                public void onPositiveClicked() {
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/Abba";
                    File file = new File(path);
                    if (!file.exists()){
                        file.mkdirs();
                        Toast.makeText(getContext(),"저장 폴더가 생성되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                    SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date();
                    FileOutputStream fos = null;
                    Bitmap resultBitmap = stickerView.createBitmap();
                    try{
                        fos = new FileOutputStream(path+"/결과물"+day.format(date)+".jpeg");
                        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+path+"/결과물"+day.format(date)+".jpeg")));
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNegativeClicked() {

                }
            });
            dialog.show(getActivity().getSupportFragmentManager(), "save");
        } else {
            Toast toast = Toast.makeText(getContext(), "저장할 결과물이 없습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void ScreenShot(View v){
        if (stickerView.getStickerCount() != 0) {
            Bitmap resultBitmap = stickerView.createBitmap();
            stickerView.addSticker(new DrawableSticker(new BitmapDrawable(resultBitmap)));
        }
        else{
            Toast toast = Toast.makeText(getContext(),"캡쳐할 영역이 비어있습니다.",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void coll_anim(View v){
        if (isOpen){
            btnMain.startAnimation(fab_main_rotate_close);
            btnLoad.startAnimation(fab_close);
            btnScreenShot.startAnimation(fab_close);
            btnRemoveAll.startAnimation(fab_close);
            btnSave.startAnimation(fab_close);
            isOpen = false;
        }else{
            btnMain.startAnimation(fab_main_rotate_open);
            btnLoad.startAnimation(fab_open);
            btnScreenShot.startAnimation(fab_open);
            btnRemoveAll.startAnimation(fab_open);
            btnSave.startAnimation(fab_open);

            isOpen = true;
        }
    }

    private void setBtn(View v) {
        btnMain = v.findViewById(R.id.btnMain);
        btnLoad = v.findViewById(R.id.btnLoad);
        btnRemoveAll = v.findViewById(R.id.btnRemoveAll);
        btnSave = v.findViewById(R.id.btnSave);
        btnScreenShot = v.findViewById(R.id.btnScreenShot);

        btnMain.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnRemoveAll.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnScreenShot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoad:
                stickerLoad(v);
                coll_anim(v);
                break;
            case R.id.btnRemoveAll:
                stickerRemoveAll(v);
                coll_anim(v);
                break;
            case R.id.btnSave:
                SaveImage(v);
                coll_anim(v);
                break;
            case R.id.btnScreenShot:
                ScreenShot(v);
                coll_anim(v);
                break;
            case R.id.btnMain:
                coll_anim(v);
                break;
        }
    }

    class ModifyIconEvent implements StickerIconEvent {

        @Override
        public void onActionDown(StickerView stickerView, MotionEvent event) {

        }

        @Override
        public void onActionMove(StickerView stickerView, MotionEvent event) {

        }

        @Override
        public void onActionUp(StickerView stickerView, MotionEvent event) {
            if (stickerView.getCurrentSticker() instanceof TextSticker) {
                SelectTextSticker = (CustomTextSticker) stickerView.getCurrentSticker();
                selectType = "text";
            } else if (stickerView.getCurrentSticker() instanceof Sticker) {
                SelectBitmap = ((BitmapDrawable) stickerView.getCurrentSticker().getDrawable()).getBitmap();
                selectType = "img";
            }
            stickerModify();
        }
    }
}