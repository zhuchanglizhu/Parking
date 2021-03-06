package com.example.myparkingcar.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myparkingcar.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.myparkingcar.MySocket;


public class resetcarid extends AppCompatActivity {
    private Button btn1,btn2;
    private EditText et1,et2,et3;
    private String s1,s2,s3,s4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetcarid);
        btn1 = (Button) findViewById(R.id.resetcarid_change);
        et1 = (EditText) findViewById(R.id.resetcarid_old_carid);
        et2 = (EditText) findViewById(R.id.resetcarid_new_carid);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            //Socket socket = new Socket("118.190.57.149", 6000);
                            MySocket socket = MySocket.getsocket();
                            // 获得输入流
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            OutputStream out = socket.getOutputStream();
                            s1 = et1.getText().toString();
                            s2 = et2.getText().toString();
                            s3 = "CU"+","+s1+","+s2;
                            out.write(s3.getBytes("utf-8"));
                            s4 = in.readLine();
                            show();
                            out.close();
                            in.close();
                        }catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    private void show(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast mToast = Toast.makeText(resetcarid.this,null,Toast.LENGTH_SHORT);
                mToast.setText("修改成功!");
                mToast.show();
            }
        });

    }
}
