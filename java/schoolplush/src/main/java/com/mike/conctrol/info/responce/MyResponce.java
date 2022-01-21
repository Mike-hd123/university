package com.mike.conctrol.info.responce;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MyResponce {
    Object Data;
    int code;
    String msg;
}
