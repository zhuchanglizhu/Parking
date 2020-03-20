package com.example.myparkingcar.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.myparkingcar.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class User_parking extends AppCompatActivity {
    private Button btn1, btn2;
    private RadioButton r1, r2, r3;
    Spinner selectCharacterSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingpage);
        btn1 = (Button) findViewById(R.id.parkingpage_btn_start);
        btn2 = (Button) findViewById(R.id.parkingpage_btn_stop);
        r1 = (RadioButton) findViewById(R.id.rb_home);
        r2 = (RadioButton) findViewById(R.id.rb_car);
        r3 = (RadioButton) findViewById(R.id.rb_me);
        selectCharacterSpinner = (Spinner) findViewById(R.id.parkingpage_spinner);

        /*btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("118.190.57.149", 6000);
                            // 获得输入流
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            OutputStream out = socket.getOutputStream();
                            s1 = et1.getText().toString();
                            s2 = et2.getText().toString();
                            s3 = "MN"+","+s2+","+s1;
                            out.write(s3.getBytes("utf-8"));
                            s4 = in.readLine();
                            show();
                            out.close();
                            in.close();
                           /* if (s4.equals("yes")) {
                                Intent intent = new Intent(login.this, setting.class);
                                startActivity(intent);
                            }*/
                     /*   }catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_parking.this, User_parking.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        new Thread(){
@Override
public void run() {
        try {
        Socket socket = new Socket("118.190.57.149", 6000);
        // 获得输入流
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();
        s1 = et1.getText().toString();
        s2 = et2.getText().toString();
        s3 = "MN"+","+s2+","+s1;
        out.write(s3.getBytes("utf-8"));
        s4 = in.readLine();
        show();
        out.close();
        in.close();
                           /* if (s4.equals("yes")) {
                                Intent intent = new Intent(login.this, setting.class);
                                startActivity(intent);
                            }*/
       /* }catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        }
        }.start();
        }
        });
        }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_parking.this, User_parking.class);
                startActivity(intent);
            }
        });
       /*selectCharacterSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, User_parking.class);
                startActivity(intent);
            }
        });*/
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_parking.this, User_Home.class);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_parking.this, User_parking.class);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_parking.this, setting.class);
                startActivity(intent);
            }
        });
    }
}
