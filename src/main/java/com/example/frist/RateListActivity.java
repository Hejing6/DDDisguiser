package com.example.frist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RateListActivity extends AppCompatActivity {
    private static final String TAG="MyListTask";
    Float rate=0.0f;
    TextView t1;
    TextView t2;
    EditText m;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        String detail = getIntent().getStringExtra("title");
        String rate2 = getIntent().getStringExtra("detail");
        rate=Float.parseFloat(rate2);
        t1 = (TextView)findViewById(R.id.textView4);
        t2 = (TextView)findViewById(R.id.output);
         m = findViewById(R.id.btn_return);
        t1.setText(detail);
    }
    private  void show(float s){
        t2.setText(String.valueOf(s));
    }
    public  void  calculate(View v){
        Float rmb = Float.parseFloat(m.getText().toString());
        show(100/rate*rmb);

    }
}