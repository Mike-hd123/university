package com.Mike.gateway.Service.Interface;

import com.Mike.common.util.MyError;

public interface TokenService {
    
    void check(String token) throws MyError;

    String getTyper(String token) throws MyError;
}
