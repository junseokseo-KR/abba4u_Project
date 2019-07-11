package com.tony0326.abba4u_project;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout idLayout,pwdLayout;
    private EditText idEdit, pwdEdit;
    private Button loginBtn, joinGo;
    private String res, id, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {
        idLayout = findViewById(R.id.idLayout);
        idEdit = idLayout.getEditText();
        pwdLayout = findViewById(R.id.pwdLayout);
        pwdEdit = pwdLayout.getEditText();

        joinGo = findViewById(R.id.joinGo);
        joinGo.setOnClickListener((View.OnClickListener) this);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joinGo:
                Intent joinIntent = new Intent(this, JoinActivity.class);
                startActivity(joinIntent);
                break;
            case R.id.loginBtn:
                id = String.valueOf(idEdit.getText());
                pwd = String.valueOf(pwdEdit.getText());
                if (!id.equals("") && !pwd.equals("")) {
                    closeError(idLayout);
                    closeError(pwdLayout);

                    UserAsyncTask task = new UserAsyncTask();
                    try {
                        res = task.execute("login", id, pwd).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("결과값",res);
                    if (res.contains("loginSuccess")) {
                        showToast("로그인 성공");
                        Intent loginIntent = new Intent(this, MainActivity.class);
                        startActivity(loginIntent);
                    } else if (res.contains("noAccount")) {
                        showToast("존재하지 않는 계정입니다.");
                    } else if(res.contains("pwdFailed")){
                        showToast("비밀번호 오류");
                    }
                }

                if (id.equals("")){
                    openError(idLayout,"아이디를 입력하세요");
                } else if (!id.equals("")){
                    closeError(idLayout);
                }

                if (pwd.equals("")){
                    openError(pwdLayout,"비밀번호를 입력하세요");
                } else if (!pwd.equals("")){
                    closeError(pwdLayout);
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
