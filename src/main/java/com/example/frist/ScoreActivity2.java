package com.example.frist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity2 extends AppCompatActivity {

    TextView textView4;
    TextView textView5;
    int scorea=0;
    int scoreb=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2);
        textView4=findViewById(R.id.texta);
        textView5=findViewById(R.id.btn);
    }


    private void showa(){
        textView4.setText(String.valueOf(scorea));
    }
    public void scoreOnea(View v){
        scorea+=1;
        showa();
    }
    public void scoreTwoa(View v){
        scorea+=2;
        showa();
    }
    public void scoreThreea(View v){
        scorea+=3;
        showa();
    }

    private void showb(){
        textView5.setText(String.valueOf(scoreb));
    }
    public void scoreOneb(View v){
        scoreb+=1;
        showb();
    }
    public void scoreTwob(View v){
        scoreb+=2;
        showb();
    }
    public void scoreThreeb(View v){
        scoreb+=3;
        showb();
    }
    public void btnReset(View v) {
        scorea = 0;
        scoreb=0;
        showa();
        showb();

    }




}