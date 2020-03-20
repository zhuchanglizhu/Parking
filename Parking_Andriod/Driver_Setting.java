package com.example.myparkingcar.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.myparkingcar.Exit;
import com.example.myparkingcar.R;

public class setting extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5;
    private RadioButton r1,r2,r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        btn1 = (Button) findViewById(R.id.setting_1);
        btn2 = (Button) findViewById(R.id.setting_2);
        btn3 = (Button) findViewById(R.id.setting_3);
        btn4 = (Button) findViewById(R.id.setting_4);
        btn5 = (Button) findViewById(R.id.setting_exit_account);
        r1=(RadioButton)findViewById(R.id.rb_home);
        r2=(RadioButton)findViewById(R.id.rb_car);
        r3=(RadioButton)findViewById(R.id.rb_me);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, resetphone.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, resetmail.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, resetcarid.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, resetpassword.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, Exit.class);
                startActivity(intent);
            }
        });
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, User_Home.class);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, User_parking.class);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, setting.class);
                startActivity(intent);
            }
        });
    }
}
