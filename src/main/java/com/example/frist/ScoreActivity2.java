package com.example.frist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity2 extends AppCompatActivity {

    TextView textView4;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView4=findViewById(R.id.textView4);
    }
    private void show(){
        textView4.setText(String.valueOf(score));
    }
    public void button4(View v){
        score+=1;
        show();
    }
    public void button7(View v){
        score+=2;
        show();
    }
    public void button8(View v){
        score+=3;
        show();
    }
    public void button3(View v){
        score=0;
        show();
    }
}