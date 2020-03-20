package com.example.myparkingcar.administrator;

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

public class Add_parking extends AppCompatActivity {

    private Button btn1,btn2;
    private String line;
    private EditText et1,et2,et3,et4,et5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_add);
        btn1 = (Button) findViewById(R.id.parking_btn_add);
        btn2 = (Button) findViewById(R.id.parking_btn_cancel);
        btn1.setOnClickListener(new ReceiverListener());
        et1 = (EditText) findViewById(R.id.parking_add_name);
        et2 = (EditText) findViewById(R.id.parking_add_capacity);
        et3 = (EditText) findViewById(R.id.parking_add_site);
        et4 = (EditText) findViewById(R.id.parking_add_charge);
        et5 = (EditText) findViewById(R.id.parking_add_phone);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_parking.this, Admini_setting.class);//跳转到了用户的注册
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
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//
                        OutputStream outputStream = socket.getOutputStream();//
                        String s1 = et1.getText().toString();
                        String s2 = et2.getText().toString();
                        String s3 = et3.getText().toString();
                        String s4 = et4.getText().toString();
                        String s5 = et5.getText().toString();
                        String s7 = "PI," + s1 + ","+s2 + "," +s3 + "," + s4 + "," + s5;
                        outputStream.write(s7.getBytes("utf-8"));
                        line = br.readLine();
                        showResponse(line);


                        if(line.equals("yes"))
                        {
                            Intent inet = new Intent(Add_parking.this,Admini_setting.class);
                            startActivity(inet);
                        }
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
        runOnUiThread(new Runnable() {//异步
            @Override
            public void run() {
                Toast mToast = Toast.makeText(Add_parking.this,null,Toast.LENGTH_SHORT);
                if (response.equals("yes")){
                    mToast.setText("录入成功!");
                    mToast.show();
                }
                else{
                    mToast.setText("录入失败,请重新尝试!");
                    mToast.show();
                }
            }
        });
    }
}
