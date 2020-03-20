package com.example.myparkingcar.administrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;//要修改对应的build.gradle

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



public class Delete_parking extends AppCompatActivity {
    private Button btn1;
   private RecyclerView r1;
    private String s1,s2,Line;
    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_delect_place);
        btn1=(Button)findViewById(R.id.delete);
       // r1= (RecyclerView) findViewById(R.id.recyclerview);
        btn1.setOnClickListener(new Delete_parking.ReceiverListener());
        et1 = (EditText) findViewById(R.id.parking_name);


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
                       // Socket socket = new Socket(HOST, PORT);
                        MySocket socket = MySocket.getsocket();
                        // 获得输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        OutputStream outputStream = socket.getOutputStream();
                        s1 = et1.getText().toString();//停车场名字

                        s2 = "PD,"+s1;
                        outputStream.write(s2.getBytes("utf-8"));
                        Line = br.readLine();

                        if(Line.equals("yes"))
                        {
                            Intent inet = new Intent(Delete_parking.this, Admini_setting.class);
                            startActivity(inet);
                        }

                        else
                            showResponse(Line);

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
                Toast mToast = Toast.makeText(Delete_parking.this,null,Toast.LENGTH_SHORT);
                mToast.setText("删除失败!");
                mToast.show();

                //}
            }
        });
    }

}
