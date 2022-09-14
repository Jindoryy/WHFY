# 프로젝트 주제 및 설명 :smile:

"청각장애인의 육아의 어려움"이라는 기사를 읽고 실제로 청각장애인의 경우 아기 울음소리를 듣지 못하거나 집안에 위급한 상황이 생겼을때 사이렌 소리가 나면 이를 인지하지 못하여 더 큰 위험에 처할 수 있다. 그래서 소리를 대신 듣고 어떤 상황인지 알려주는 애플리케이션을 만드는 것이 목적.

# 진행 기간 및 인원 :sparkles:

- 2022.3.23 ~ 2022.6.15 (2.5개월)
- 인원 3명

# 주요 내용 :speech_balloon:

## 개발 흐름

![image](https://user-images.githubusercontent.com/87755660/189794686-a955760c-3e57-4736-b303-07f4fb67bd4a.png)

## 개발 결과

물건 떨어지는 소리 : 초록색 조명 점멸<br>
아기 울음 소리 : 빨간색 조명 점멸<br>
노크 소리 : 노란색 조명 점멸<br>

결과 동영상 : https://youtu.be/vxWF4g2Le6I

# 맡은 역할 :thumbsup:

이진규 : 앱<br>
엄보성 : 서버<br>
이재완 : 인공지능<br>

# 역할 상세 내용 (앱)

필립스 휴 API 연동(Philips hue API Connection)

- 모든 요청은 Retrofit 통신(앱 개발 시 서버통신에 사용되는 HTTP API를 자바, 코틀린의 인터페이스 형태로 변환해 안드로이드 개발시 API를 쉽게 호출할 수 있도록 지원하는 라이브러리)을 이용함

1. 브릿지 연결(Bridge Connection)

- 브릿지와 LAN으로 연결된 공유기 설정에 들어가서 브릿지의 IP 주소 확인
- POSTMAN으로 브릿지 IP 주소로 GET 전송을 보내 브릿지의 키값과 연결된 전구의 고유번호 받기

2. 전구 제어(Bulb Control)

- On/Off, 색 조절 등 기능 제어를 위해 형식에 맞는 JSON 형태의 JAVA Class 코드 생성
- 브릿지의 키값과 전구의 고유번호를 포함한 URL에 JSON 형식에 맞게 JAVA Class 를 구현 후 GSON(JAVA Class -> JSON 역직렬화)을 이용해 POST 또는 PUT 요청을 통해 전구 제어
- 받아오는 JSON 형태의 값을 GSON(JSON -> JAVA Class 직렬화)을 이용해 전구 상태에 따른 알림 출력

# 개발환경 :pencil2:

AndroidStudio<br>
Ubuntu 20.04<br>
tensorflow-gpu 2.6<br>
keras 2.4<br>
goormide<br>
python 3.6<br>
NodeJS 10.19.0<br>
Express 4.16.1<br>
MySQL 8.0

# 개선점 :pray:

1. wav 파일을 전송하는 형태므로 스트림을 직접 전송해 분류하는 것보다 속도가 느릴 것으로 예상한다.
오디오 버퍼를 전송하는 형태나 적절한 예제를 찾지 못해 적용하지 못했으나, 적용하면 분명 속도 부분에서 성능 향상이 기대되는 부분이다.

2. 현재는 앱을 실행하는 동안에만 사용이 가능하다. 즉, 화면이 잠기거나, 앱의 화면을 나가거나 하면 앱을 이용할 수 없다. 마이크를 백그라운드로 운용하는 것이 안드로이드 보안 정책에 걸려 구현하지 못한 것인데, 이런 형태라면 사용성이 많이 떨어진다. 다른 적절한 방법을 찾아내, 백그라운드에서도 앱이 계속 실행되며 소리 분류가 정상적으로 이뤄지게 만들어야 한다.

# 기대 효과

음성 인식 센서로 아이의 울음소리를 인식하여 청각장애인 부모에게 청각 외의 시각적 정보를 제공한다. 
이러한 정보를 스마트폰으로 전달해 위급상황을 즉각적으로 알림으로써 청각장애인 부모의 육아 환경 개선에 도움이 될 것이다.

# 사용한 오픈소스와 오픈 API

OmRecorder - A Simple Pcm / Wav audio recorder with nice api. (2017.07.04)
https://github.com/kailash09dabhi/OmRecorder

ml-sound-classifier - Machine Learning Sound Classifier (2019.10.25.)
https://github.com/daisukelab/ml-sound-classifier

philips hue
https://developers.meethue.com/develop/get-started-2/


