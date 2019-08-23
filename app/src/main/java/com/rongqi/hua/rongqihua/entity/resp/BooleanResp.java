package com.rongqi.hua.rongqihua.entity.resp;

/**
 * Created by dinghu on 2019/8/23.
 */
public class BooleanResp extends BaseResp{
    public Boolean data;
    public Boolean res;

    public Boolean isSuccess(){
        return data;
    }
}
