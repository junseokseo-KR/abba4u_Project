package com.example.abba4u_project;

import android.graphics.Bitmap;

import static com.example.abba4u_project.staticData.CutBitmap;
import static com.example.abba4u_project.staticData.MirrorBitmap;

public class SizeCutting {
    boolean SizeCutting_Life=true;
    Thread th;
    public SizeCutting(final Bitmap b)
    {
        Runnable r=new Runnable() {
            @Override
            public void run() {
                CutBitmap=SizeCheck(b);
                MirrorBitmap=CutBitmap;
            }
        };
        th=new Thread(r);
        th.start();

    }
    public void SizeCutting_kill(){
        SizeCutting_Life=false;
    }


    public Bitmap SizeCheck(Bitmap target) {
        int MaxHeight = 0;
        boolean FindMaxHeigt = false;
        int MaxWidth = 0;
        boolean FindMaxWidth = false;
        int MinHeight = 0;
        boolean FindMinHeight = false;
        int MinWidth = 0;
        boolean FindMinWidth = false;
        //높이 최저부분

        if (MinHeight == 0) {
            int w = 0;
            int h = 0;
            while (SizeCutting_Life&&(!FindMinHeight) && h < target.getHeight()) {
                w = 0;
                while (SizeCutting_Life&&(!FindMinHeight) && w < target.getWidth()) {

                    if (target.getPixel(w, h) != 0) {

                        MinHeight = h;
                        FindMinHeight = true;
                    }
                    w++;
                }
                h++;
            }
        }
        //높이의 최고 부분
        if (MaxHeight == 0) {
            int w = target.getWidth() - 1;
            int h = target.getHeight() - 1;
            while (SizeCutting_Life&&(!FindMaxHeigt) && h > 0) {
                w = target.getWidth() - 1;
                while (SizeCutting_Life&&(!FindMaxHeigt) && w > 0) {
                    if (target.getPixel(w, h) != 0) {
                        MaxHeight = h;
                        FindMaxHeigt = true;
                    }
                    w--;
                }
                h--;
            }
        }
        //최저 너비 부분
        if (MinWidth == 0) {
            int w = 0;
            int h = 0;
            while (SizeCutting_Life&&(!FindMinWidth) && w < target.getWidth()) {
                h = 0;
                while (SizeCutting_Life&&(!FindMinWidth) && h < target.getHeight()) {
                    if (target.getPixel(w, h) != 0) {
                        MinWidth = w;
                        FindMinWidth = true;
                    }
                    h++;
                }
                w++;
            }
        }
        //최고 너비 부분
        if (MaxWidth == 0) {
            int w = target.getWidth() - 1;
            int h = target.getHeight() - 1;
            while (SizeCutting_Life&&(!FindMaxWidth) && w > 0) {
                h = target.getHeight() - 1;
                while (SizeCutting_Life&&(!FindMaxWidth) && h > 0) {
                    if (target.getPixel(w, h) != 0) {
                        MaxWidth = w;
                        FindMaxWidth = true;
                    }
                    h--;
                }
                w--;
            }
        }
        if (SizeCutting_Life&&FindMaxHeigt && FindMaxWidth && FindMinHeight && FindMinWidth) {
            //너비높이 고려 빈 비트맵생성
            Bitmap res = Bitmap.createBitmap(MaxWidth - MinWidth, MaxHeight - MinHeight, target.getConfig());

            //그림너비가 시작되는 최소지점
            int targetminW = MinWidth;
            //그림 높이가 시작되는 최소지점
            int targetminH = MinHeight;

            int resW = 0;
            int resH = 0;
            boolean s = true;

            while (SizeCutting_Life&&targetminW < MaxWidth) {
                resH = 0;
                targetminH = MinHeight;
                while (SizeCutting_Life&&targetminH < MaxHeight) {
                    res.setPixel(resW, resH, target.getPixel(targetminW, targetminH));
                    targetminH++;
                    resH++;
                }
                targetminW++;
                resW++;
            }
            if(SizeCutting_Life)
                return res;
            else
                return null;
        } else {

            return null;
        }
    }
}
