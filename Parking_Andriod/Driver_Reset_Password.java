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


public class resetpassword extends AppCompatActivity {
    private Button btn1,btn2;
    private EditText et1,et2,et3;
    private  String s1,s2,s3,s4,s5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);

        et1 = (EditText) findViewById(R.id.resetpassword_old_password);
        et2 = (EditText) findViewById(R.id.resetpassword_new_password);
        et3 = (EditText) findViewById(R.id.resetpassword_new_password_check);
        btn1 = (Button) findViewById(R.id.resetpassword_change);

        btn1.setOnClickListener(new View.OnClickListener() {

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
                            s3 = et3.getText().toString();
                            s4 = "UPc"+","+s2+","+s1;
                            out.write(s4.getBytes("utf-8"));
                            s5 = in.readLine();
                            showResponse(s5);

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
                Toast mToast = Toast.makeText(resetpassword.this,null,Toast.LENGTH_SHORT);
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
