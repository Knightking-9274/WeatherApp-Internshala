package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtText;//Taking input
    private TextView txtView, txtViewCondition, txtViewWind,txtPressure,txtHumudity ,txtMin,txtMax;//Displaying output

    private Button btnSearch,btnFavourite; //Button for searching and adding favourites

    private Spinner spinner; //Spinner to add favourite cities

    ArrayList<String> cities; //Arraylist to store cities

    ArrayAdapter<String> adpt; //Adapter for Spinner


//    private Retrofit retrofit;

//    private FetchAPI fetchAPI;

     String apiKey = "a9a220e757359d06c861c230a3b1be5c";

     //URL from OpenWeatherAPI
    // String apiURL = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI elements
        edtText = findViewById(R.id.edtTxt1);
        txtView = findViewById(R.id.txtView1);
//        txtViewCondition = findViewById(R.id.txtView2);
        txtPressure = findViewById(R.id.txtView4);
        txtHumudity = findViewById(R.id.txtView5);
        txtMin = findViewById(R.id.txtView6);
        txtMax = findViewById(R.id.txtView7);
//        txtViewWind = findViewById(R.id.txtView3);
        btnSearch = findViewById(R.id.btnSearch);
        btnFavourite = findViewById(R.id.btnFavourite);
        spinner = findViewById(R.id.spinner1);


        // Button click listener to search for weather
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrofit setup for API communication
                 Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
                FetchAPI fetchAPI = retrofit.create(FetchAPI.class);

                // Making API call to get weather information for the entered city
                Call<Test> test = fetchAPI.getWeather(edtText.getText().toString().trim(),apiKey);
                test.enqueue(new Callback<Test>() {
                    @Override
                    public void onResponse(Call<Test> call, Response<Test> response) {
                        if(response.code()==404){
                            // Display a message for incorrect city name
                            Toast.makeText(MainActivity.this, "Enter Correct City Name!", Toast.LENGTH_LONG).show();
                        }
                        else if(!response.isSuccessful()){
                            // Display the HTTP response code if not successful
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                        }
                        // Parsing the JSON response to extract weather information
                        Test testCall = response.body();
                        Main main = testCall.getMain();


//                        Wind wind = testCall.getWind();



//                        Weather weather = testCall.getWeather();

                        //Tempreture Extracted in Celcius
                        Double d = main.getTemp();
                        Integer tempreture = (int) (d-273.15);
                        txtView.setText("Tempreture " +String.valueOf(tempreture)+"°C");


//                        //Weather Conditions Extracted
//                        String s = weather.getDescription();
//                        txtViewCondition.setText("Condition "+s);


//                        //Wind Extracted
//                        Double windSpeed = wind.getSpeed();
//                        int speed = (int)Math.round(windSpeed);
//                        txtViewWind.setText("Winds Speed "+String.valueOf(speed) );


                        //Humidity Extracted
                        Integer humidity = main.getHumidity();
                        txtHumudity.setText("Humidity "+String.valueOf(humidity));

                        //Pressure Extracted
                        Integer pressure = main.getPressure();
                        txtPressure.setText("Pressure "+String.valueOf(pressure));


                        //MinTempreture Extracted
                        Double min = main.getTempMin();
                        int minTemp = (int)(min-273.15);
                        txtMin.setText("Minimum "+String.valueOf(minTemp) + "°C");


                        //MaxTempreture Extracted
                        Double max = main.getTempMax();
                        int maxTemp = (int) (max-273.15);
                        txtMax.setText("Maximum "+String.valueOf(maxTemp)+ "°C");
                    }

                    @Override
                    public void onFailure(Call<Test> call, Throwable t) {
                        // Display an error message in case of API call failure
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

            // Initializing favorite cities list and adapter for the spinner
            cities = new ArrayList<>();
            adpt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,cities);
            adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adpt);

         //Button Click Listners to add citites to favourite
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adding entered city to spinner and updating spinner
                String cityName = edtText.getText().toString();
                cities.add(cityName);
                adpt.notifyDataSetChanged();

            }
        });
    }




}