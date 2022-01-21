package com.Mike.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyResponce {
    private Object Data;
    private int code;
    private String msg;
}
