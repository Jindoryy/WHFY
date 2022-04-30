package com.example.whfy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bulb_on_off (View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/8I8sTewkIthh6U9p4nLm9XJ5tIf0LbeVbwhTQY1y/lights/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Body body = new Body(true);
        retrofitAPI.PutData(body).enqueue(new Callback<Success>() {

            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                if (response.isSuccessful()) {
                    Success data = response.body();
                    Log.d("TEST", "성공");
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


//    public void bulb_on_off (View view){

//        Toast.makeText(getApplicationContext(), "전구가 켜졌습니다.", Toast.LENGTH_LONG).show();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://192.168.0.5/api/8I8sTewkIthh6U9p4nLm9XJ5tIf0LbeVbwhTQY1y/lights/2")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//        Body body = new Body();
//        body.setBodyon(true);
//
//        retrofitAPI.PutData(body).enqueue(new Callback<Success>() {
//            @Override
//            public void onResponse(Call<Success> call, Response<Success> response) {
//                if (response.isSuccessful()) {
//                    Success data = response.body();
//                    Log.d("TEST", "PUT 성공");
//            }
//        }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                t.printStackTrace();
//            }
//        });



//        try {
//            URL url = new URL("https://192.168.0.5/clip/v2/resource/light/0b41b374-3bd5-4ba8-b6f2-2db5df368f1c");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // http 요청에 필요한 타입 정의
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestMethod("PUT");
//            conn.setDoOutput(true);
//            conn.setDoInput(true); // 서버로 부터 전달할 값이 있다면 true
//            conn.setRequestProperty("hue-application-key", "ym33XOFbnmlobmsqwcYEMpNZJzOzm-6ZEXXrxN8D");
//           conn.setRequestProperty("Accept", "application/json");

            // http 요청 실시
//           conn.connect();

//            JSONObject body = new JSONObject();
//            JSONObject body2 = new JSONObject();
//            body2.put("on", true);
//            body.put("on", body2.toString());
//
//            System.out.println(body.toString());
//
//            OutputStream os = conn.getOutputStream();
//            os.write(body.toString().getBytes());
//            os.flush();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
}