package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinghu on 2019/7/18.
 */
public class DataResp<T> extends BaseResp {
    @SerializedName(value = "data", alternate = "list")
    public T data;
}
