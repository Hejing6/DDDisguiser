package com.example.frist;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

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

public class ListActivity3 extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
    private static final String TAG = "MyListActivity3";

    Handler handler;

    private ArrayList<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    private int msgWhat=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list3);
        // ListView list3 = findViewById(R.id.mylist3);
        //data

        /*listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 50; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:" + i);//标题文字
            map.put("ItemDetail", "detail:" + i);//详情描述
            listItems.add(map);
        }*/
        //adapter

        //
        //list3.setAdapter(listItemAdapter);
        //getListView().setAdapter(ListItemAapter);
        //setListAdapter(adapter);
        // getListView().setOnItemClickListener(this);
        //initListView();
        this.setListAdapter(listItemAdapter);
        MyAdapter adapter =new MyAdapter(this,R.layout.list_item,listItems);

        Thread t=new Thread(this);
        t.start();


        handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == msgWhat) {
                    List<HashMap<String, String>> List2 = (List<HashMap<String, String>>) msg.obj;
                    SimpleAdapter listItemAdapter= new SimpleAdapter(ListActivity3.this,
                            List2,//listItem 数据源
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }

        };


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick:position="+position);
        Object itemAtPosition=getListView().getItemAtPosition(position);
        HashMap<String,String>map =(HashMap<String, String>)itemAtPosition;
        String title=map.get("ItemTitle");
        String detail=map.get("ItemDetail");
        Log.i(TAG,"title"+title);
        Log.i(TAG,"detail"+detail);
        Intent intent = new Intent(this,ListActivity3.class);
        intent.putExtra("title",title);
        intent.putExtra("detail",detail);
        startActivity(intent);


    }
    /* private  void initListView(){
         listItems=new ArrayList<HashMap<String, String>>();
         for (int i=0; i<10;i++) {
             HashMap<String, String> map = new HashMap<String, String>();
             map.put("ItemTitle", "Rate:" + i);
             Log.i(TAG, "rate" + i);
             map.put("ItemDetail", "Detail:" + i);
             listItems.add(map);
         }
         listItemAdapter=new SimpleAdapter(this,
                 listItems,
                 R.layout.list_item,
                 new String[]{"ItemTitle", "ItemDetail"},
                 new int[]{R.id.itemTitle, R.id.itemDetail});


     }*/
    @Override
    public void run() {
        List<HashMap<String, String>> rateList = new ArrayList<HashMap<String, String>>();

        Log.i(TAG, "run:哪里有问题01");
        boolean marker=false;
        URL url = null;
        try {
            Log.i(TAG, "run:哪里有问题02");

            Thread.sleep(3000);
            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run:time=" + doc.title());//获取时间

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG, "run:time=" + publicTime.html());

            Element tables = doc.getElementsByTag("table").first();
            Log.i(TAG, "run:" + tables);
            Elements trs = tables.getElementsByTag("tr");
            Log.i(TAG, "sss" + trs);
            for (Element td : trs) {
                Elements tds = td.getElementsByTag("td");
                if (tds.size() > 0) {
                    String str = tds.first().text();
                    Log.i(TAG, "run:td=" + str);

                    String val = tds.get(5).text();
                    Log.i(TAG, "run:rate=" + val);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", str);
                    map.put("ItemDetail", val);
                    rateList.add(map);
                    Log.i(TAG, "run:str" + "=>" + rateList);

                }
                marker=true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();
        msg.what=msgWhat;


        if(marker){
            msg.arg1=1;

        }else{
            msg.arg1=0;
        }
        Log.i(TAG, "run:哪里有问题03");

        msg.obj=rateList;
        handler.sendMessage(msg);

    }

}