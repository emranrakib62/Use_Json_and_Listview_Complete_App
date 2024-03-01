package com.example.usejsonandlistviewcompleteapp;

import static com.android.volley.Request.*;
import static com.android.volley.Request.Method.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
ProgressBar progressBar;
ListView listView;
TextView tvtitle;
ImageView image;
ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
HashMap<String, String>hashMap;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        listView=findViewById(R.id.listview);



String url="https://emranrakib.000webhostapp.com/Apps/vedio.json";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            progressBar.setVisibility(View.GONE);



                try {


                    for(int x=0;x<response.length();x++){


                        JSONObject jsonObject=response.getJSONObject(x);
                        String title=jsonObject.getString("title");
                        String vedio_id=jsonObject.getString("vedio_id");
                       hashMap=new HashMap<>();
                       hashMap.put("title",title);
                       hashMap.put("vedio_id",vedio_id);
                       arrayList.add(hashMap);


                    }
                 MYadapter mYadapter=new MYadapter();
                    listView.setAdapter(mYadapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);


    }


    private  class MYadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=getLayoutInflater();
            View myview =layoutInflater.inflate(R.layout.item,null);
         tvtitle=myview.findViewById(R.id.tvtitle);
         image=myview.findViewById(R.id.image);
        HashMap<String,String>hashMap=arrayList.get(position);
        String title=hashMap.get("title");
        String vedio_id=hashMap.get("vedio_id");

        String image_url="https://img.youtube.com/vi/"+vedio_id+"/0.jpg";

            tvtitle.setText(title);
            Picasso.get().load(image_url).placeholder(R.drawable.pngtree).into(image);



            return myview;
        }
    }



}