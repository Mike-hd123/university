package com.Mike.user.Service.Imp;

import java.util.concurrent.TimeUnit;

import com.Mike.common.util.MyError;
import com.Mike.user.Control.Info.UserLoginInfo;
import com.Mike.user.Mapper.UserMapper;
import com.Mike.user.Mapper.Entity.User;
import com.Mike.user.Service.Interface.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper repository;

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public String login(UserLoginInfo user) throws MyError {
        com.Mike.user.Mapper.Entity.User dataUser = repository.getuser(user.getUsername());
        if (dataUser == null)
            throw new MyError(400, "用户名不存在！");
        if (!DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(dataUser.getPasswd()))
            throw new MyError(400, "密码错误！");
        String token = JWT.create().withAudience(user.getUsername()).sign(Algorithm.HMAC256(user.getPassword()));
        // 保存到redis中
        redis.opsForValue().set(token, dataUser.getUtype() + "", 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public void loginOut(String token) throws MyError {
        if (redis.opsForValue().get(token) != null) {
            boolean delete = redis.delete(token);
            if (!delete)
                throw new MyError(400, "退出登录失败，请刷新后重试！");
        }
    }

    @Override
    public String uType(String token) throws MyError {
        String utype;
        try {
            utype = redis.opsForValue().get(token);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return utype;
    }

    @Override
    public String file(String token) throws MyError {
        String file;
        try {
            String name;
            name = JWT.decode(token).getAudience().get(0);
            file = repository.getuser(name).getFile();
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return file;
    }

    @Override
    public void savefile(String token, String file) throws MyError {
        try {
            String name;
            name = JWT.decode(token).getAudience().get(0);
            User user = repository.getuser(name);
            user.setFile(file);
            repository.update(user);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }
}
