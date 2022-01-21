package com.Mike.common.util;

public class MyError extends Exception {
    private MyResponce myResponce;

    public MyError(int code, String msg) {
        super(msg);
        myResponce = new MyResponce();
        myResponce.setData(null);
        myResponce.setCode(code);
        myResponce.setMsg(msg);
    }

    public MyResponce getMyResponce() {
        return myResponce;
    };
}
