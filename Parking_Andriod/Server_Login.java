package com.example.myparkingcar.administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myparkingcar.user.MainActivity;
import com.example.myparkingcar.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.myparkingcar.MySocket;



public class Alogin extends AppCompatActivity {
    private Button btn1,btn2;
    private EditText et1,et2;
    private  String s1,s2,s3,s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrantor);
        //

       // Log.d("Alogin",data);
        btn1 = (Button) findViewById(R.id.administrator_btn_login);
        btn2 = (Button) findViewById(R.id.administrator_btn_register);
        btn1.setOnClickListener(new ReceiverListener());
        et1 = (EditText) findViewById(R.id.administrator_name);
        et2 = (EditText) findViewById(R.id.administrator_password);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Alogin.this,Aregister.class);//跳转到了用户的注册
                startActivity(intent);
            }
        });
    }

    class ReceiverListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
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
                        s1 = et1.getText().toString();
                        s2 = et2.getText().toString();
                        s3 = "AL,"+s1+","+s2;
                        outputStream.write(s3.getBytes("utf-8"));
                        s4 = br.readLine();

                        if(s4.equals("yes"))
                        {
                            Intent inet = new Intent(Alogin.this, Admini_setting.class);
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
                Toast mToast = Toast.makeText(Alogin.this,null,Toast.LENGTH_SHORT);
                mToast.setText("登陆失败,用户名或密码输入错误!");
                mToast.show();

                //}
            }
        });
    }

}
