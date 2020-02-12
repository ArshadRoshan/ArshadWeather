package com.example.arshad.arshadweather;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JSONObject weather;
    TextView txtLocation;
    ImageView currentImage;
    EditText currentZip;
    Button enter;
    String zipcode = "";   //String that is sent to the async task
    AsyncThread asyncThread;
    TextView txtCurrent0;
    TextView txtHigh0;
    TextView txtHigh1;
    TextView txtHigh2;
    TextView txtHigh3;
    TextView txtHigh4;
    TextView txtLow0;
    TextView txtLow1;
    TextView txtLow2;
    TextView txtLow3;
    TextView txtLow4;
    TextView time0;
    TextView time3;
    TextView time6;
    TextView time9;
    TextView time12;
    ImageView currentPic;
    ImageView zeroPic;
    ImageView threePic;
    ImageView sixPic;
    ImageView ninePic;
    ImageView twelvePic;
    TextView quote;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncThread = new AsyncThread();
        asyncThread.execute("08852");

        currentZip = findViewById(R.id.editText);
        enter = findViewById(R.id.button);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipcode = currentZip.getText().toString();
                asyncThread.cancel(true);
                asyncThread = new AsyncThread();
                asyncThread.execute(zipcode);
            }
        });
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... voids) {

            URL url = null;
            try {
                //http://api.openweathermap.org/data/2.5/forecast?zip=08852,us&APPID=358da3b6257b4b510d8930a403947e7e
                url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=" + voids[0] + ",us&APPID=358da3b6257b4b510d8930a403947e7e&units=imperial");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection urlConnection = null;
            try {
                urlConnection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream inputStream = null;
            try {
                inputStream = urlConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String savedData = null;
            try {
                savedData = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                weather = new JSONObject(savedData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ArrayList<ForecastData> forecastDatas = new ArrayList<>();
            for(int x = 0; x < 5; x++){
                try {
                    String highTemp = weather.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("temp_max");
                    String lowTemp = weather.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("temp_min");
                    String temperature = weather.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("temp");
                    String description = weather.getJSONArray("list").getJSONObject(x).getJSONArray("weather").getJSONObject(0).getString("main");
                    int time = weather.getJSONArray("list").getJSONObject(x).getInt("dt");
                    forecastDatas.add(new ForecastData(temperature,highTemp,lowTemp,description,time));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            txtCurrent0 = findViewById(R.id.currentTemperature);
            currentPic = findViewById(R.id.currentImage);

            txtHigh0 = findViewById(R.id.txtHighZero);
            txtHigh1 = findViewById(R.id.txtHighOne);
            txtHigh2 = findViewById(R.id.txtHighTwo);
            txtHigh3 = findViewById(R.id.txtHighThree);
            txtHigh4 = findViewById(R.id.txtHighFour);

            txtLow0 = findViewById(R.id.txtLowZero);
            txtLow1 = findViewById(R.id.txtLowOne);
            txtLow2 = findViewById(R.id.txtLowTwo);
            txtLow3 = findViewById(R.id.txtLow3);
            txtLow4 = findViewById(R.id.txtLowFour);

            time0 = findViewById(R.id.timeZero);
            time3 = findViewById(R.id.timeThree);
            time6 = findViewById(R.id.timeSix);
            time9 = findViewById(R.id.timeNine);
            time12 = findViewById(R.id.timeTwelve);

            zeroPic = findViewById(R.id.imageZero);
            threePic = findViewById(R.id.ImageOne);
            sixPic = findViewById(R.id.imageTwo);
            ninePic = findViewById(R.id.imageThree);
            twelvePic = findViewById(R.id.ImageFour);

            quote =  findViewById(R.id.currentQuote);


            for(int x = 0; x < 5; x++){
                switch (x){
                    case 0:
                        txtHigh0.setText(forecastDatas.get(x).getHighTemperature());
                        txtHigh0.setTextColor(Color.BLUE);
                        txtLow0.setText(forecastDatas.get(x).getLowTemperature());
                        txtLow0.setTextColor(Color.RED);
                        txtCurrent0.setText(forecastDatas.get(x).getTemperature());
                        txtCurrent0.setTextColor(Color.BLACK);
                        time0.setText(forecastDatas.get(x).setTime(forecastDatas.get(x).getTime()));
                        time0.setTextColor(Color.WHITE);
                        currentPic.setImageResource(forecastDatas.get(x).getImage());
                        zeroPic.setImageResource(forecastDatas.get(x).getImage());
                        quote.setText(forecastDatas.get(x).getQuote());
                        quote.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        txtHigh1.setText(forecastDatas.get(x).getHighTemperature());
                        txtHigh1.setTextColor(Color.BLUE);
                        txtLow1.setText(forecastDatas.get(x).getLowTemperature());
                        txtLow1.setTextColor(Color.RED);
                        time3.setText(forecastDatas.get(x).setTime(forecastDatas.get(x).getTime()));
                        time3.setTextColor(Color.WHITE);
                        threePic.setImageResource(forecastDatas.get(x).getImage());
                        break;
                    case 2:
                        txtHigh2.setText(forecastDatas.get(x).getHighTemperature());
                        txtHigh2.setTextColor(Color.BLUE);
                        txtLow2.setText(forecastDatas.get(x).getLowTemperature());
                        txtLow2.setTextColor(Color.RED);
                        time6.setText(forecastDatas.get(x).setTime(forecastDatas.get(x).getTime()));
                        time6.setTextColor(Color.WHITE);
                        sixPic.setImageResource(forecastDatas.get(x).getImage());
                        break;
                    case 3:
                        txtHigh3.setText(forecastDatas.get(x).getHighTemperature());
                        txtHigh3.setTextColor(Color.BLUE);
                        txtLow3.setText(forecastDatas.get(x).getLowTemperature());
                        txtLow3.setTextColor(Color.RED);
                        time9.setText(forecastDatas.get(x).setTime(forecastDatas.get(x).getTime()));
                        time9.setTextColor(Color.WHITE);
                        ninePic.setImageResource(forecastDatas.get(x).getImage());
                        break;
                    case 4:
                        txtHigh4.setText(forecastDatas.get(x).getHighTemperature());
                        txtHigh4.setTextColor(Color.BLUE);
                        txtLow4.setText(forecastDatas.get(x).getLowTemperature());
                        txtLow4.setTextColor(Color.RED);
                        time12.setText(forecastDatas.get(x).setTime(forecastDatas.get(x).getTime()));
                        time12.setTextColor(Color.WHITE);
                        twelvePic.setImageResource(forecastDatas.get(x).getImage());
                        break;

                }
            }

            txtLocation = findViewById(R.id.id_txtLocation);
            try {
                txtLocation.setText(weather.getJSONObject("city").getString("name"));
                txtLocation.setTextColor(Color.BLACK);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class ForecastData{

        String temperature, highTemperature, lowTemperature, description;
        int time;

        public ForecastData(String temperature ,String highTemperature, String lowTemperature, String description, int time){
            this.temperature=temperature;
            this.highTemperature=highTemperature;
            this.lowTemperature=lowTemperature;
            this.description=description;
            this.time=time;
        }
        public String getTemperature(){
            return temperature + "°F";
        }
        public String getHighTemperature(){
            return highTemperature + "°F";
        }
        public String getLowTemperature(){
            return lowTemperature + "°F";
        }
        public String getDescription() {
            return description;
        }
        public int getTime()
        {
            return time;
        }
        public String setTime(int epoch)
        {
            int totalhours = (epoch/3600)-5;
            do{
                totalhours = totalhours-24;
            }while(totalhours>24);
            if(totalhours==24)
            {
                return "12:00 AM";
            }
            if(totalhours==1)
            {
                return "1:00 AM";
            }
            if(totalhours==2)
            {
                return "2:00 AM";
            }
            if(totalhours==3)
            {
                return "3:00 AM";
            }
            if(totalhours==4)
            {
                return "4:00 AM";
            }
            if(totalhours==5)
            {
                return "5:00 AM";
            }
            if(totalhours==6)
            {
                return "6:00 AM";
            }
            if(totalhours==7)
            {
                return "7:00 AM";
            }
            if(totalhours==8)
            {
                return "8:00 AM";
            }
            if(totalhours==9)
            {
                return "9:00 AM";
            }
            if(totalhours==10)
            {
                return "10:00 AM";
            }
            if(totalhours==11)
            {
                return "11:00 AM";
            }
            if(totalhours==12)
            {
                return "12:00 PM";
            }
            if(totalhours==13)
            {
                return "1:00 PM";
            }
            if(totalhours==14)
            {
                return "2:00 PM";
            }
            if(totalhours==15)
            {
                return "3:00 PM";
            }
            if(totalhours==16)
            {
                return "4:00 PM";
            }
            if(totalhours==17)
            {
                return "5:00 PM";
            }
            if(totalhours==18)
            {
                return "6:00 PM";
            }
            if(totalhours==19)
            {
                return "7:00 PM";
            }
            if(totalhours==20)
            {
                return "8:00 PM";
            }
            if(totalhours==21)
            {
                return "9:00 PM";
            }
            if(totalhours==22)
            {
                return "10:00 PM";
            }
            if(totalhours==23)
            {
                return "11:00 PM";
            }
            return "";
        }
        public int getImage(){
            switch(getDescription()){
                case "Rain":
                    return R.drawable.rain;
                case "Clear":
                    return R.drawable.clear;
                case "Snow":
                    return R.drawable.snow;
                case "Clouds":
                    return R.drawable.cloudy;
                case "Thunder":
                    return R.drawable.thunder;
            }
            return 0;
        }
        public String getQuote(){
            switch(getDescription()){
                case "Rain":
                    return "Why do mother kangaroos hate rainy days? \n" +
                            "Because then the children have to play inside.";
                case "Clear":
                    return "Why is the sky not happy on clear days? \n" +
                            "It has the blues";
                case "Snow":
                    return "Why does it take longer to build a blonde snowman? \n" +
                            "You have to hollow out its head first.";
                case "Clouds":
                    return "What happens when the clouds clear in California? \n" +
                            "UCLA";
                case "Thunder":
                    return "Why did Thor file a police report? \n" +
                            "Because someone stole his thunder";
            }
            return "";
        }
    }
}
