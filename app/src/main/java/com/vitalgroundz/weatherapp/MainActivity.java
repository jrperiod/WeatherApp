package com.vitalgroundz.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitalgroundz.weatherapp.data.Forecast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_API = "http://api.openweatherapp.org/";
    public static final String API_KEY = "bc165b4be4be8f55bba6417b6ef2a87d";
    public static final String UNIT_FAHRENHEIT = "imperial";
    public static final String UNIT_CELSIUS = "metric";

    private TextView temp;
    private TextView locale;
    private ImageView weatherIcon;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = (TextView) findViewById(R.id.temp);
        locale = (TextView) findViewById(R.id.locale);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void getCurrentForecastByZip() {
        OpenWeatherMapService openWeatherMapService = retrofit.create(OpenWeatherMapService.class);

        Call<Forecast> forecastCall = openWeatherMapService.currentForecastByZip("11411,us",UNIT_FAHRENHEIT, API_KEY);

        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.d("flow", "success: " + response.body().getName());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d("flow", "failure: " + t.getMessage());
            }
        });
    }

    private void populateScreen(Forecast forecast) {
        temp.setText(Integer.toString((int)forecast.getMain().getTemp()) + "\u2109");
        locale.setText(getString(R.string.hello_vitalgroundz, forecast.getName()));
    }



    @Override
    protected void onResume() {
        super.onResume();

        if(retrofit == null) {
            initRetrofit();
        }

        getCurrentForecastByZip();

    }

}
