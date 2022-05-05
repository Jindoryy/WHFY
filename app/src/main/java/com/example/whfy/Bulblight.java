package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bulblight {

    // 기본 자료형(boolean)은 true, false 값 밖에 없기 때문에 인스턴스 변수로 선언될 때는 false가 기본으로 설정됨
    // 하지만 밑에는 래퍼 클래스 이기 때문에 null값을 포함할 수 있어서 전구 제어하기 위해서는 기본값을 false로 설정해줘야 함.
    private Boolean on = false;

    public Bulblight(){}
    public Bulblight(Boolean on) {
        this.on = on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Boolean getOn() {
        return on;
    }
}
