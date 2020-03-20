package com.example.myparkingcar.administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myparkingcar.R;
import com.example.myparkingcar.user.MainActivity;
import com.example.myparkingcar.user.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.myparkingcar.MySocket;


public class Aregister extends AppCompatActivity {
    private Button btn1, btn2;
    private String line;
    private EditText et1, et2;
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_register);
        btn1 = (Button) findViewById(R.id.administrator_register_login);
        btn2 = (Button) findViewById(R.id.administrator_register_cancel);

        btn1.setOnClickListener(new Aregister.ReceiverListener());

        et1 = (EditText) findViewById(R.id.administrator_register_name);
        et2 = (EditText) findViewById(R.id.administrator_register_password);

    }

    class ReceiverListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Thread() {//创建线程
                @Override
                public void run() {
                    try {
                        //实例化Socket
                       // Socket socket = new Socket(HOST, PORT);
                        MySocket socket = MySocket.getsocket();
                        //获得输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//获取服务器传来的字节流
                        OutputStream outputStream = socket.getOutputStream();//发送给服务器端的数据
                        String s1 = et1.getText().toString();//获得此控件中的字符串文本值，返回String对象
                        String s2 = et2.getText().toString();
                        String s8 = "AR," + s1 + "," + s2 ;
                        outputStream.write(s8.getBytes("utf-8"));//写入输出流
                        line = br.readLine();//读取一个文本行
                        showResponse(line);

                        if (line.equals("yes")) {//注册成功则页面跳转
                            Intent inet = new Intent(Aregister.this, login.class);
                            startActivity(inet);
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            }.start();

        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {//异步
            @Override
            public void run() {
                Toast mToast = Toast.makeText(Aregister.this, null, Toast.LENGTH_SHORT);
                if (response.equals("yes")) {
                    mToast.setText("注册成功!");
                    mToast.show();
                } else {
                    mToast.setText("注册失败,用户名重复或两次密码输入不一致!");
                    mToast.show();
                }
            }
        });
    }
}

