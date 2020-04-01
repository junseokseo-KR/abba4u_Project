/*
    Student : 1544021 서준석 - 스티커뷰와 포토뷰 라이브러리를 이용하여 콜라주 프레그먼트 구현
 */
package com.tony0326.abba4u_project.Module.Fragment;

import android.content.Intent;
import android.database.Cursor;
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

import com.tony0326.abba4u_project.CustomStickerView;
import com.tony0326.abba4u_project.CustomTextSticker;
import com.tony0326.abba4u_project.GetImageActivity;
import com.tony0326.abba4u_project.GetTextStickerActivity;
import com.tony0326.abba4u_project.ImageAsyncTask;
import com.tony0326.abba4u_project.MainActivity;
import com.tony0326.abba4u_project.ModifyImageActivity;
import com.tony0326.abba4u_project.ModifyTextStickerActivity;
import com.tony0326.abba4u_project.Module.Dialog.DescriptionDialog;
import com.tony0326.abba4u_project.Module.Dialog.RemoveAllDIalog;
import com.tony0326.abba4u_project.Module.Dialog.SaveDialog;
import com.tony0326.abba4u_project.Module.Dialog.SelectDialog;
import com.tony0326.abba4u_project.Module.Listener.RemoveAllDialogListener;
import com.tony0326.abba4u_project.Module.Listener.SaveDialogListener;
import com.tony0326.abba4u_project.Module.Listener.SelectDialogListener;
import com.tony0326.abba4u_project.R;
import com.tony0326.abba4u_project.UserAsyncTask;
import com.tony0326.abba4u_project.staticData;
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
import java.util.concurrent.ExecutionException;

import static com.tony0326.abba4u_project.staticData.coll_content;
import static com.tony0326.abba4u_project.staticData.coll_title;
import static com.tony0326.abba4u_project.staticData.result_coll_uri;
import static com.tony0326.abba4u_project.staticData.userID;

public class CollageFragment extends Fragment implements View.OnClickListener {
    public CollageFragment() {
    }

    public static CustomStickerView stickerView;
    CustomTextSticker sticker;
    private FloatingActionButton btnRemoveAll, btnLoad, btnSave, btnUser, btnMain;
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
        stickerView = (CustomStickerView) layout.findViewById(R.id.sticker_view);
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
        modifyIcon.setIconEvent(new ModifyIconEvent());;


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
                    staticData.SelectTextSticker = (CustomTextSticker) sticker;
                    staticData.selectType = "text";
                } else {
                    staticData.SelectBitmap = ((BitmapDrawable) stickerView.getCurrentSticker().getDrawable()).getBitmap();
                    staticData.selectType = "img";
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
                    staticData.SelectTextSticker.setText(data.getStringExtra("textValue"));
                    staticData.SelectTextSticker.setTextColor(data.getIntExtra("textColor", 0xff000000));
                    staticData.SelectTextSticker.resizeText();
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
            showToast("초기화할 스티커가 없습니다.");
        }
    }

    public void stickerModify() {
        if (staticData.SelectBitmap == null && staticData.SelectTextSticker == null) {
            showToast("선택된 스티커가 없습니다.");
        } else {
            Intent intent;
            if (staticData.selectType.equals("text")) {
                intent = new Intent(getActivity().getApplicationContext(), ModifyTextStickerActivity.class);
                intent.putExtra("textValue", staticData.SelectTextSticker.getText().toString());
                intent.putExtra("textColor", String.valueOf(staticData.SelectTextSticker.getTextColor()));
                startActivityForResult(intent, 630);
                //630 : 텍스트 이미지 수정 리퀘스트 코드
            } else if (staticData.selectType.equals("img")) {
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
        if (staticData.SelectBitmap == null && sticker == null) {
            showToast("선택된 스티커가 없습니다.");
        } else {
            if (stickerView.getCurrentSticker() instanceof TextSticker) {
                showToast("준비중 입니다.");
            } else if (stickerView.getCurrentSticker() instanceof Sticker) {
                stickerView.addSticker(new DrawableSticker(stickerView.getCurrentSticker().getDrawable()));
            }
        }
    }

    public void SaveImage(View v) {
        //결과를 저장하는 비트맵
        if (stickerView.getStickerCount() != 0) {
            if (!staticData.coll_content.equals("No Content")&&!staticData.coll_title.equals("No Title")) {
                SaveDialog dialog = new SaveDialog();
                dialog.setDialogListener(new SaveDialogListener() {
                    @Override
                    public void onPositiveClicked() {
                        String filePath ="";
                        String msg = "";
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Abba";
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();
                            msg += "저장 폴더가 생성되었습니다.\n";
                        }
                        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date();
                        FileOutputStream fos = null;
                        Bitmap resultBitmap = stickerView.createBitmap();
                        try {
                            filePath = path + "/"+ staticData.coll_title+ day.format(date) + ".jpeg";
                            fos = new FileOutputStream(filePath);
                            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
                            msg += "결과물이 갤러리에 저장되었습니다.";
                            showToast(msg);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ImageAsyncTask task = new ImageAsyncTask(getContext());
                        Log.i("저장경로",filePath);
                        try {
                            String[] res = task.execute(filePath,"result").get();
                            if (res[0].equals("upLoadSuccess")){
                                Log.i("결과",res[0]);
                                String id = userID;
                                String title = coll_title;
                                String content = coll_content;
                                String imgURL = res[1];
                                int stickerCnt = stickerView.getStickerCount();
                                Log.i("전송 결과",id+" / "+title+" / "+content+" / "+imgURL+" / "+stickerCnt);
                                UserAsyncTask task2 = new UserAsyncTask();
                                try {
                                    String res2 = task2.execute("sendResult", id, title, content, imgURL, String.valueOf(stickerCnt)).get();
                                    Log.i("결과",res2);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        stickerView.sendSticker();
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog.show(getActivity().getSupportFragmentManager(), "save");
            }
            else{
                showToast("제목과 설명을 작성해주세요.");
            }
        } else {
            Toast toast = Toast.makeText(getContext(), "저장할 결과물이 없습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void Description(View v){
        //결과를 저장하는 비트맵
        if (stickerView.getStickerCount() != 0) {
            DescriptionDialog dialog = new DescriptionDialog();
            dialog.show(getActivity().getSupportFragmentManager(), "description");
        } else {
            Toast toast = Toast.makeText(getContext(), "설명을 쓸 결과물이 없습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void showToast(String msg){
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void call_anim(View v){
        if (isOpen){
            btnMain.startAnimation(fab_main_rotate_close);
            btnLoad.startAnimation(fab_close);
            btnUser.startAnimation(fab_close);
            btnRemoveAll.startAnimation(fab_close);
            btnSave.startAnimation(fab_close);

            btnLoad.setClickable(false);
            btnUser.setClickable(false);
            btnRemoveAll.setClickable(false);
            btnSave.setClickable(false);
            isOpen = false;
        }else{
            btnMain.startAnimation(fab_main_rotate_open);
            btnLoad.startAnimation(fab_open);
            btnUser.startAnimation(fab_open);
            btnRemoveAll.startAnimation(fab_open);
            btnSave.startAnimation(fab_open);

            btnLoad.setClickable(true);
            btnUser.setClickable(true);
            btnRemoveAll.setClickable(true);
            btnSave.setClickable(true);
            isOpen = true;
        }
    }

    private void setBtn(View v) {
        btnMain = v.findViewById(R.id.btnMain);
        btnLoad = v.findViewById(R.id.btnLoad);
        btnRemoveAll = v.findViewById(R.id.btnRemoveAll);
        btnSave = v.findViewById(R.id.btnSave);
        btnUser = v.findViewById(R.id.btnUser);

        btnMain.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnRemoveAll.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoad:
                stickerLoad(v);
                call_anim(v);
                break;
            case R.id.btnRemoveAll:
                stickerRemoveAll(v);
                call_anim(v);
                break;
            case R.id.btnSave:
                SaveImage(v);
                call_anim(v);
                break;
            case R.id.btnUser:
                Description(v);
                call_anim(v);
                break;
            case R.id.btnMain:
                call_anim(v);
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
                staticData.SelectTextSticker = (CustomTextSticker) stickerView.getCurrentSticker();
                staticData.selectType = "text";
            } else if (stickerView.getCurrentSticker() instanceof Sticker) {
                staticData.SelectBitmap = ((BitmapDrawable) stickerView.getCurrentSticker().getDrawable()).getBitmap();
                staticData.selectType = "img";
            }
            stickerModify();
        }
    }
}