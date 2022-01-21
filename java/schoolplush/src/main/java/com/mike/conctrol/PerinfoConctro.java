package com.mike.conctrol;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.mike.conctrol.info.request.changeuserinfo;
import com.mike.conctrol.info.responce.MyResponce;
import com.mike.config.qiniu;
import com.mike.mapper.adminmapper;
import com.mike.mapper.studentmapper;
import com.mike.mapper.teachermapper;
import com.mike.mapper.userinfomapper;
import com.mike.mapper.databaseobj.admin;
import com.mike.mapper.databaseobj.student;
import com.mike.mapper.databaseobj.teacher;
import com.mike.mapper.databaseobj.userinfo;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PerinfoConctro {
    @Autowired
    private userinfomapper userinfomapper;

    @Autowired
    private teachermapper teachermapper;

    @Autowired
    private studentmapper studentmapper;

    @Autowired
    private adminmapper adminmapper;

    @Autowired
    private MyResponce responce;

    @Autowired
    private qiniu qiniu;

    // 构造一个带指定Region对象的配置类
    private Configuration cfg = new Configuration(Region.regionAs0());

    private UploadManager uploadManager = new UploadManager(cfg);

    private String bashPath = "/tmp/";

    @GetMapping("utype")
    public MyResponce utype(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        userinfo user = userinfomapper.getuser(JWT.decode(token).getAudience().get(0));
        System.out.println(user);
        responce.setData(user.getUtype());
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

    @GetMapping("file")
    public MyResponce file(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        userinfo user = userinfomapper.getuser(JWT.decode(token).getAudience().get(0));
        System.out.println(user);
        if (user.getUtype() == 1) {// 管理员
            responce.setData(adminmapper.getfile(user.getUid()));
        } else if (user.getUtype() == 2) {// 教师
            responce.setData(teachermapper.getfile(user.getUid()));
        } else {// 学生
            responce.setData(studentmapper.getfile(user.getUid()));
        }
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

    // 获取个人信息
    @GetMapping("me")
    public MyResponce me(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        userinfo user = userinfomapper.getuser(JWT.decode(token).getAudience().get(0));
        System.out.println(user);
        if (user.getUtype() == 1) {// 管理员
            responce.setData("管理员暂无数据！");
        } else if (user.getUtype() == 2) {// 教师
            responce.setData(teachermapper.searchbyid(user.getUid()));
        } else {// 学生
            responce.setData(studentmapper.searchbyid(user.getUid()));
        }
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

    // 上传头像
    @PostMapping("upload")
    public MyResponce update(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String filename = null;
        if (!file.isEmpty()) {
            // 获取用户信息
            String token = request.getHeader("Authorization");
            // 设置文件保存路径
            filename = JWT.decode(token).getAudience().get(0) + "_" + file.getOriginalFilename();
            // 保存文件
            try {
                file.transferTo(new File(bashPath + filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        responce.setData(filename);
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

    // 保存头像
    @PostMapping("savefile")
    public MyResponce savefile(@RequestBody changeuserinfo info, HttpServletRequest request) {
        // 本地临时路径
        String dirPath = bashPath;
        // 七牛云配置
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String Uptoken = auth.uploadToken(qiniu.getBucket());
        // 上传文件
        try {
            com.qiniu.http.Response response = uploadManager.put(dirPath + info.getFilename(), null, Uptoken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            String filename = "http://" + qiniu.getUrl() + '/' + putRet.key;
            // 保存到数据库
            String token = request.getHeader("Authorization");
            userinfo user = userinfomapper.getuser(JWT.decode(token).getAudience().get(0));
            System.out.println(user);
            if (user.getUtype() == 1) {// 管理员
                adminmapper.savefile(new admin(user.getUid(), filename));
            } else if (user.getUtype() == 2) {// 教师
                teachermapper.savefile(new teacher(user.getUid(), filename));
            } else {// 学生
                studentmapper.savefile(new student(user.getUid(), filename));
            }
        } catch (QiniuException ex) {
            com.qiniu.http.Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok!");
        return responce;
    }

}
