package com.tony0326.abba4u_project.Module.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tony0326.abba4u_project.CustomZoomView;
import com.tony0326.abba4u_project.SomeView;

import static com.tony0326.abba4u_project.staticData.lcf;
import static com.tony0326.abba4u_project.staticData.zv;

public class LassoCutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lcf=this;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        zv = new CustomZoomView(getActivity());
        SomeView someview=new SomeView(getActivity());
        zv.addView(someview);
        zv.setLayoutParams(layoutParams);
        zv.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        return zv ;
    }
}
