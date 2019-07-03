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
    AppCompatButton btnTextColor;
    int textColor = 0xff000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifytextsticker_layout);
        Intent intent = getIntent();
        ts_edit = findViewById(R.id.ts_view_modify);
        ts_edit.setText(intent.getStringExtra("textValue"));
        btnTextColor = findViewById(R.id.btnTextColor_modify);
        ts_edit.requestFocus();
    }

    public void openColorPicker(View v) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, textColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                textColor = color;
                ts_edit.setTextColor(textColor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }

        });
        dialog.show();
    }

    public void modifyTextSticker(View v){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("textValue", ts_edit.getText().toString());
        resultIntent.putExtra("textColor", textColor);
        setResult(630,resultIntent);
        finish();
    }
}
