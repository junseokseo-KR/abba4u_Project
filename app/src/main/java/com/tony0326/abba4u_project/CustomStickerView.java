package com.tony0326.abba4u_project;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.tony0326.abba4u_project.staticData.coll_content;
import static com.tony0326.abba4u_project.staticData.coll_title;

public class CustomStickerView extends StickerView {
    private List<Sticker> stickers = new ArrayList<>();
    String res;

    public CustomStickerView(Context context) {
        super(context);
    }

    public CustomStickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomStickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean remove(@Nullable Sticker sticker) {
        stickers.remove(sticker);
        return super.remove(sticker);
    }

    @NonNull
    @Override
    public StickerView addSticker(@NonNull Sticker sticker) {
        stickers.add(sticker);
        return super.addSticker(sticker);
    }

    @Override
    public void removeAllStickers() {
        stickers.removeAll(stickers);
        super.removeAllStickers();
    }

    public String sendResult() {
        UserAsyncTask task = new UserAsyncTask();
        String send_title = "No Title";
        String send_content = "No Content";
        try {
            send_title = coll_title;
            send_content = coll_content;
            Log.i("전달 값",send_title+" / "+send_content);
            res = task.execute("sendResult", send_title, send_content).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Sticker sticker : stickers) {
            Log.i("스티커", sticker.toString()+"");
            Log.i("스티커 위치", sticker.getMappedCenterPoint()+"");
            Log.i("스티커 너비", sticker.getCurrentWidth()+"");
            Log.i("스티커 높이", sticker.getCurrentHeight()+"");
            Log.i("스티커 각도", sticker.getCurrentAngle()+"");
            if (sticker instanceof CustomTextSticker){
                Log.i("스티커 텍스트", ((CustomTextSticker) sticker).getText()+"");
                Log.i("스티커 텍스트 컬러 R", Color.red(((CustomTextSticker) sticker).getTextColor())+"");
                Log.i("스티커 텍스트 컬러 G", Color.green(((CustomTextSticker) sticker).getTextColor())+"");
                Log.i("스티커 텍스트 컬러 B", Color.blue(((CustomTextSticker) sticker).getTextColor())+"");
            }
        }

        return res;
    }
}
