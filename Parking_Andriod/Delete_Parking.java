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


public class Change_Apassword extends AppCompatActivity {

    private Button btn1;
    private String line;
    private EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_password);
        btn1 = (Button) findViewById(R.id.btn_password);
        et1 = (EditText) findViewById(R.id.old_password);
        et2 = (EditText) findViewById(R.id.new_password);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            //Socket socket = new Socket("118.190.57.149", 6500);
                            MySocket socket = MySocket.getsocket();
                            // 获得输入流
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            OutputStream out = socket.getOutputStream();
                            String  s1 = et1.getText().toString();
                            String  s2 = et2.getText().toString();
                            String  s4 = "APC"+","+s1+","+s2;
                            out.write(s4.getBytes("utf-8"));
                            String  s5 = in.readLine();
                            showResponse(s5);

                            if(line.equals("yes"))
                            {

                                Intent inet = new Intent(Change_Apassword.this, Admini_setting.class);
                                startActivity(inet);
                            }
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

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast mToast = Toast.makeText(Change_Apassword.this,null,Toast.LENGTH_SHORT);
                if (response.equals("yes")){
                    mToast.setText("修改成功!");
                    mToast.show();
                }
                else{
                    mToast.setText(response);
                    mToast.show();
                }
            }
        });
    }
}
