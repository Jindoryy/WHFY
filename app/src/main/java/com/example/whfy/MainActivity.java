package com.example.whfy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // 전구가 선택 되었는지 확인 출력하기 위해 잠시 생성
//    TextView textView2;

    // 전구 선택 클래스 선언
    Bulbchoice bulbchoice = new Bulbchoice();

    // 위젯 변수 선언
    RadioButton radiobutton1, radiobutton2, radiobutton3;

    // retrofit 호출 및 flag 설정(class 변수)
    static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 전구가 선택 되었는지 확인 출력하기 위해 잠시 생성
//        textView2 = (TextView) findViewById(R.id.textView);

        // 선언한 위젯변수에 id를 통해 해당 클래스들을 가져옴
        radiobutton1 = (RadioButton) findViewById(R.id.rg_btn1);
        radiobutton2 = (RadioButton) findViewById(R.id.rg_btn2);
        radiobutton3 = (RadioButton) findViewById(R.id.rg_btn3);
    }

    // 버튼 클릭시 불빛 설정하는 method
    public void bulb_on_off (View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/8I8sTewkIthh6U9p4nLm9XJ5tIf0LbeVbwhTQY1y/lights/" + bulbchoice.getChoice() + "/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // 전구 불빛 설정하는 code
        flag = !flag;
        Bulblight bulblight = new Bulblight(flag);

        // 전구 불빛 상태에 따른 메시지 출력 code
        Toast.makeText(getApplicationContext(), flag ? "전구가 켜졌습니다." : "전구가 꺼졌습니다.", Toast.LENGTH_SHORT).show();

        // retrofitAPI를 이용하여 json 전송 code
        retrofitAPI.PutData(bulblight).enqueue(new Callback<List<Bulbreceive>>() {

            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");

                    // data.get(0).getLights2StateOn() 반환형이 Boolean인데 String만 log에 찍혀서 주석 처리 해놓음 (성공한거임)
//                    Log.d("TEST", data.get(0).getLights2StateOn());

                }
            }

            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });

    }

    public void radioButtonClick(View view) {
        if (radiobutton1.isChecked()) {
            bulbchoice.setChoice(1);

            // 전구가 선택 되었는지 확인 출력하기 위해 잠시 생성
//            textView2.setText("전구1 선택됨");
        }
        else if (radiobutton2.isChecked()) {
            bulbchoice.setChoice(2);

            // 전구가 선택 되었는지 확인 출력하기 위해 잠시 생성
//            textView2.setText("전구2 선택됨");
        }
        else if (radiobutton3.isChecked()) {
            bulbchoice.setChoice(3);

            // 전구가 선택 되었는지 확인 출력하기 위해 잠시 생성
//            textView2.setText("전구3 선택됨");
        }
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