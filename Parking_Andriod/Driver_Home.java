package com.example.myparkingcar.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myparkingcar.R;

public class User_Home extends AppCompatActivity {
    private Button btn1;
    TextView t1;
    private RadioButton r1,r2,r3;
    //private RecyclerView R1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        btn1 = (Button) findViewById(R.id.home_change);
        t1 = (TextView) findViewById(R.id.place_information);
        
        r1=(RadioButton)findViewById(R.id.rb_home);
        r2=(RadioButton)findViewById(R.id.rb_car);
        r3=(RadioButton)findViewById(R.id.rb_me);
       // R1= (RecyclerView) findViewById(R.id.Rv_place);//未实现查询
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, User_parking.class);
                startActivity(intent);
            }
        });
       /*R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, User_parking.class);
                startActivity(intent);
            }
        });*/
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, User_Home.class);
                startActivity(intent);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, User_parking.class);
                startActivity(intent);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Home.this, setting.class);
                startActivity(intent);
            }
        });
    }
}
