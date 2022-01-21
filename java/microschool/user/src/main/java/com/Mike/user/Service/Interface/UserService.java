package com.Mike.user.Service.Interface;

import com.Mike.common.util.MyError;
import com.Mike.user.Control.Info.UserLoginInfo;

public interface UserService {
    String login(UserLoginInfo user) throws MyError;

    void loginOut(String token) throws MyError;

    String uType(String token) throws MyError;

    public String file(String token) throws MyError;
    
    public void savefile(String token,String filename) throws MyError;
}
