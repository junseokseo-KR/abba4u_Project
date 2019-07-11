package com.tony0326.abba4u_project.Module.Dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.tony0326.abba4u_project.Module.Listener.SaveDialogListener;
import com.tony0326.abba4u_project.R;

@SuppressLint("ValidFragment")
public class SaveDialog extends DialogFragment {
    private Button continueBtn, exitBtn;
    private SaveDialogListener saveDialogListener;
    public SaveDialog(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savedialog_layout, container, false);
        continueBtn = view.findViewById(R.id.savedialog_continueBtn);
        exitBtn = view.findViewById(R.id.savedialog_exitBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDialogListener.onPositiveClicked();
                dismiss();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDialogListener.onNegativeClicked();
                dismiss();
            }
        });

        return view;
    }

    public void setDialogListener(SaveDialogListener saveDialogListener){
        this.saveDialogListener = saveDialogListener;
    }
}
