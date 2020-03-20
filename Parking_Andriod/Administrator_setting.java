package com.example.myparkingcar.administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myparkingcar.Exit;
import com.example.myparkingcar.R;


public class Admini_setting extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_function);
        btn1 = (Button) findViewById(R.id.administrator_create_parking);
        btn2 = (Button) findViewById(R.id.administrator_delete_parking);
        btn3 = (Button) findViewById(R.id.administrator_change_capacity);
        btn4 = (Button) findViewById(R.id.administrator_change_password);
        btn5 = (Button) findViewById(R.id.administrator_parking_information);
        btn6 = (Button) findViewById(R.id.administrator_look);
        btn7 = (Button) findViewById(R.id.administrator_exit);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Add_parking.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Delete_parking.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Update_carcapacity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Change_Apassword.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Look_parking.class);
                startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Look_car.class);
                startActivity(intent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admini_setting.this, Exit.class);
                startActivity(intent);
            }
        });
    }
}
