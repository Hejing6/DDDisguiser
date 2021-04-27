package com.example.frist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "RateActivity";
    EditText input;
    TextView result;
    Float dollarRate = 0.1528f;
    Float euroRate = 0.1284f;
    Float wonRate = 171.99f;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        input = findViewById(R.id.input);
        result = findViewById(R.id.output);

        //获得对象
        SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);//自己定义的文件名
        //PreferenceManager.getDefaultSharedPreferences(this);//获得默认文件

        dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate = sharedPreferences.getFloat("won_rate",0.0f);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what==7){
                    String str = (String)msg.obj;
                    Log.i(TAG,"handleMessage: get str=" + str);
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };

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
        open();

        Log.i(TAG,"openConFig:dollarRate="+dollarRate);
        Log.i(TAG,"openConFig:euroRate="+euroRate);
        Log.i(TAG,"openConFig:wonRate="+wonRate);

        //startActivity(config);
        //startActivityForResult(config,5);

    }

    private void open() {
        Intent config = new Intent(this,ConfigActivity.class); ;
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);


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

            //编辑 保存数据
            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.apply();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_setting){
            open();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void run() {
        Log.i(TAG,"run:run().......");
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //线程中完成的任务
        URL url = null;
        try{
            url = new URL("https://www.usd-cny.com/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG,"run: html=" + html);
        }catch(MalformedURLException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }
        //返回数据给主线程


        Message msg = handler.obtainMessage(7);
        msg.obj = "from message";
        handler.sendMessage(msg);
    }


    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while (true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz < 0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}