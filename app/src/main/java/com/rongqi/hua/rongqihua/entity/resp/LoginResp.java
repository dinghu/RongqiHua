package com.rongqi.hua.rongqihua.entity.resp;

import com.google.gson.annotations.SerializedName;

public class LoginResp extends DataResp<LoginResp.UuidBean> {


    public static class UuidBean {
        /**
         * uuid : 458b9f1ab01c42059ea6b1b8e04e4f83
         */

        private String uuid;
        private boolean userLog;
        @SerializedName("teacher")
        private TUserInfo tUserInfo;

        public boolean isUserLog() {
            return userLog;
        }

        public void setUserLog(boolean userLog) {
            this.userLog = userLog;
        }

        public TUserInfo gettUserInfo() {
            return tUserInfo;
        }

        public void settUserInfo(TUserInfo tUserInfo) {
            this.tUserInfo = tUserInfo;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
