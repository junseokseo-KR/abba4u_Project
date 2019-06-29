package com.example.abba4u_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ModifyTextStickerActivity extends Activity {
    AppCompatEditText ts_edit;
    AppCompatTextView ts_view;
    AppCompatButton btnTextColor;
    int textColor = 0xff000000;
    Editable textValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gettextsticker_layout);
        Intent intent = getIntent();
        ts_edit = findViewById(R.id.ts_edit);
        ts_view = findViewById(R.id.ts_view);
        ts_edit.setText(intent.getStringExtra("textValue"));
        ts_view.setText(intent.getStringExtra("textValue"));
        btnTextColor = findViewById(R.id.btnTextColor);
        ts_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //변경 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //변경 시점
                textValue = ts_edit.getText();
                ts_view.setText(textValue);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //변경 후
            }
        });
    }

    public void openColorPicker(View v) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, textColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                textColor = color;
                ts_view.setTextColor(textColor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }

        });
        dialog.show();
    }

    public void makeTextSticker(View v){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("textValue", textValue.toString());
        resultIntent.putExtra("textColor", textColor);
        setResult(630,resultIntent);
        finish();
    }
}
