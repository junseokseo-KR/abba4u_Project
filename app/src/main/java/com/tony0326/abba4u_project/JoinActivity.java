package com.tony0326.abba4u_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tony0326.abba4u_project.R;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputLayout nameLay, idLay, pwdLay;
    private EditText nameEdit, idEdit, pwdEdit;
    private String name, id, pwd, command, res;
    private Button joinBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);
        initView();
    }

    private void initView(){
        nameLay = findViewById(R.id.nameLayout);
        idLay = findViewById(R.id.idLayout);
        pwdLay = findViewById(R.id.pwdLayout);
        joinBtn = findViewById(R.id.joinBtn);

        nameEdit = nameLay.getEditText();
        idEdit = idLay.getEditText();
        pwdEdit = pwdLay.getEditText();

        joinBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.joinBtn:
                name = String.valueOf(nameEdit.getText());
                id = String.valueOf(idEdit.getText());
                pwd = String.valueOf(pwdEdit.getText());
                if (!id.equals("") && !pwd.equals("") && !name.equals("")) {
                    closeError(idLay);
                    closeError(pwdLay);
                    closeError(nameLay);

                    UserAsyncTask task = new UserAsyncTask();

                    command = "join";
                    try {
                        res = task.execute(command, id, pwd, name).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("결과값",res);
                    if (res.contains("joinSuccess")) {
                        showToast("회원가입 성공\n로그인을 진행해주세요.");
                        finish();
                    } else if (res.contains("existAccount")) {
                        showToast("이미 존재하는 계정(ID)입니다.");
                    }
                }

                if (id.equals("")){
                    openError(idLay,"아이디를 입력하세요");
                } else if (!id.equals("")){
                    closeError(idLay);
                }

                if (pwd.equals("")){
                    openError(pwdLay,"비밀번호를 입력하세요");
                } else if (!pwd.equals("")){
                    closeError(pwdLay);
                }

                if (name.equals("")){
                    openError(nameLay,"이름을 입력하세요");
                } else if (!name.equals("")){
                    closeError(nameLay);
                }
                break;
        }
    }

    private void showToast(String msg){
        Toast toast;
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -600);
        toast.show();
    }

    private void closeError(TextInputLayout layout){
        layout.setErrorEnabled(false);
        layout.setError(null);
    }

    private void openError(TextInputLayout layout, String msg){
        layout.setErrorEnabled(true);
        layout.setError(msg);
    }
}
