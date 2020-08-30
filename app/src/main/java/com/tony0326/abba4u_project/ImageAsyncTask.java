package com.tony0326.abba4u_project;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.tony0326.abba4u_project.staticData.coll_title;
import static com.tony0326.abba4u_project.staticData.userID;

public class ImageAsyncTask extends AsyncTask<String, String, String[]> {
    private String fileUri;
    private File imageFile;

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String[] result = new String[2];

    int maxBuffersize = 1024*1024;

    HttpURLConnection conn;
    DataOutputStream dos;
    Context context;

    public ImageAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        this.fileUri = strings[0];
        this.imageFile = new File(strings[0]);

        if (!imageFile.isFile()){
            result[0] = "upLoadFail";
            return result;
        } else{
            try {
                FileInputStream fis = new FileInputStream(imageFile);
                String ip_Address = "";

                URL url = null;
                if (strings[1].equals("result")){
                    url = new URL("http://"+ip_Address+":8090/Abba4U_Project/upload.jsp?id="+userID+"&title="+coll_title+"&type=result");   
                }
                if (strings[1].equals("sticker")){
                    url= new URL("http://"+ip_Address+":8090/Abba4U_Project/upload.jsp?id="+userID+"&title="+coll_title+"&type=sticker");
                }
                
                //connection 열기
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // input 허용
                conn.setDoOutput(true); // output 허용
                conn.setUseCaches(false);
                conn.setRequestMethod("POST"); // 전송 방식
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data"); //인코딩 타입
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary); // boundary 기준으로 인자를 구분함
                conn.setRequestProperty("uploaded_file", fileUri);

                //데이터 쓰기
                dos = new DataOutputStream(conn.getOutputStream());

                //이미지 전송
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileUri + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = fis.available();
                int bufferSize = Math.min(bytesAvailable, maxBuffersize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = fis.read(buffer,0,bufferSize);

                //이미지 읽기
                while(bytesRead>0){
                    dos.write(buffer,0,bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBuffersize);
                    bytesRead = fis.read(buffer,0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                fis.close();
                dos.flush();
                if (conn.getResponseCode()==200){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while((line = reader.readLine())!=null){
                        stringBuffer.append(line);
                    }
                    Log.i("받아온 path",stringBuffer.toString());
                    result[1] = stringBuffer.toString();
                }
                fis.close();
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result[0] = "upLoadSuccess";
        return result;
    }
}
