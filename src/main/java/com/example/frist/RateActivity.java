package com.example.frist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    private static final String TAG = "RateActivity";
    EditText input;
    TextView result;
    Float dollarRate = 0.1528f;
    Float euroRate = 0.1284f;
    Float wonRate = 171.99f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        input = findViewById(R.id.input);
        result = findViewById(R.id.output);

    }
    public void click(View btn){
        Log.d(TAG, "click: ");

        float r =0.0f;
        switch (btn.getId()){
            case R.id.btn_dollar:
                r = dollarRate;
                break;
            case R.id.btn_euro:
                r = euroRate;
                break;
            case R.id.btn_won:
                r = wonRate;
                break;
        }
        String str = input.getText().toString();
        Log.i(TAG, "click: str=" + str);
        if( str==null || str.length()==0){
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }else{

            double a=Double.parseDouble(input.getText().toString());
            double b=a*r;
            result.setText(""+b);

        }

    }
    public void openConFig(View btn){
        Log.i(TAG, "openConFig:....");
        Intent config = new Intent(this,ConfigActivity.class);
        //加入传递的参数
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"openConFig:dollarRate="+dollarRate);
        Log.i(TAG,"openConFig:euroRate="+euroRate);
        Log.i(TAG,"openConFig:wonRate="+wonRate);

        //startActivity(config);
        startActivityForResult(config,5);

    }
    public void onActivityResult(int requestCode,int resultCode, Intent data ){
        if(requestCode==5 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.0f);
            euroRate = bundle.getFloat("key_euro",0.0f);
            wonRate = bundle.getFloat("key_won",0.0f);
            Log.i(TAG,"onActivityResult: dollarRate"+ dollarRate);
            Log.i(TAG,"onActivityResult: euroRate"+ euroRate);
            Log.i(TAG,"onActivityResult: wonRate"+ wonRate);

        }
        super.onActivityResult(requestCode,resultCode,data);
    }


}