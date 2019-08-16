package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinghu on 2019/7/18.
 */
public class BaseResp {
    public String code;
    @SerializedName(value = "msg", alternate = "message")
    public String message;
}
