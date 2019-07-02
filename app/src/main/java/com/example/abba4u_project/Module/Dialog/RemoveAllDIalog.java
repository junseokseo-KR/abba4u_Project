package com.example.abba4u_project.Module.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.abba4u_project.Module.Listener.RemoveAllDialogListener;
import com.example.abba4u_project.R;

@SuppressLint("ValidFragment")
public class RemoveAllDIalog extends DialogFragment {
    private Button deleteBtn, closeBtn;
    private RemoveAllDialogListener removeAllDialogListener;

    public RemoveAllDIalog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.removealldialog_layout,container,false);
        deleteBtn = view.findViewById(R.id.remoevalldialog_deleteBtn);
        closeBtn = view.findViewById(R.id.removealldialog_cancleBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllDialogListener.onPositiveClicked();
                dismiss();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setDialogListener(RemoveAllDialogListener removeAllDialogListener){
        this.removeAllDialogListener = removeAllDialogListener;
    }
}
