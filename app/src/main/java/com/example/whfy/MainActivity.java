package com.example.whfy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    // retrofit log 변수값 (임시)
    private final String TAG = "MainActivityLog";

    //GPU_server 컨테이너
//    private final String URL = "https://gpu-server-hlndu.run-asia-northeast1.goorm.io/";
    // server 컨테이너
    private final String URL = "https://server-zhdfl.run.goorm.io/";

    // 서버에서 넘어오는 사운드 결과값
    static String Sound_result = "initial sound";

    // 전구 선택 변수 선언
    static int Bulbpick;

    // 위젯 변수 선언
    RadioButton radiobutton1, radiobutton2, radiobutton3;

    // 전구별로 제어하기 위해 객체 배열 생성 (bulblights[0]은 쓰지 않음)
    Bulblight[] bulblights = {new Bulblight(), new Bulblight(), new Bulblight(), new Bulblight()};

    // 브릿지 인증 키 값
    static String Bridgekey = "initial value";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 선언한 위젯변수에 id를 통해 해당 클래스들을 가져옴
        radiobutton1 = (RadioButton) findViewById(R.id.rg_btn1);
        radiobutton2 = (RadioButton) findViewById(R.id.rg_btn2);
        radiobutton3 = (RadioButton) findViewById(R.id.rg_btn3);
    }


    // 브릿지 연결하는 메서드
    public void bridge_connect(View view) {

        // 텍스트 위젯 생성
        TextView textView = (TextView) findViewById(R.id.textView);

        // retrofit 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        retrofitAPI.PostData(new Devicetype()).enqueue(new Callback<List<Bridgekey>>() {
            @Override
            public void onResponse(Call<List<Bridgekey>> call, Response<List<Bridgekey>> response) {
                if (response.isSuccessful()) {
                    List<Bridgekey> data = response.body();

                    try {
                        Bridgekey = data.get(0).getSuccess().getUsername();
                        Log.d("Bridge key", Bridgekey);

                        // 버튼 연결 알림
                        if (!Bridgekey.equals("initial value")) {
                            Toast.makeText(getApplicationContext(), "브릿지에 연결 되었습니다.", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "브릿지 버튼을 눌러 주시기 바랍니다.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bridgekey>> call, Throwable t) {
                Log.d("호출 TEST", "호출 실패");
            }
        });
    }


    // 버튼 클릭시 불빛 on/off 하는 메서드
    public void bulb_on_off(View view) {

        // retrofit 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/" + Bulbpick + "/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        // 전구별로 불빛 설정하는 code
        if (bulblights[Bulbpick].getOn()) {
            bulblights[Bulbpick].setOn(false);
        } else {
            bulblights[Bulbpick].setOn(true);
        }

        // 전구 불빛 상태에 따른 메시지 출력 code
        Toast.makeText(getApplicationContext(), bulblights[Bulbpick].getOn() ? "전구가 켜졌습니다." : "전구가 꺼졌습니다.", Toast.LENGTH_SHORT).show();

        // retrofitAPI를 이용하여 json 전송 code
        retrofitAPI.PutData(bulblights[Bulbpick]).enqueue(new Callback<List<Bulbreceive>>() {

            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();


//                    // data.get(0).getLights2StateOn() 반환형이 Boolean인데 String만 log에 찍혀서 주석 처리 해놓음 (성공한거임)
//                    Log.d("TEST", "" + data.get(0).getLights2StateOn() + "");

                }
            }

            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });

    }


    // 전구 선택 메서드
    public void radioButtonClick(View view) {
        if (radiobutton1.isChecked()) {
            Bulbpick = 1;
        } else if (radiobutton2.isChecked()) {
            Bulbpick = 2;
        } else if (radiobutton3.isChecked()) {
            Bulbpick = 3;
        }
    }


    // 버튼 클릭시 전구 색깔 바꾸는 메서드
    public void bulb_color(View view) {

        // retrofit 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/" + Bulbpick + "/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        switch (view.getId()) {
            case R.id.buttonred:
                bulblights[Bulbpick].setHue(0);
                break;
            case R.id.buttonorange:
                bulblights[Bulbpick].setHue(10000);
                break;
            case R.id.buttongreen:
                bulblights[Bulbpick].setHue(25500);
                break;
            case R.id.buttonblue:
                bulblights[Bulbpick].setHue(46920);
                break;
            case R.id.buttonpupple:
                bulblights[Bulbpick].setHue(50000);
                break;
        }
        bulblights[Bulbpick].setOn(true);

        // retrofitAPI를 이용하여 json 전송 code
        retrofitAPI.PutData(bulblights[Bulbpick]).enqueue(new Callback<List<Bulbreceive>>() {


            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }


            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });
    }


    // 버튼을 눌렀을 때 단 시간내에 점, 소등하는 메서드
    public void danger_button(View view) {

        for (int i = 0; i < 30; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                bulb_on_off(view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // 버튼을 눌렀을 때 전체 소등
    public void Alloff_button(View view) {


        // retrofit 3개 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/1/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/2/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();
        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/3/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        RetrofitAPI retrofitAPI2 = retrofit2.create(RetrofitAPI.class);
        RetrofitAPI retrofitAPI3 = retrofit3.create(RetrofitAPI.class);

        // 전구별로 불빛 설정하는 code
        bulblights[1].setOn(false);
        bulblights[2].setOn(false);
        bulblights[3].setOn(false);


        // retrofitAPI를 이용하여 json 전송 code
        retrofitAPI.PutData(bulblights[1]).enqueue(new Callback<List<Bulbreceive>>() {

            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }

            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });


        retrofitAPI2.PutData(bulblights[2]).enqueue(new Callback<List<Bulbreceive>>() {

            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }

            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });


        retrofitAPI3.PutData(bulblights[3]).enqueue(new Callback<List<Bulbreceive>>() {

            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }

            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });

    }

    // 서버에서 받은 결과 값으로 점등하는 메서드
    public void Sound_data(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);

        Call<ResponseBody> call_get = service.getFunc("get data");
        call_get.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Sound_result = response.body().string();
                        Log.v(TAG, "flag_0");
                        Sound_bulb(view);
                        Log.v(TAG, "flag_1");
                        Log.v(TAG, "Sound_result = " + Sound_result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "error = " + String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "error = " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
                Log.v(TAG, "flag_2");
                Sound_bulb2(view);
                Log.v(TAG, "flag_3");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v(TAG, "Fail");
                Toast.makeText(getApplicationContext(), "Response Fail", Toast.LENGTH_SHORT).show();
            }
        });

//        for (int i = 0; i < 10; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                Sound_bulb2(view);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    // 단 시간내에 점, 소등하는 메서드 Sound_bulb2를 호출
    public void Sound_bulb(View view) {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                Sound_bulb2(view);
                Log.v(TAG, "flag_run");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 서버에서 받아 온 상황(String)에 따라 색 조절 및 점, 소등
    public void Sound_bulb2(View view) {

        Log.v(TAG,"Sound_bulb2  " + Sound_result);
        // retrofit 3개 호출
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/1/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();
        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/2/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();
        Retrofit retrofit4 = new Retrofit.Builder()
                .baseUrl("https://192.168.0.5/api/" + Bridgekey + "/lights/3/")
                .addConverterFactory(GsonConverterFactory.create()) // gson은 json을 java class로 바꾸는데 사용
                .client(SSLHandling.getUnsafeOkHttpClient().build()) // ssl 우회 code
                .build();

        RetrofitAPI retrofitAPI = retrofit2.create(RetrofitAPI.class);
        RetrofitAPI retrofitAPI2 = retrofit3.create(RetrofitAPI.class);
        RetrofitAPI retrofitAPI3 = retrofit4.create(RetrofitAPI.class);


        if (Sound_result.equals("glass")) { // glass (유리)
            bulblights[1].setHue(0);
            bulblights[2].setHue(0);
            bulblights[3].setHue(0);
        } else if (Sound_result.equals("knock")) { // knock (노크)
            bulblights[1].setHue(10000);
            bulblights[2].setHue(10000);
            bulblights[3].setHue(10000);
        } else if (Sound_result.equals("baby_cry")) { // baby_cry (아기울음소리)
            bulblights[1].setHue(25500);
            bulblights[2].setHue(25500);
            bulblights[3].setHue(25500);
        } else if (Sound_result.equals("siren")) { // siren (사이렌)
            bulblights[1].setHue(46920);
            bulblights[2].setHue(46920);
            bulblights[3].setHue(46920);
        } else if (Sound_result.equals("scream")) { // scream (비명)
            bulblights[1].setHue(50000);
            bulblights[2].setHue(50000);
            bulblights[3].setHue(50000);
        }

        if (bulblights[1].getOn()) {
            bulblights[1].setOn(false);
        } else {
            bulblights[1].setOn(true);
        }

        if (bulblights[2].getOn()) {
            bulblights[2].setOn(false);
        } else {
            bulblights[2].setOn(true);
        }

        if (bulblights[3].getOn()) {
            bulblights[3].setOn(false);
        } else {
            bulblights[3].setOn(true);
        }

        Log.v(TAG, "conn_1");
        retrofitAPI.PutData(bulblights[1]).enqueue(new Callback<List<Bulbreceive>>() {


            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }


            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });
        Log.v(TAG, "conn_2");
        retrofitAPI2.PutData(bulblights[2]).enqueue(new Callback<List<Bulbreceive>>() {


            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }


            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
            }
        });
        Log.v(TAG, "conn_3");
        retrofitAPI3.PutData(bulblights[3]).enqueue(new Callback<List<Bulbreceive>>() {


            @Override
            public void onResponse(Call<List<Bulbreceive>> call, Response<List<Bulbreceive>> response) {
                if (response.isSuccessful()) {
                    List<Bulbreceive> data = response.body();
                    Log.d("TEST", "성공");
                }
            }


            @Override
            public void onFailure(Call<List<Bulbreceive>> call, Throwable t) {
                t.printStackTrace();
                Log.d("TEST", "실패");
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