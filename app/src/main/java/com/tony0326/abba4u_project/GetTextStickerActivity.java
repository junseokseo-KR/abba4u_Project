package com.tony0326.abba4u_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.tony0326.abba4u_project.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class GetTextStickerActivity extends Activity {
    AppCompatEditText ts_edit;
    AppCompatButton btnTextColor;
    int textColor = 0xff000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gettextsticker_layout);
        ts_edit = findViewById(R.id.ts_viewEdit);
        btnTextColor = findViewById(R.id.btnTextColor);
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

    public void makeTextSticker(View v){
        if (ts_edit.getText() != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("textValue", ts_edit.getText().toString());
            resultIntent.putExtra("textColor", textColor);
            setResult(629, resultIntent);
            finish();
        }
        else if (ts_edit.getText() == null){
            Toast.makeText(getApplicationContext(),"입력된 텍스트가 없습니다.",Toast.LENGTH_SHORT);
        }
    }

}