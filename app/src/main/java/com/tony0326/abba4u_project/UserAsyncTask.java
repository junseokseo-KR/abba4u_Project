package com.tony0326.abba4u_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserAsyncTask extends AsyncTask<String, Void, String> {
    //jsp에 보낼값, jsp로부터 받을 값
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;

            //서버의 ip가 변경될때마다 바꿔줘야함
            String ip_Address = "192.168.0.3";

            URL url = new URL("http://" + ip_Address + ":8090/Abba4U_Project/userDataPage.jsp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            if (strings[0].equals("login")) {
                sendMsg = "command=" + strings[0] + "&id=" + strings[1] + "&pwd=" + strings[2];
            } else if (strings[0].equals("join")) {
                sendMsg = "command=" + strings[0] + "&id=" + strings[1] + "&pwd=" + strings[2] + "&name=" + strings[3];
            } else if (strings[0].equals("sendResult")) {
                sendMsg = "command=" + strings[0] + "&id=" + strings[1] + "&title=" + strings[2] + "&content=" + strings[3] + "&imgURL=" + strings[4] + "&stickerCnt=" + strings[5];
            } else if (strings[0].equals("sendData")) {
                //imgURL, type, locationX, locationY, width, height, angle, r,g,b, text;
                sendMsg = "command=" + strings[0] + "&imgURL=" + strings[1] + "&type=" + strings[2] + "&locationX=" + strings[3] + "&locationY=" + strings[4] + "&width=" + strings[5] +
                        "&height=" + strings[6] + "&angle=" + strings[7] + "&r=" + strings[8] + "&g=" + strings[9] + "&b=" + strings[10] + "&text=" + strings[11] + "&id=" + strings[12] + "&title=" + strings[13];
            }
            outputStreamWriter.write(sendMsg);
            outputStreamWriter.flush();

            if (connection.getResponseCode() == connection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                receiveMsg = stringBuffer.toString();
            } else {
                Log.i("통신 에러 이유", connection.getResponseCode() + "");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }
}
