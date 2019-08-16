package com.rongqi.hua.rongqihua.entity.req;

/**
 * Created by dinghu on 2019/8/16.
 */
public class AccountReq {
    public String admin;
    public String password;

    public AccountReq(String admin, String password) {
        this.admin = admin;
        this.password = password;
    }
}
