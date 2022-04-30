package com.example.whfy;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {

    private String baseUrl;
    private static ApplicationController instance;
    public static ApplicationController getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
        buildNetworkService();
    }

    private RetrofitAPI networkService;

    public RetrofitAPI getNetworkService() {
        return networkService;
    }

    public void buildNetworkService() {
        synchronized (ApplicationController.class) {
            if (networkService == null) {
                baseUrl = "https://192.168.0.5/api/8I8sTewkIthh6U9p4nLm9XJ5tIf0LbeVbwhTQY1y/lights/2";
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();
                GsonConverterFactory factory = GsonConverterFactory.create(gson);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(factory)
                        .build();
                networkService = retrofit.create(RetrofitAPI.class);
            }
        }
    }
}
