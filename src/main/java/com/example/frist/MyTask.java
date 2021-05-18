package com.example.frist;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable {
    private Handler handler;

    private static final String TAG = "MyTask";


//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_task);
//    }
    public void setHandler(Handler h) {
        this.handler = h;
    }
    @Override
    public void run() {
        List<String>   ret= new ArrayList<String>();
        URL url = null;
        try {
            Log.i(TAG, "run: 哪里有问题");
            //Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();//获取汇率
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run: title = " + doc.title());
            //获取时间
            //Element publicTime = doc.getElementsByClass("time").first();
            //Log.i(TAG, "run: time = " + publicTime.html());
            Element table = doc.getElementsByTag("table").get(1);
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 0) {
                    String str = tds.first().text();
                    Log.i(TAG, "run: td" + str);
                    String val = tds.get(5).text();
                    Log.i(TAG, "run: rate = " + val);
                    ret.add(str + "-->" + val);
                }
            }//获取汇率

            }catch(MalformedURLException e){
                e.printStackTrace();

            }catch(IOException e){
                e.printStackTrace();
            }
        //返回数据给主线程
        Log.i(TAG, "run: 哪里有问题2");
        Message msg = handler.obtainMessage(0,ret);
        handler.sendMessage(msg);

        }

}

