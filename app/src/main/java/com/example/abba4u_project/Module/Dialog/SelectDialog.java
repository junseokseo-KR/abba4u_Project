package com.example.abba4u_project.Module.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.abba4u_project.Module.Listener.SaveDialogListener;
import com.example.abba4u_project.Module.Listener.SelectDialogListener;
import com.example.abba4u_project.R;


public class SelectDialog extends DialogFragment {
    private Button imgBtn, txtBtn;
    private SelectDialogListener selectDialogListener;
    public SelectDialog(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectdialog_layout, container, false);
        imgBtn = view.findViewById(R.id.selectdialog_imgBtn);
        txtBtn = view.findViewById(R.id.selectdialog_txtBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialogListener.onImageStickerClicked();
                dismiss();
            }
        });
        txtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialogListener.onTextStickerClicked();
                dismiss();
            }
        });
        return view;
    }
    public void setDialogListener(SelectDialogListener selectDialogListener){
        this.selectDialogListener = selectDialogListener;
    }
}
