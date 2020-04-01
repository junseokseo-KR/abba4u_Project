package com.tony0326.abba4u_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.util.Log;

import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.tony0326.abba4u_project.staticData.coll_title;
import static com.tony0326.abba4u_project.staticData.userID;

public class CustomStickerView extends StickerView {
    private List<Sticker> stickers = new ArrayList<>();
    String fileUri;
    String res;
    int color;

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

    public byte[] resultBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void getColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                if (palette == null) {
                    return ;
                }
                Palette.Swatch swatch = null;
                if (palette.getLightVibrantSwatch() != null) {
                    swatch = palette.getLightVibrantSwatch();
                    Log.i("Lightvibrant", "어디잇누");
                } else if (palette.getVibrantSwatch() != null) {
                    swatch = palette.getVibrantSwatch();
                    Log.i("vibrant", "어디잇누");
                } else if (palette.getDarkVibrantSwatch() != null) {
                    swatch = palette.getDarkVibrantSwatch();
                    Log.i("Darkvibrant", "어디잇누");
                } else if (palette.getLightMutedSwatch() != null) {
                    swatch = palette.getLightMutedSwatch();
                    Log.i("Lightmute", "어디잇누");
                } else if (palette.getMutedSwatch() != null) {
                    swatch = palette.getMutedSwatch();
                    Log.i("mute", "어디잇누");
                } else if (palette.getDarkMutedSwatch() != null) {
                    swatch = palette.getDarkMutedSwatch();
                    Log.i("Darkmute", "어디잇누");
                }

                color = swatch.getRgb();
            }
        });
    }

    public static int[] integerToRGB(int color){
        int[] colors = new int[3];
        colors[0] = color >> 16 & 0xff; //r

        colors[1] = color >> 8 & 0xff; //g

        colors[2] = color & 0xff; //b
        Log.i("integerToRGB_color",colors[0]+"/"+colors[1]+"/"+colors[2]);
        return colors;
    }


    public String[] sendSticker() {
        Date date = new Date();
        int i = 1;
        FileOutputStream fos;
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Sticker";
        String filePath = null;
        String[] res = new String[2];
        File file = new File(path);
        Bitmap resultBitmap = null;

        float locationX,locationY, width, height, angle = 0;
        String imgURL = null,text = null;
        int type = 0,r = 0,g = 0,b = 0;

        SimpleDateFormat day = null;
        if (!file.exists()) {
            file.mkdirs();
        }

        for (Sticker sticker : stickers) {
            day = new SimpleDateFormat("yyyyMMddHHmmss");
            resultBitmap = Bitmap.createBitmap(((BitmapDrawable) sticker.getDrawable()).getBitmap());
            try {
                filePath = path + "/" + staticData.coll_title + "_sticker" + i + day.format(date) + ".jpeg";
                fos = new FileOutputStream(filePath);
                resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                ImageAsyncTask imageAsyncTask = new ImageAsyncTask(getContext());
                res = imageAsyncTask.execute(filePath, "sticker").get();
                if (res[0].equals("upLoadSuccess")) {
                    Log.i("성공", res[0]);
                } else {
                    Log.i("실패", res[0]);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //imgURL, type, locationX, locationY, width, height, angle, r,g,b, text;
            locationX = sticker.getMappedBoundPoints()[0];
            locationY = sticker.getMappedBoundPoints()[1];
            width = sticker.getCurrentWidth();
            height = sticker.getCurrentHeight();
            angle = sticker.getCurrentAngle();
            if (sticker instanceof CustomTextSticker){
                type = 2;
                text = ((CustomTextSticker) sticker).getText();
                r = Color.red(((CustomTextSticker) sticker).getTextColor());
                g = Color.green(((CustomTextSticker) sticker).getTextColor());
                b = Color.blue(((CustomTextSticker) sticker).getTextColor());
            }else if (sticker instanceof DrawableSticker){
                type = 1;
                imgURL = res[1];
                getColor(resultBitmap);
                Log.i("color",color+"");
                int[] colors = integerToRGB(color);
                r = colors[0];
                g = colors[1];
                b = colors[2];
                Log.i(i+" insert_color",colors[0]+"/"+colors[1]+"/"+colors[2]);
            }
            Log.i("스티커정보",imgURL+" / "+type+" / "+locationX+" / "+locationY+" / "+width+" / "+height+" / "+angle+" / "+r+" / "+g+" / "+b+" / "+text);
            try {
                UserAsyncTask userAsyncTask = new UserAsyncTask();
                res[0] = userAsyncTask.execute("sendData",imgURL, type+"", locationX+"", locationY+"", width+"", height+"", angle+"", r+"",g+"",b+"", text, userID, coll_title).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

        return res;
    }
}
