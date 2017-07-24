package com.example.john.volleysimple;

import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView txt, txtHistory, lblPts, lblHistory;
    ImageView img;
    EditText card_num;
    Button btn_get_pts;
    CheckBox chbo_show_history;
    String jsonHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblPts = (TextView)findViewById(R.id.lblPts);
        txt = (TextView)findViewById(R.id.txt);
        lblHistory = (TextView)findViewById(R.id.lblHistory);
        txtHistory = (TextView)findViewById(R.id.txtHistory);
        img = (ImageView)findViewById(R.id.img);
        card_num = (EditText)findViewById(R.id.card_num);
        btn_get_pts = (Button)findViewById(R.id.get_pts);
        chbo_show_history = (CheckBox)findViewById(R.id.showHistory);


        card_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(card_num.getText().length() == 8){
                    btn_get_pts.setEnabled(true);
                } else {
                    btn_get_pts.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_get_pts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://10.0.2.2/projects/Pkt%20z%20karty/db_connection.php?card_num=";
                url+=card_num.getText();
                String urlHistory = url;
                urlHistory += "&history=3";
                Boolean noCard;

                Log.d("TAG",url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s){
                        // Null return means there is no card with given id
                        if (s.length()<1) {
                            Toast.makeText(getApplicationContext(),"Brak karty o podanym numerze! Sprawdź wpisany numer.",Toast.LENGTH_LONG).show();
                            txt.setText("");
                            lblPts.setVisibility(View.INVISIBLE);
                        } else {
                            lblPts.setVisibility(View.VISIBLE);
                            txt.setText(s+" pkt.");
                        }
                    }}, new Response.ErrorListener() {
                        @Override
                                public void onErrorResponse(VolleyError volleyError){
                                String error = "Error: "+ volleyError;
                                Log.e("STRINGREQERROR", error);
                        }
                    });
                AppController.getInstance().addToRequestQueue(stringRequest, "POINTS");

                if (chbo_show_history.isChecked() == true){
                    getTransactionHistory(urlHistory);
                } else {
                    txtHistory.setText("");
                    lblHistory.setVisibility(View.INVISIBLE);
                }


                }
            });

        }

    public void getTransactionHistory(String urlHistory) {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, urlHistory, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSON", response.toString());
                try {
                    jsonHistory="";
                    for(int i=0; i<response.length(); i++){
                        JSONObject transaction = response.getJSONObject(i);
                        String tDate = transaction.getString("transaction_date");
                        String tSum = transaction.getString("transaction_sum");

                        jsonHistory += tDate + " - " + tSum + " zł.\n\n";
                    }
                    txtHistory.setText(jsonHistory);
                    if (txtHistory.length()>0){
                        lblHistory.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String error = "Error: " + volleyError;
                Log.e("JSONREQERROR", error);
            }
        });

        AppController.getInstance().addToRequestQueue(req, "HISTORY");

    }

    }


