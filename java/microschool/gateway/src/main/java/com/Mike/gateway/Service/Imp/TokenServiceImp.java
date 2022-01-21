package com.Mike.gateway.Service.Imp;

import com.Mike.common.util.MyError;
import com.Mike.gateway.Service.Interface.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImp implements TokenService {

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public void check(String token) throws MyError {
        if (redis.opsForValue().get(token) == null)
            throw new MyError(400, "你还未登录!");
    }

    @Override
    public String getTyper(String token) throws MyError {
        String typer = redis.opsForValue().get(token);
        if (typer == null)
            throw new MyError(400, "你还未登录!");
        return typer;
    }
    
}
