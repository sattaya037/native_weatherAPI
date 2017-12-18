package com.weather.std.bigweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MainActivity extends AppCompatActivity {
    RelativeLayout RelativeLayout;

    class WeatherInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                URL url=new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                int data = reader.read();
                String apiDetails ="";
                char current;

                while (data != -1){
                    current=(char) data;
                    apiDetails +=current;
                    data=reader.read();
                }
                return  apiDetails;
            }catch (Exception e) {
               e.printStackTrace();
            }

            return null;
        }
    }

    public  void  getWeatherInfo(View view){
        WeatherInfo weatherInfo = new WeatherInfo();

        EditText cityName = (EditText) findViewById(R.id.cityEditText);
        TextView weatherTextView =(TextView) findViewById(R.id.weatherTextView);
        RelativeLayout=(RelativeLayout)findViewById(R.id.activity_main);
        ImageView icon =(ImageView)findViewById(R.id.weatherIcon);
        try {
            String weatherApiDetiails = weatherInfo.execute("http://api.openweathermap.org/data/2.5/weather?q="
                    +cityName.getText().toString()+"&appid=a56fae216498a62d8469f1e5b7bd119f").get();
//            Log.i("Weather Api Info",weatherApiDetiails);

            JSONObject jsonObject = new JSONObject(weatherApiDetiails);

            String weather =jsonObject.getString("weather");
/*
            Log.i("Weather Details",weather);
*/

            JSONArray arrayc = new JSONArray(weather);

            String main ="";
            String description="";

            for(int i=0; i < arrayc.length();i++){

                JSONObject arrayObject = arrayc.getJSONObject(i);

                main =arrayObject.getString("main");
                description =arrayObject.getString("description");
            }

            if(main.equals("Clouds")) {
                icon.setBackgroundResource(R.drawable.clouds);
            }
            else if (main.equals("Clear")){
                icon.setBackgroundResource(R.drawable.sunny);
            }
            else if (main.equals("Haze")){
                icon.setBackgroundResource(R.drawable.foggy);
            }
            else if(main.equals("Fog")){
                icon.setBackgroundResource(R.drawable.fog);
            }
            else if(main.equals("Rain")){
                icon.setBackgroundResource(R.drawable.drop);
            }
            else {
                icon.setBackgroundResource(R.drawable.ic_more_horiz_black_24dp);
            }

            weatherTextView.setText("Main : "+ main + "\n" +
                                    "Desc :"+description+"\n"
                                     );
            /*Log.i("Main",main);
            Log.i("Desc",description);*/


            if(cityName.getText().toString().toLowerCase().equals("london")) {
                RelativeLayout.setBackgroundResource(R.drawable.london);
            }
            else if (cityName.getText().toString().toLowerCase().equals("phuket")){
                RelativeLayout.setBackgroundResource(R.drawable.phuket);
            }
            else if (cityName.getText().toString().toLowerCase().equals("rome")){
                RelativeLayout.setBackgroundResource(R.drawable.rome);
            }
            else if (cityName.getText().toString().toLowerCase().equals("paris")){
                RelativeLayout.setBackgroundResource(R.drawable.paris);
            }


            else {
                RelativeLayout.setBackgroundResource(R.drawable.b);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}