package com.example.john.volleysimple;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    ImageView img;
    EditText card_num;
    Button btn_get_pts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.txt);
        img = (ImageView)findViewById(R.id.img);
        card_num = (EditText)findViewById(R.id.card_num);
        btn_get_pts = (Button)findViewById(R.id.get_pts);
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        btn_get_pts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.0.2.2/projects/Pkt%20z%20karty/db_connection.php?card_num=";
                    url+=card_num.getText();
                Log.d("TAG",url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s){
                        if (s.length()<1) {
                            txt.setText("Brak karty o podanym numerze!");
                        } else {
                            txt.setText("Masz już: "+s+" pkt. !");
                        }
                        txt.setGravity(1);
                    }}, new Response.ErrorListener() {
                        @Override
                                public void onErrorResponse(VolleyError volleyError){
                                String error = "Error: "+ volleyError;
                                txt.setText(error);
                        }
                    });

                requestQueue.add(stringRequest);
                }
            });

        }
    }
