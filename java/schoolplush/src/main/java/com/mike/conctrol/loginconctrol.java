package com.mike.conctrol;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mike.conctrol.info.request.Userlogin;
import com.mike.conctrol.info.responce.MyResponce;
import com.mike.mapper.userinfomapper;
import com.mike.mapper.databaseobj.userinfo;
import org.springframework.util.DigestUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class loginconctrol {
    @Autowired
    private userinfomapper userinfomapper;

    @Autowired
    private MyResponce responce;

    @Autowired
    private StringRedisTemplate redis;

    userinfo userinfo;

    // 登录和退出页面
    @PostMapping("login")
    public MyResponce login(@RequestBody Userlogin user) {
        System.out.println(user.getUsername() + ' ' + user.getPassword());
        userinfo = userinfomapper.getuser(user.getUsername());
        if (userinfo != null) {
            if (DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(userinfo.getPasswd())) {
                String token = JWT.create().withAudience(userinfo.getUsername())
                        .sign(Algorithm.HMAC256(userinfo.getPasswd()));

                // 保存到redis中
                redis.opsForValue().set(token, user.getUsername(), 1, TimeUnit.DAYS);

                responce.setData(token);
                responce.setCode(200);
                responce.setMsg("login success");
            } else {
                responce.setData(null);
                responce.setCode(400);
                responce.setMsg("Password mistake");
            }
        } else {
            responce.setData(null);
            responce.setCode(400);
            responce.setMsg("can't not find account");
        }
        return responce;
    }

    // 注销登录
    @GetMapping("loginout")
    public MyResponce loginout(HttpServletRequest request) {
        System.out.println("loginout");
        String token = request.getHeader("Authorization");
        redis.delete(token);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("loginout");
        return responce;
    }

    // 测试是否在线
    @GetMapping("online")
    public MyResponce online() {
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("online");
        return responce;
    }
}