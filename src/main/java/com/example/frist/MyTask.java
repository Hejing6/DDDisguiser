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
import java.util.HashMap;
import java.util.List;

public class MyTask implements Runnable {
    private Handler handler;

    private int msgWhat = 7;

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
        List<HashMap<String,String>> rateList = new ArrayList<HashMap<String, String>>();
        boolean maker = false;
        URL url = null;
        try {
            Log.i(TAG, "run: 哪里有问题");

            Thread.sleep(5000);
            //Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();//获取汇率
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();//获取汇率a
            Log.i(TAG, "run: title = " + doc.title());
            //获取时间
            //Element publicTime = doc.getElementsByClass("time").first();
            //Log.i(TAG, "run: time = " + publicTime.html());
            //获取a汇率到列表
            Element table = doc.getElementsByTag("table").get(1);

            Elements tds = table.getElementsByTag("td");
//            Elements trs = table.getElementsByTag("tr");
//            for (Element tr : trs) {
//                Elements tds = tr.getElementsByTag("td");
//                if (tds.size() > 0) {
//                    String str = tds.first().text();
//                    Log.i(TAG, "run: td" + str);
//                    String val = tds.get(5).text();
//                    Log.i(TAG, "run: rate = " + val);
//                    ret.add(str + "-->" + val);}//获取汇率
            for (int i = 0;i<tds.size();i+=8) {
                if (tds.size() > 0) {
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(1+5);
                    String str1 = td1.text();
                    String  val = td2.text();
                    //String str = tds.get(i).text();
                    Log.i(TAG, "run:td:" + str1);
                    //String val = tds.get(i + 5).text();
                    Log.i(TAG, "run: rate:" + val);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", "Rate: " + str1);//标题文字
                    map.put("ItemDetail", "Detail: " + val);//详情描述
                    rateList.add(map);
                    Log.i("td", str1 + "=>" + val);
                }
                maker = true;
            }


            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
        }
        Message msg = handler.obtainMessage(0);
//        msg.obj = msgWhat;
//        if(maker){
//            msg.arg1 = 1;
//        }else {
//            msg.arg2 = 0;
//        }
        msg.obj = rateList;
        handler.sendMessage(msg);

        //返回数据给主线程
//        Log.i(TAG, "run: 哪里有问题2");
//        Message msg = handler.obtainMessage(0,ret);
//        handler.sendMessage(msg);

        }

}

