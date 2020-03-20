package com.example.myparkingcar.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class login extends AppCompatActivity {
    private Button btn1,btn2;
    private EditText et1,et2;
    private  String s1,s2,s3,s4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn1 = (Button) findViewById(R.id.login_btn_register);
        btn2 = (Button) findViewById(R.id.login_btn_login);
        btn2.setOnClickListener(new ReceiverListener());
        et1 = (EditText) findViewById(R.id.login_edit_account);
        et2 = (EditText) findViewById(R.id.login_edit_pwd);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    class ReceiverListener implements View.OnClickListener {

        public void onClick(View v) {
            new Thread() {
                @Override
                public void run() {
                    // 执行完毕后给handler发送一个空消息
                    try {
                        // 实例化Socket
                        //Socket socket = new Socket(HOST, PORT);
                        MySocket socket = MySocket.getsocket();
                        // 获得输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        OutputStream outputStream = socket.getOutputStream();
                        s1 = et1.getText().toString();//把这个传到User_Parking那个页面
                        s2 = et2.getText().toString();
                        s3 = "UL,"+s1+","+s2;
                        outputStream.write(s3.getBytes("utf-8"));
                        s4 = br.readLine();
                        //outputStream.close();
                        //br.close();
                        if(s4.equals("yes"))
                        {
                            Intent inet = new Intent(login.this, User_Home.class);
                            startActivity(inet);
                        }

                        else
                            showResponse(s4);

                    } catch (UnknownHostException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast mToast = Toast.makeText(login.this,null,Toast.LENGTH_SHORT);
                mToast.setText("登陆失败,用户名或密码输入错误!");
                mToast.show();

                //}
            }
        });
    }
}

