# 필립스 휴 API 연동(Philips hue API Connection)

- 모든 요청은 Retrofit 통신(앱 개발 시 서버통신에 사용되는 HTTP API를 자바, 코틀린의 인터페이스 형태로 변환해 안드로이드 개발시 API를 쉽게 호출할 수 있도록 지원하는 라이브러리)을 이용함

1. 브릿지 연결(Bridge Connection)

- 브릿지와 LAN으로 연결된 공유기 설정에 들어가서 브릿지의 IP 주소 확인
- POSTMAN으로 브릿지 IP 주소로 GET 전송을 보내 브릿지의 키값과 연결된 전구의 고유번호 받기

2. 전구 제어(Bulb Control)

- On/Off, 색 조절 등 기능 제어를 위해 형식에 맞는 JSON 형태의 JAVA Class 코드 생성
- 브릿지의 키값과 전구의 고유번호를 포함한 URL에 JSON 형식에 맞게 JAVA Class 를 구현 후 GSON(JAVA Class -> JSON 역직렬화)을 이용해 POST 또는 PUT 요청을 통해 전구 제어
- 받아오는 JSON 형태의 값을 GSON(JSON -> JAVA Class 직렬화)을 이용해 전구 상태에 따른 알림 출력
