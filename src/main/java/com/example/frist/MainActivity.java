package com.example.frist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        out=findViewById(R.id.textshow);
        out.setText("结果为：");

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View v) {
        //Log.i(TAG,"onClick:000000000000000");

        inp=findViewById(R.id.inp);
        //String inpStr = inp.getText().toString();
        double a=Double.parseDouble(inp.getText().toString());
        double b=1.8*a+32;
        out.setText("结果为： "+b);

        Log.i(TAG,"onClick:get");
    }
}