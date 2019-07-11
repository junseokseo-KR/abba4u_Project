package com.tony0326.abba4u_project.Module.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.tony0326.abba4u_project.R;
import com.tony0326.abba4u_project.staticData;

public class DescriptionDialog extends DialogFragment {
    private Button saveBtn, cancleBtn;
    private TextInputLayout titleLay, contentLay;
    private EditText titleEdit, contentEdit;
    public DescriptionDialog(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descriptiondialog_layout, container, false);
        initView(view);
        if (!staticData.coll_title.equals("No Title")){
            titleEdit.setText(staticData.coll_title);
        }
        if (!staticData.coll_content.equals("No Content")){
            contentEdit.setText(staticData.coll_content);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staticData.coll_title = String.valueOf(titleEdit.getText());
                staticData.coll_content = String.valueOf(contentEdit.getText());
                if (staticData.coll_title.equals("")){
                    staticData.coll_title = "No Title";
                }
                if (staticData.coll_content.equals("")){
                    staticData.coll_content = "No Content";
                }
                dismiss();
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private void initView(View view){
        titleLay = view.findViewById(R.id.titleLayout);
        titleEdit = titleLay.getEditText();
        contentLay = view.findViewById(R.id.contentLayout);
        contentEdit = contentLay.getEditText();
        saveBtn = view.findViewById(R.id.descriptiondialog_saveBtn);
        cancleBtn = view.findViewById(R.id.descriptiondialog_cancleBtn);
    }
}
