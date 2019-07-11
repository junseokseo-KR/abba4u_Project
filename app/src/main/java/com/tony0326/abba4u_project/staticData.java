package com.tony0326.abba4u_project;

import android.graphics.Bitmap;

import com.tony0326.abba4u_project.Module.Fragment.LassoCutFragment;

import pl.polidea.view.ZoomView;

public class staticData {
    public static boolean inputstate=false;
    public static Bitmap CutBitmap=null;
    public static Bitmap MainBitmap=null;
    public static Bitmap MirrorBitmap=null;
    public static Bitmap SelectBitmap=null;
    public static CustomTextSticker SelectTextSticker=null;
    public static String selectType = "";
    public static LassoCutFragment lcf=null;
    public static SomeView sv=null;
    public static ZoomView zv = null;
    public static boolean cutMode = false;
    public static boolean cut = false;
    public static String coll_title = "No Title";
    public static String coll_content = "No Content";
}
